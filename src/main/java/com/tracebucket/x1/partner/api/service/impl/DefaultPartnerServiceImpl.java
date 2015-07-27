package com.tracebucket.x1.partner.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.repository.jpa.DefaultPartnerRepository;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerPositionAndOrganizationUnitResource;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by sadath on 26-May-2015.
 */
@Service
@Transactional
public class DefaultPartnerServiceImpl implements DefaultPartnerService {
    
    @Autowired
    private DefaultPartnerRepository partnerRepository;

    @Autowired
    private Mapper mapper;

    @Override
    public DefaultPartner save(String tenantId, DefaultPartner partner) {
        partner.setOwner(new DefaultOwner(tenantId));
        return partnerRepository.save(partner);
    }

    @Override
    public DefaultPartner findOne(String tenantId, AggregateId aggregateId) {
        return partnerRepository.findOne(aggregateId, tenantId);
    }

    @Override
    public List<DefaultPartner> findAll(String tenantId) {
        return partnerRepository.findAll(tenantId);
    }

    @Override
    public boolean delete(String tenantId, AggregateId partnerAggregateId) {
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            partnerRepository.delete(partner.getAggregateId(), tenantId);
            return partnerRepository.findOne(partnerAggregateId, tenantId) == null ? true : false;
        }
        return false;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner setPartnerCategory(String tenantId, PartnerCategory partnerCategory, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            partner.setPartnerCategory(partnerCategory);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner movePartnerToCategory(String tenantId, PartnerCategory newPartnerCategory,AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            partner.setPartnerCategory(newPartnerCategory);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPartnerRole(String tenantId, DefaultPartner addPartnerRole){
        DefaultPartner partner = partnerRepository.findOne(addPartnerRole.getAggregateId(), tenantId);
        if(partner != null) {
            Set<DefaultPartnerRole> partnerRoles = addPartnerRole.getAllAssignedRoles();
            if(partnerRoles != null && partnerRoles.size() > 0) {
                partnerRoles.parallelStream().forEach(role -> {
                    partner.addPartnerRole(role);
                });
            }
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner updatePartnerRole(String tenantId, DefaultPartner updatePartnerRole, EntityId partnerRoleEntityId) {
        DefaultPartner partner = partnerRepository.findOne(updatePartnerRole.getAggregateId(), tenantId);
        if(partner != null) {
            Set<DefaultPartnerRole> partnerRoles = updatePartnerRole.getAllAssignedRoles();
            if(partnerRoles != null && partnerRoles.size() > 0) {
                DefaultPartnerRole updateRole = partnerRoles.parallelStream()
                        .filter(t -> t.getEntityId().equals(partnerRoleEntityId))
                        .findFirst().get();
                if(updateRole != null) {
                    partner.updatePartnerRole(updateRole, mapper);
                }
            }
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addAddressToRole(String tenantId, EntityId partnerRoleEntityId, Address address, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            if(partnerRoles != null) {
                partnerRoles.stream().forEach(r -> {
                    if (r.getEntityId().getId().equals(partnerRoleEntityId.getId())) {
                        partner.addAddressToRole(r, address);
                    }
                });
                return partner;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner moveRoleAddressTo(String tenantId, EntityId partnerRoleEntityId, Address newAddress, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            if(partnerRoles != null) {
                partnerRoles.stream().forEach(r -> {
                    if(r.getEntityId().getId().equals(partnerRoleEntityId.getId())) {
                        partner.moveRoleAddressTo(r, newAddress);
                    }
                });
                return partner;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner changeOwner(String tenantId, DefaultOwner newOwner, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            partner.changeOwner(newOwner);
            return partner;
        }
        return null;
    }

    @Override
    public Boolean hasPartnerRole(String tenantId, AggregateId partnerAggregateId, EntityId roleEntityId){
        Long found = null;
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            if(partner.getAllAssignedRoles() != null && partner.getAllAssignedRoles().size() > 0) {
                found = partner.getAllAssignedRoles().parallelStream()
                        .filter(t -> t.getEntityId().getId().equals(roleEntityId.getId()))
                        .count();
            }
        }
        return (found != null && found > 0) ? true : false;
    }

    @Override
    public List<DefaultPartner> findPartnersByOrganization(String organizationUid) {
        return partnerRepository.findPartnersByOrganization(organizationUid);
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPosition(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid) {
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            partner.addPosition(partnerRoleUid, positionUid);
            return partner;
        }
        return null;
    }

    @Override
    public Set<DefaultPartner> searchPartners(String tenantId, AggregateId organizationAggregateId, String searchTerm) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {

            List<DefaultPartner> partners = findPartnersByOrganization(tenantId);
            Set<DefaultPartner> foundPartners = new HashSet<>();
                if(partners != null) {
                   partners.stream().forEach(p -> {
                        if((p.getTitle() != null && p.getTitle().toLowerCase().matches(searchTerm))) {
                            foundPartners.add(p);
                        } else if((p.getWebsite() != null && p.getWebsite().toLowerCase().matches(searchTerm))) {
                            foundPartners.add(p);
                        } else if(p.getPartnerRoles().stream().filter(pRoles -> pRoles.getName() != null && pRoles.getName().toLowerCase().matches(searchTerm) ||
                                                pRoles.getAddresses().stream().filter(addresses -> (addresses.getCity() != null && addresses.getCity().toLowerCase().matches(searchTerm) ||
                                                        addresses.getCountry() != null && addresses.getCountry().toLowerCase().matches(searchTerm) ||
                                                        addresses.getRegion() != null && addresses.getRegion().toLowerCase().matches(searchTerm) ||
                                                        addresses.getState() != null && addresses.getState().toLowerCase().matches(searchTerm) ||
                                                        addresses.getDistrict() != null && addresses.getDistrict().toLowerCase().matches(searchTerm) ||
                                                        addresses.getStreet() != null && addresses.getStreet().toLowerCase().matches(searchTerm) ||
                                                        addresses.getZip() != null && addresses.getZip().toLowerCase().matches(searchTerm))).count() > 0

                        ).count() > 0) {
                            foundPartners.add(p);
                        }
                    });
                    partners.stream().forEach(p -> {
                        Set<DefaultPartnerRole> partnerRoles = p.getPartnerRoles();
                        for(DefaultPartnerRole partnerRole : partnerRoles) {
                            if(partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee)partnerRole;
                                if(employee.getName() != null && employee.getName().toLowerCase().matches(searchTerm)) {
                                    foundPartners.add(p);
                                } else if(employee.getMiddleName() != null && employee.getMiddleName().toLowerCase().matches(searchTerm)){
                                    foundPartners.add(p);
                                }  else if(employee.getLastName() != null && employee.getLastName().toLowerCase().matches(searchTerm)){
                                    foundPartners.add(p);
                                }

                                Set<DefaultEmail> emails = employee.getEmail();
                                for(DefaultEmail email: emails){
                                    if(email.getEmail() != null && email.getEmail().toLowerCase().matches(searchTerm))
                                        foundPartners.add(p);
                                }
                            }
                        }
                    });
                    if(foundPartners.size() > 0) {
                        return foundPartners;
                    }
                }

        }
        return null;
    }

    @Override
    public Boolean isPositionAssigned(String tenantId, String organizationUid, String positionUid, String organizationUnitUid) {
        Boolean innerBreak = false;
        Boolean outerBreak = false;
        if(tenantId.equals(organizationUid)) {
            List<DefaultPartner> partners = findPartnersByOrganization(tenantId);
            if(partners != null) {
                for(DefaultPartner partner : partners) {
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    if(partnerRoles != null) {
                        for(DefaultPartnerRole partnerRole : partnerRoles) {
                            if (partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                if (employee.getOrganizationUnit() != null && employee.getOrganizationUnit().equals(organizationUnitUid)
                                        && employee.getPosition() != null && employee.getPosition().equals(positionUid)) {
                                    innerBreak = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(innerBreak) {
                        outerBreak = true;
                        break;
                    }
                }
                return outerBreak;
            }
        }
        return null;
    }

    @Override
    public Boolean isOrganizationUnitAssigned(String tenantId, String organizationUid, String organizationUnitUid) {
        Boolean innerBreak = false;
        Boolean outerBreak = false;
        if(tenantId.equals(organizationUid)) {
            List<DefaultPartner> partners = findPartnersByOrganization(tenantId);
            if(partners != null) {
                for(DefaultPartner partner : partners) {
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    if(partnerRoles != null) {
                        for(DefaultPartnerRole partnerRole : partnerRoles) {
                            if (partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                if (employee.getOrganizationUnit() != null && employee.getOrganizationUnit().equals(organizationUnitUid)) {
                                    innerBreak = true;
                                    break;
                                }
                            }
                        }
                    }
                    if(innerBreak) {
                        outerBreak = true;
                        break;
                    }
                }
                return outerBreak;
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPositionAndOrganization(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid, EntityId organizationUnitUid) {
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if (partner != null) {
            partner.addPositionAndOrganization(partnerRoleUid, positionUid, organizationUnitUid);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public List<DefaultPartner> addPositionAndOrganization(String tenantId, String organizationUid, List<DefaultPartnerPositionAndOrganizationUnitResource> resource) {
        if(tenantId.equals(organizationUid)) {
            if (resource != null) {
                List<DefaultPartner> partners = new ArrayList<DefaultPartner>();
                resource.stream().forEach(res -> {
                    DefaultPartner partner = partnerRepository.findOne(new AggregateId(res.getPartnerUid()), tenantId);
                    if (partner != null) {
                        partner.addPositionAndOrganization(new EntityId(res.getRoleUid()), new EntityId(res.getPositionUid()), new EntityId(res.getOrganizationUnitUid()));
                        partners.add(partner);
                    }
                });
                if(partners.size() > 0) {
                    return partners;
                }
            }
        }
        return null;
    }

    @Override
    public Map<Boolean, Set<DefaultPartner>> getEmployeesAssignedAndNotToOrganizationAndPosition(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid) {
        if(tenantId.equals(organizationUid.getAggregateId())) {
            Map<Boolean, Set<DefaultPartner>> partners = new HashMap<Boolean, Set<DefaultPartner>>();
            List<DefaultPartner> partnersAssigned = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(organizationUid.getAggregateId(), organizationUnitUid.getId(), positionUid.getId());
            List<DefaultPartner> partnersNotAssigned = partnerRepository.getEmployeesNotAssignedToOrganizationAndPosition(organizationUid.getAggregateId());
            if(partnersAssigned != null) {
                partners.put(true, new HashSet<>(partnersAssigned));
            }
            if(partnersNotAssigned != null) {
                partners.put(false, new HashSet<>(partnersNotAssigned));
            }
            return partners;
        }
        return null;
    }

    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationAndPosition(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid) {
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(organizationUid.getAggregateId(), organizationUnitUid.getId(), positionUid.getId());
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    @Override
    public Map<String, Map<String, ArrayList<DefaultPartner>>> getEmployeesAssignedToOrganizationAndPosition(String tenantId, AggregateId organizationUid) {
        if(tenantId.equals(organizationUid.getAggregateId())) {
            List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(tenantId);
            if(partners != null) {
                Map<String, Map<String, ArrayList<DefaultPartner>>> employees = new HashMap<String, Map<String, ArrayList<DefaultPartner>>>();
                partners.stream().forEach(partner -> {
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    if (partnerRoles != null) {
                        partnerRoles.stream().forEach(partnerRole -> {
                            if (partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                if (employees.containsKey(employee.getOrganizationUnit())) {
                                    Map<String, ArrayList<DefaultPartner>> positions = employees.get(employee.getOrganizationUnit());
                                    if (positions.containsKey(employee.getPosition())) {
                                        positions.get(employee.getPosition()).add(partner);
                                    } else {
                                        ArrayList<DefaultPartner> defaultEmployees = new ArrayList<DefaultPartner>();
                                        defaultEmployees.add(partner);
                                        positions.put(employee.getPosition(), defaultEmployees);
                                    }
                                } else {
                                    Map<String, ArrayList<DefaultPartner>> positionEmployees = new HashMap<String, ArrayList<DefaultPartner>>();
                                    ArrayList<DefaultPartner> defaultEmployees = new ArrayList<DefaultPartner>();
                                    defaultEmployees.add(partner);
                                    positionEmployees.put(employee.getPosition(), defaultEmployees);
                                    employees.put(employee.getOrganizationUnit(), positionEmployees);
                                }
                            }
                        });
                    }
                });
                if(employees.size() > 0) {
                    return employees;
                }
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public Set<DefaultPartner> restructureEmployees(String tenantId, AggregateId organizationUid, HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> employeeStructure) {
        if(tenantId.equals(organizationUid.getAggregateId())) {
            if(employeeStructure != null) {
                Set<DefaultPartner> defaultPartners = new HashSet<DefaultPartner>();
                employeeStructure.entrySet().stream().forEach(orgUnit -> {
                   /* if(tenantId.equals(orgUnit.getKey())) {*/
                        HashMap<String, ArrayList<Map<String, String>>> positions = orgUnit.getValue();
                        if(positions != null) {
                            positions.entrySet().stream().forEach(position -> {
                                ArrayList<Map<String, String>> employeesList = position.getValue();
                                if(employeesList != null && employeesList.size() > 0) {
                                    employeesList.stream().forEach(employees -> {
                                        employees.entrySet().stream().forEach(employee -> {
                                            DefaultPartner partner = findOne(tenantId, new AggregateId(employee.getKey()));
                                            if(partner != null) {
                                                partner.addPositionAndOrganization(new EntityId(employee.getValue()), new EntityId(position.getKey()), new EntityId(orgUnit.getKey()));
                                                defaultPartners.add(partner);
                                            }
                                        });
                                    });
                                }
                            });
                        }
                    /*}*/
                });
                Map<String, Map<String, ArrayList<DefaultPartner>>> currentEmployees = getEmployeesAssignedToOrganizationAndPosition(tenantId, organizationUid);
                if(currentEmployees != null && currentEmployees.size() > 0) {
                    currentEmployees.entrySet().forEach(orgUnitUid -> {
                        orgUnitUid.getValue().entrySet().forEach(posUid -> {
                            posUid.getValue().forEach(partner -> {
                                boolean found = false;
                                for (Map.Entry<String, HashMap<String, ArrayList<Map<String, String>>>> orgEntry : employeeStructure.entrySet()) {
                                    for (Map.Entry<String, ArrayList<Map<String, String>>> posEntry : orgEntry.getValue().entrySet()) {
                                        for (Map<String, String> empEntry : posEntry.getValue()) {
                                            for (Map.Entry<String, String> emp : empEntry.entrySet()) {
                                                if (partner.getAggregateId().getAggregateId().equals(emp.getKey())) {
                                                    found = true;
                                                }
                                            }
                                        }
                                    }
                                }
                                if (!found) {
                                    partner.removePositionAndOrganization();
                                    defaultPartners.add(partner);
                                }
                            });
                        });
                    });
                }
                if(defaultPartners.size() > 0) {
                    return defaultPartners;
                }
            }
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addDepartment(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId departmentUid) {
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        if(partner != null) {
            partner.addDepartment(partnerRoleUid, departmentUid);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addDepartmentPositionAndOrganizationUnit(String tenantId, AggregateId partnerUid, EntityId roleUid, String organizationUnitUid, String positionUid, String departmentUid) {
        DefaultPartner partner = partnerRepository.findOne(partnerUid, tenantId);
        if(partner != null) {
            partner.addDepartmentPositionAndOrganizationUnit(roleUid, organizationUnitUid, positionUid, departmentUid);
            return partner;
        }
        return null;
    }

    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationUnitAndDepartment(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId departmentUid) {
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationUnitAndDepartment(organizationUid.getAggregateId(), organizationUnitUid.getId(), departmentUid.getId());
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid, EntityId departmentUid) {
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment(organizationUid.getAggregateId(), organizationUnitUid.getId(), positionUid.getId(), departmentUid.getId());
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    @Override
    public DefaultPartner getLoggedInEmployeeDetails(String tenantId, String username) {
        return partnerRepository.getLoggedInEmployeeDetails(tenantId, username);
    }

    @Override
    public Map<String, String> getLoggedInEmployeeMinimalDetails(String tenantId, String username) {
        DefaultPartner employee = partnerRepository.getLoggedInEmployeeDetails(tenantId, username);
        if(employee != null) {
            Map<String, String> details = new HashMap<String, String>();
            details.put("uid", employee.getAggregateId().getAggregateId());
            details.put("organizationUid", employee.getOwner().getOrganizationUID());
            Set<DefaultPartnerRole> roles = employee.getAllAssignedRoles();
            if(roles != null && roles.size() > 0) {
                roles.stream().forEach(role -> {
                    if(role instanceof DefaultEmployee) {
                        DefaultEmployee emp = (DefaultEmployee) role;
                        details.put("organizationUnitUid", emp.getOrganizationUnit());
                        details.put("departmentUid", emp.getDepartment());
                        details.put("positionUid", emp.getPosition());
                        details.put("username", emp.getUserName());
                        details.put("firstName", emp.getFirstName());
                        details.put("middleName", emp.getMiddleName());
                        details.put("lastName", emp.getLastName());
                        if(emp.getValidity() != null) {
                            details.put("validFrom", emp.getValidity().getValidFrom().toString());
                            details.put("validTill", emp.getValidity().getValidTill().toString());
                        }
                        if(emp.getDateOfBirth() != null) {
                            details.put("dateOfBirth", emp.getDateOfBirth().toString());
                        }
                    }
                });
            }
            return details;
        }
        return null;
    }

    @Override
    public List<DefaultPartner> getEmployeesWhoAreNotUsers(String tenantId) {
        return partnerRepository.getEmployeesWhoAreNotUsers(tenantId);
    }
}