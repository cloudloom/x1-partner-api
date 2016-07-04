package com.tracebucket.x1.partner.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultValidity;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.repository.jpa.DefaultPartnerRepository;
import com.tracebucket.x1.partner.api.rest.exception.PartnerException;
import com.tracebucket.x1.partner.api.rest.resources.DefaultNotifyTo;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerPositionAndOrganizationUnitResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerUsername;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

/**
 * Created by sadath on 26-May-2015.
 * DefaultPartnerService Implementation
 */
@Service
@Transactional
public class DefaultPartnerServiceImpl implements DefaultPartnerService {
    
    @Autowired
    private DefaultPartnerRepository partnerRepository;

    @Autowired
    private Mapper mapper;

    /**
     * Save
     * @param tenantId
     * @param partner
     * @return DefaultPartner
     */
    @Override
    public DefaultPartner save(String tenantId, DefaultPartner partner) {
        Set<DefaultPartnerRole> roles = partner.getAllAssignedRoles();
        if(roles != null && roles.size() > 0) {
            roles.stream().forEach(role -> {
                if(role instanceof DefaultEmployee) {
                    DefaultEmployee employee = (DefaultEmployee) role;
                    DefaultValidity validity = employee.getValidity();
                    if(validity != null && validity.getValidFrom() != null && validity.getValidTill() != null) {
                        //Validate Validity Of The User, If Valid From Is Greater Than Valid Till Throw An Exception
                        if(validity.getValidFrom().after(validity.getValidTill())) {
                            throw new PartnerException("Valid From Should Be Less Than Valid Till.", HttpStatus.PRECONDITION_REQUIRED);
                        }
                    }
                    //validate Date Of Birth, Date Of Birth Cannot Be In Future
                    if(employee.getDateOfBirth() != null) {
                        if(employee.getDateOfBirth().after(new Date())) {
                            throw new PartnerException("Date Of Birth Cannot Be In Future.", HttpStatus.PRECONDITION_REQUIRED);
                        }
                    }
                }
            });
        }
        //set owner
        partner.setOwner(new DefaultOwner(tenantId));
        return partnerRepository.save(partner);
    }

    /**
     * Fine One
     * @param tenantId
     * @param aggregateId
     * @return DefaultPartner
     */
    @Override
    public DefaultPartner findOne(String tenantId, AggregateId aggregateId) {
        DefaultPartner partner = partnerRepository.findOne(aggregateId, tenantId);
/*        if(partner != null) {
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            if (partnerRoles != null && partnerRoles.size() > 0) {
                partnerRoles.stream().forEach(role -> {
                    if (role instanceof DefaultEmployee) {
                        DefaultEmployee employee = (DefaultEmployee) role;
                        if (employee.getNotifyTo() != null && employee.getNotifyTo().size() > 0) {
                            Set<String> mgr = employee.getNotifyTo();
                            Map<String, String> m = new HashMap<String, String>();
                            mgr.stream().forEach(mg -> {
                                DefaultPartner partner1 = findOne(tenantId, new AggregateId(mg));
                                if (partner1 != null) {
                                    Set<DefaultPartnerRole> roles = partner1.getAllAssignedRoles();
                                    if (roles != null && roles.size() > 0) {
                                        roles.stream().forEach(role1 -> {
                                            if (role1 instanceof DefaultEmployee) {
                                                DefaultEmployee employee1 = (DefaultEmployee) role1;
                                                String firstName = employee1.getFirstName();
                                                String middleName = employee1.getMiddleName();
                                                String lastName = employee1.getLastName();
                                                m.put(mg, firstName + (middleName != null ? (" " + middleName) : "") + (" " + lastName));
                                            }
                                        });
                                    }
                                }
                            });
                            if (m.size() > 0) {
                                employee.setNotificationsTo(m);
                            }
                        }
                    }
                });
            }
        }*/
        return partner;
    }

    /**
     * Get Employee
     * @param tenantId
     * @param aggregateId
     * @param entityId
     * @return DefaultEmployee
     */
    @Override
    public DefaultEmployee getEmployee(String tenantId, AggregateId aggregateId, EntityId entityId) {
        //fetch partner
        DefaultPartner partner = partnerRepository.findOne(aggregateId, tenantId);
        //if fetched
        if(partner != null) {
            //get all partner roles
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            if(partnerRoles != null && partnerRoles.size() > 0) {
                //stream, filter and find partner with role of employee
                DefaultPartnerRole partnerRole = partnerRoles.stream().filter(role -> role.getEntityId().equals(entityId)).findFirst().orElse(null);
                if(partnerRole != null && partnerRole instanceof DefaultEmployee) {
                    return (DefaultEmployee)partnerRole;
                }
            }
        }
        return null;
    }

    /**
     * Get Employees By Login Names
     * @param tenantId
     * @param userNames
     * @return List<DefaultPartner>
     */
    @Override
    public List<DefaultPartner> getEmployeesByLoginNames(String tenantId, List<String> userNames) {
        return partnerRepository.getEmployeesByLoginNames(tenantId, userNames);
    }

    /**
     * Is User An Employee
     * @param tenantId
     * @param userName
     * @return Boolean
     */
    @Override
    public Boolean isUserAnEmployee(String tenantId, String userName) {
        //get employees by login name
        List<DefaultPartner> employees = partnerRepository.getEmployeesByLoginNames(tenantId, Arrays.asList(userName));
        //if found, it means that there is an employee with userName
        if(employees != null && employees.size() > 0)
            return true;
        return false;
    }

    /**
     * Get Employees User Name By Partner UIDS
     * @param tenantId
     * @param partnerUIDS
     * @return Map<String, String>
     */
    @Override
    public Map<String, String> getEmployeesUserNameByPartnerUIDS(String tenantId, List<String> partnerUIDS) {
        //if partnerUIDS found
        if(partnerUIDS != null && partnerUIDS.size() > 0) {
            //map to hold partnerUid and username, key:partnerUid, value:username
            Map<String, String> userNames = new HashMap<String, String>();
            //stream and for each partneruid get its roles and if role is employee get its username and put it into map
            partnerUIDS.stream().forEach(partnerUid -> {
                DefaultPartner partner = partnerRepository.findOne(new AggregateId(partnerUid), tenantId);
                if(partner != null && partner.getAllAssignedRoles() != null) {
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    partnerRoles.stream().forEach(partnerRole -> {
                        if(partnerRole instanceof DefaultEmployee) {
                            DefaultEmployee employee = (DefaultEmployee) partnerRole;
                            userNames.put(partner.getAggregateId().getAggregateId(), employee.getUserName());
                        }
                    });
                }
            });
            return userNames;
        }
        return null;
    }

    /**
     * Notify To By Username
     * @param tenantId
     * @param userName
     * @return DefaultNotifyTo
     */
    @Override
    public DefaultNotifyTo notifyToByUsername(String tenantId, String userName) {
        //add incoming userName to userNames
        List<String> userNames = new ArrayList<String>();
        userNames.add(userName);
        //get employees by loginNames
        List<DefaultPartner> employees = partnerRepository.getEmployeesByLoginNames(userNames);
        //if found
        if(employees != null && employees.size() == 1) {
            //there will be only one employee for a user name, get first
            DefaultPartner partner = employees.get(0);
            //get all partner roles
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            //if partnerRoles found
            if(partnerRoles != null && partnerRoles.size() > 0) {
                //for each partner role check if role is employee
                for(DefaultPartnerRole partnerRole : partnerRoles) {
                    if(partnerRole instanceof DefaultEmployee) {
                        DefaultEmployee employee = (DefaultEmployee) partnerRole;
                        //get notifyTo, which contains partnerUid of reporting managers
                        Set<String> notifyTo = employee.getNotifyTo();
                        if(notifyTo != null && notifyTo.size() > 0) {
                            //reporting managers emails and phone nos
                            DefaultNotifyTo defaultNotifyTo = new DefaultNotifyTo();
                            //for each reporting manager, get their emails and phone nos, add to defaultNotifyTo and return
                            notifyTo.stream().forEach(to -> {
                                DefaultPartner reportingManager = partnerRepository.findOne(new AggregateId(to));
                                if(reportingManager != null) {
                                    Set<DefaultPartnerRole> partnerRoles1 = reportingManager.getAllAssignedRoles();
                                    if(partnerRoles1 != null && partnerRoles1.size() > 0) {
                                        partnerRoles1.stream().forEach(partnerRole1 -> {
                                            if(partnerRole1 instanceof DefaultEmployee) {
                                                DefaultEmployee reportingEmployee = (DefaultEmployee) partnerRole1;
                                                Set<DefaultEmail> emails = reportingEmployee.getEmail();
                                                if(emails != null && emails.size() > 0) {
                                                    emails.stream().forEach(email -> {
                                                        defaultNotifyTo.getEmails().add(email.getEmail());
                                                    });
                                                }
                                                Set<DefaultPhone> phones = reportingEmployee.getPhone();
                                                if(phones != null && phones.size() > 0) {
                                                    phones.stream().forEach(phone -> {
                                                        defaultNotifyTo.getPhoneNos().add(phone.getNumber());
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                            return defaultNotifyTo;
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * Find All Partners
     * @param tenantId
     * @return List<DefaultPartner>
     */
    @Override
    public List<DefaultPartner> findAll(String tenantId) {
        List<DefaultPartner> partners = partnerRepository.findAll(tenantId);
/*        if(partners != null && partners.size() > 0) {
            partners.stream().forEach(partner -> {
                Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                if(partnerRoles != null && partnerRoles.size() > 0) {
                    partnerRoles.stream().forEach(role -> {
                        if(role instanceof DefaultEmployee) {
                            DefaultEmployee employee = (DefaultEmployee) role;
                            if(employee.getNotifyTo() != null && employee.getNotifyTo().size() > 0) {
                                Set<String> mgr = employee.getNotifyTo();
                                Map<String, String> m = new HashMap<String, String>();
                                mgr.stream().forEach(mg -> {
                                    DefaultPartner partner1 = findOne(tenantId, new AggregateId(mg));
                                    if(partner1 != null) {
                                        Set<DefaultPartnerRole> roles = partner1.getAllAssignedRoles();
                                        if(roles != null && roles.size() > 0) {
                                            roles.stream().forEach(role1 -> {
                                                if(role1 instanceof DefaultEmployee) {
                                                    DefaultEmployee employee1 = (DefaultEmployee) role1;
                                                    String firstName = employee1.getFirstName();
                                                    String middleName = employee1.getMiddleName();
                                                    String lastName = employee1.getLastName();
                                                    m.put(mg, firstName + (middleName != null ? (" " + middleName) : "" )+ (" " + lastName));
                                                }
                                            });
                                        }
                                    }
                                });
                                if(m.size() > 0) {
                                    employee.setNotificationsTo(m);
                                }
                            }
                        }
                    });
                }
            });
        }*/
        return partners;
    }

    /**
     * Delete Partner
     * @param tenantId
     * @param partnerAggregateId
     * @return boolean
     */
    @Override
    public boolean delete(String tenantId, AggregateId partnerAggregateId) {
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //delete
            partnerRepository.delete(partner.getAggregateId(), tenantId);
            return partnerRepository.findOne(partnerAggregateId, tenantId) == null ? true : false;
        }
        return false;
    }

    /**
     * Set Partner Category
     * @param tenantId
     * @param partnerCategory
     * @param partnerAggregateId
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner setPartnerCategory(String tenantId, PartnerCategory partnerCategory, AggregateId partnerAggregateId){
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //set partner category
            partner.setPartnerCategory(partnerCategory);
            return partner;
        }
        return null;
    }

    /**
     * Update / Move Partner Category
     * @param tenantId
     * @param newPartnerCategory
     * @param partnerAggregateId
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner movePartnerToCategory(String tenantId, PartnerCategory newPartnerCategory,AggregateId partnerAggregateId){
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //set partner category
            partner.setPartnerCategory(newPartnerCategory);
            return partner;
        }
        return null;
    }

    /**
     * Add Partner Role
     * @param tenantId
     * @param addPartnerRole
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPartnerRole(String tenantId, DefaultPartner addPartnerRole){
        //find partner
        DefaultPartner partner = partnerRepository.findOne(addPartnerRole.getAggregateId(), tenantId);
        //if partner found
        if(partner != null) {
            //get all assigned roles of incoming partner role
            Set<DefaultPartnerRole> partnerRoles = addPartnerRole.getAllAssignedRoles();
            //for each incoming partner role, add role to found partner
            if(partnerRoles != null && partnerRoles.size() > 0) {
                partnerRoles.parallelStream().forEach(role -> {
                    partner.addPartnerRole(role);
                });
            }
            return partner;
        }
        return null;
    }

    /**
     * Update Partner Role
     * @param tenantId
     * @param updatePartnerRole
     * @param partnerRoleEntityId
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner updatePartnerRole(String tenantId, DefaultPartner updatePartnerRole, EntityId partnerRoleEntityId) {
        //get all incoming partnerRoles to be updated
        Set<DefaultPartnerRole> roles = updatePartnerRole.getAllAssignedRoles();
        //for each incoming partnerRole to be updated, if role is employee validate validity and date of birth of employee, if validation fails throw an exception
        if(roles != null && roles.size() > 0) {
            roles.stream().forEach(role -> {
                if(role instanceof DefaultEmployee) {
                    DefaultEmployee employee = (DefaultEmployee) role;
                    DefaultValidity validity = employee.getValidity();
                    if(validity != null && validity.getValidFrom() != null && validity.getValidTill() != null) {
                        if(validity.getValidFrom().after(validity.getValidTill())) {
                            throw new PartnerException("Valid From Should Be Less Than Valid Till.", HttpStatus.PRECONDITION_REQUIRED);
                        }
                    }
                    if(employee.getDateOfBirth() != null) {
                        if(employee.getDateOfBirth().after(new Date())) {
                            throw new PartnerException("Date Of Birth Cannot Be In Future.", HttpStatus.PRECONDITION_REQUIRED);
                        }
                    }
                }
            });
        }
        //find partner
        DefaultPartner partner = partnerRepository.findOne(updatePartnerRole.getAggregateId(), tenantId);
        //if partner found
        if(partner != null) {
            //get all incoming partners' all assigned roles
            Set<DefaultPartnerRole> partnerRoles = updatePartnerRole.getAllAssignedRoles();
            if(partnerRoles != null && partnerRoles.size() > 0) {
                //filter the role to be update
                DefaultPartnerRole updateRole = partnerRoles.stream()
                        .filter(t -> t.getEntityId().equals(partnerRoleEntityId))
                        .findFirst().get();
                //if role to be updated found
                if(updateRole != null) {
                    //map new values
                    partner.updatePartnerRole(updateRole, mapper);
                }
            }
            return partner;
        }
        return null;
    }

    /**
     * Add Address To Role
     * @param tenantId
     * @param partnerRoleEntityId
     * @param address
     * @param partnerAggregateId
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addAddressToRole(String tenantId, EntityId partnerRoleEntityId, Address address, AggregateId partnerAggregateId){
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //get all assigned roles
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            //if roles found
            if(partnerRoles != null) {
                //for each role, find role to which address is to be added, if found add address to it
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

    /**
     * Move Role Address To
     * @param tenantId
     * @param partnerRoleEntityId
     * @param newAddress
     * @param partnerAggregateId
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner moveRoleAddressTo(String tenantId, EntityId partnerRoleEntityId, Address newAddress, AggregateId partnerAggregateId){
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //get all assigned roles
            Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
            //if roles found
            if(partnerRoles != null) {
                //for each role, find role of which address is to be updated, if found update
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

    /**
     * Change Owner
     * @param tenantId
     * @param newOwner
     * @param partnerAggregateId
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner changeOwner(String tenantId, DefaultOwner newOwner, AggregateId partnerAggregateId){
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //update owner
            partner.changeOwner(newOwner);
            return partner;
        }
        return null;
    }

    /**
     * Has Partner Role
     * @param tenantId
     * @param partnerAggregateId
     * @param roleEntityId
     * @return Boolean
     */
    @Override
    public Boolean hasPartnerRole(String tenantId, AggregateId partnerAggregateId, EntityId roleEntityId){
        //is partner role found
        Long found = null;
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if partner found
        if(partner != null) {
            //check if role is found
            if(partner.getAllAssignedRoles() != null && partner.getAllAssignedRoles().size() > 0) {
                found = partner.getAllAssignedRoles().parallelStream()
                        .filter(t -> t.getEntityId().getId().equals(roleEntityId.getId()))
                        .count();
            }
        }
        //if found is greater than 0 role was found
        return (found != null && found > 0) ? true : false;
    }

    /**
     * Find Partners By Organization
     * @param organizationUid
     * @return List<DefaultPartner>
     */
    @Override
    public List<DefaultPartner> findPartnersByOrganization(String organizationUid) {
        return partnerRepository.findPartnersByOrganization(organizationUid);
    }

    /**
     * Add Position
     * @param tenantId
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param positionUid
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPosition(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid) {
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if partner found
        if(partner != null) {
            //add position
            partner.addPosition(partnerRoleUid, positionUid);
            return partner;
        }
        return null;
    }

    /**
     * Search Partners
     * @param tenantId
     * @param organizationAggregateId
     * @param searchTerm
     * @return Set<DefaultPartner>
     */
    @Override
    public Set<DefaultPartner> searchPartners(String tenantId, AggregateId organizationAggregateId, String searchTerm) {
        if(tenantId.equals(organizationAggregateId.getAggregateId())) {
            //get partners by organization
            List<DefaultPartner> partners = findPartnersByOrganization(tenantId);
            //track found partners
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
                                }else if(employee.getFirstName() != null && employee.getFirstName().toLowerCase().matches(searchTerm)) {
                                    foundPartners.add(p);
                                }
                                else if(employee.getMiddleName() != null && employee.getMiddleName().toLowerCase().matches(searchTerm)){
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

    /**
     * Check If Position Is Assigned
     * @param tenantId
     * @param organizationUid
     * @param positionUid
     * @param organizationUnitUid
     * @return Boolean
     */
    @Override
    public Boolean isPositionAssigned(String tenantId, String organizationUid, String positionUid, String organizationUnitUid) {
        Boolean innerBreak = false;
        Boolean outerBreak = false;
        if(tenantId.equals(organizationUid)) {
            //findPartners By Organization
            List<DefaultPartner> partners = findPartnersByOrganization(tenantId);
            //if partners found
            if(partners != null) {
                //for each found partner
                for(DefaultPartner partner : partners) {
                    //get all assigned roles
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    //if assigned roles found
                    if(partnerRoles != null) {
                        //for each assigned role
                        for(DefaultPartnerRole partnerRole : partnerRoles) {
                            //if role is employee
                            if (partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                //check if organizationUid, organziationUnitUid and positionUid match, if matched break the loop
                                if (employee.getOrganizationUnit() != null && employee.getOrganizationUnit().equals(organizationUnitUid)
                                        && employee.getPosition() != null && employee.getPosition().equals(positionUid)) {
                                    innerBreak = true;
                                    break;
                                }
                            }
                        }
                    }
                    //if innerBreak is true
                    if(innerBreak) {
                        //break outer loop
                        outerBreak = true;
                        break;
                    }
                }
                //return true if found
                return outerBreak;
            }
        }
        return null;
    }

    /**
     * Is OrganizationUnit Assigned
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @return Boolean
     */
    @Override
    public Boolean isOrganizationUnitAssigned(String tenantId, String organizationUid, String organizationUnitUid) {
        Boolean innerBreak = false;
        Boolean outerBreak = false;
        if(tenantId.equals(organizationUid)) {
            //find partners by organization
            List<DefaultPartner> partners = findPartnersByOrganization(tenantId);
            //if partners found
            if(partners != null) {
                //for each partner
                for(DefaultPartner partner : partners) {
                    //get all assigned roles
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    //if assigned roles found
                    if(partnerRoles != null) {
                        //for each assigned role
                        for(DefaultPartnerRole partnerRole : partnerRoles) {
                            //if role is employee
                            if (partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                //check if it matches organizationUnit
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
                //return true if assigned
                return outerBreak;
            }
        }
        return null;
    }

    /**
     * Add Position And Organization
     * @param tenantId
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param positionUid
     * @param organizationUnitUid
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPositionAndOrganization(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid, EntityId organizationUnitUid) {
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if partner found
        if (partner != null) {
            //add position and organization
            partner.addPositionAndOrganization(partnerRoleUid, positionUid, organizationUnitUid);
            return partner;
        }
        return null;
    }

    /**
     * Add Position And Organization To Multiple Partners
     * @param tenantId
     * @param organizationUid
     * @param resource
     * @return List<DefaultPartner>
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public List<DefaultPartner> addPositionAndOrganization(String tenantId, String organizationUid, List<DefaultPartnerPositionAndOrganizationUnitResource> resource) {
        //if tenantId equals organizationUid
        if(tenantId.equals(organizationUid)) {
            if (resource != null) {
                //partners to which position and organization unit is assigned
                List<DefaultPartner> partners = new ArrayList<DefaultPartner>();
                //for each incoming resource
                resource.stream().forEach(res -> {
                    //find partner
                    DefaultPartner partner = partnerRepository.findOne(new AggregateId(res.getPartnerUid()), tenantId);
                    //if partner found, add position and organization
                    if (partner != null) {
                        partner.addPositionAndOrganization(new EntityId(res.getRoleUid()), new EntityId(res.getPositionUid()), new EntityId(res.getOrganizationUnitUid()));
                        partners.add(partner);
                    }
                });
                //if position and organization assigned
                if(partners.size() > 0) {
                    return partners;
                }
            }
        }
        return null;
    }

    /**
     * Get Employees Assigned To And Not Assigned To Organization And Position
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @param positionUid
     * @return Map<Boolean, Set<DefaultPartner>>
     *     key: true, value: partners assigned to organizationUnit and position
     *     key:false, value: partnets not assigned to organizationUnit and position
     */
    @Override
    public Map<Boolean, Set<DefaultPartner>> getEmployeesAssignedAndNotToOrganizationAndPosition(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid) {
        if(tenantId.equals(organizationUid.getAggregateId())) {
            //track assigned and not assigned partners
            Map<Boolean, Set<DefaultPartner>> partners = new HashMap<Boolean, Set<DefaultPartner>>();
            //get all assigned partners
            List<DefaultPartner> partnersAssigned = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(organizationUid.getAggregateId(), organizationUnitUid.getId(), positionUid.getId());
            //get all not assigned partners
            List<DefaultPartner> partnersNotAssigned = partnerRepository.getEmployeesNotAssignedToOrganizationAndPosition(organizationUid.getAggregateId(), organizationUnitUid.getId());
            //if partnersAssigned found
            if(partnersAssigned != null) {
                partners.put(true, new HashSet<>(partnersAssigned));
            }
            //if partnersAssigned not found
            if(partnersNotAssigned != null) {
                partners.put(false, new HashSet<>(partnersNotAssigned));
            }
            return partners;
        }
        return null;
    }

    /**
     * Get Employees Assigned To Organization And Position
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @param positionUid
     * @return Set<DefaultPartner>
     */
    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationAndPosition(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid) {
        //get employees assigned to organization and position
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(organizationUid.getAggregateId(), organizationUnitUid.getId(), positionUid.getId());
        //if employees found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Employees Assigned To Organization Unit
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @return Set<DefaultPartner>
     */
    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationUnit(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid) {
        //get employees assigned to organization unit
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationUnit(organizationUid.getAggregateId(), organizationUnitUid.getId());
        //if employees found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Employees Assigned To Organization And Position
     * @param tenantId
     * @param organizationUid
     * @return Map<String, Map<String, ArrayList<DefaultPartner>>>
     *     outer map key: organizationUnit
     *     inner map key: position
     */
    @Override
    public Map<String, Map<String, ArrayList<DefaultPartner>>> getEmployeesAssignedToOrganizationAndPosition(String tenantId, AggregateId organizationUid) {
        if(tenantId.equals(organizationUid.getAggregateId())) {
            //getEmployeesAssignedToOrganizationAndPosition
            List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(tenantId);
            //if employees found
            if(partners != null) {
                //track employees belonging to organizationUnit and Position
                Map<String, Map<String, ArrayList<DefaultPartner>>> employees = new HashMap<String, Map<String, ArrayList<DefaultPartner>>>();
                //for each partner
                partners.stream().forEach(partner -> {
                    //get all assigned roles
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    //if assigned roles found
                    if (partnerRoles != null) {
                        //for each role
                        partnerRoles.stream().forEach(partnerRole -> {
                            //if role is employee
                            if (partnerRole instanceof DefaultEmployee) {
                                DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                //check if employees contains organizationunit
                                if (employees.containsKey(employee.getOrganizationUnit())) {
                                    //get positions by organizationUnit
                                    Map<String, ArrayList<DefaultPartner>> positions = employees.get(employee.getOrganizationUnit());
                                    //is positions contains incoming position
                                    if (positions.containsKey(employee.getPosition())) {
                                        //add employee to that position
                                        positions.get(employee.getPosition()).add(partner);
                                    } else {
                                        //if positions does not contain incoming position then add one
                                        ArrayList<DefaultPartner> defaultEmployees = new ArrayList<DefaultPartner>();
                                        defaultEmployees.add(partner);
                                        positions.put(employee.getPosition(), defaultEmployees);
                                    }
                                }
                                //if employees does not contain organizationUnit, then add it
                                else {
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
                //if employees found, then return
                if(employees.size() > 0) {
                    return employees;
                }
            }
        }
        return null;
    }

    /**
     * Restructure Employees
     * @param tenantId
     * @param organizationUid
     * @param employeeStructure
     * @return Set<DefaultPartner>
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public Set<DefaultPartner> restructureEmployees(String tenantId, AggregateId organizationUid, HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> employeeStructure) {
        if(tenantId.equals(organizationUid.getAggregateId())) {
            //employee structure outer map key : orgUnit
            //employee structure inner map key : position
            //employee structure inner map value : List Of Map, map key:partnerUid, value:roleUid
            //if employeeStructure is not null
            if(employeeStructure != null) {
                //track partners whose structure is to be updated
                Set<DefaultPartner> defaultPartners = new HashSet<DefaultPartner>();
                //for each orgUnit
                employeeStructure.entrySet().stream().forEach(orgUnit -> {
                   /* if(tenantId.equals(orgUnit.getKey())) {*/
                    //get its positions
                        HashMap<String, ArrayList<Map<String, String>>> positions = orgUnit.getValue();
                    //if positions found
                        if(positions != null) {
                            //for each postion
                            positions.entrySet().stream().forEach(position -> {
                                //get employee list
                                ArrayList<Map<String, String>> employeesList = position.getValue();
                                //if employee list found
                                if(employeesList != null && employeesList.size() > 0) {
                                    //for each employee
                                    employeesList.stream().forEach(employees -> {
                                        employees.entrySet().stream().forEach(employee -> {
                                            //get employee
                                            DefaultPartner partner = findOne(tenantId, new AggregateId(employee.getKey()));
                                            //if employee found
                                            if(partner != null) {
                                                //add postion and organization
                                                partner.addPositionAndOrganization(new EntityId(employee.getValue()), new EntityId(position.getKey()), new EntityId(orgUnit.getKey()));
                                                //track updated employee in defaultPartners
                                                defaultPartners.add(partner);
                                            }
                                        });
                                    });
                                }
                            });
                        }
                    /*}*/
                });
                //get current employees assigned to organization and position
                // iterate and match with the incoming structure, set position and organizationUnit as null for those employees which are not found in current structure
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
                                //employee not found
                                if (!found) {
                                    //remove position and organization
                                    partner.removePositionAndOrganization();
                                    defaultPartners.add(partner);
                                }
                            });
                        });
                    });
                }
                //if updated employees found, then save
                if(defaultPartners.size() > 0) {
                    return defaultPartners;
                }
            }
        }
        return null;
    }

    /**
     * Add Employees To Position
     * @param tenantId
     * @param organizationUid
     * @param employeeStructure
     * @return Set<DefaultPartner>
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public Set<DefaultPartner> addEmployeesToPositions(String tenantId, AggregateId organizationUid, HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> employeeStructure) {
        //if tenantId equals organizationUid
        if(tenantId.equals(organizationUid.getAggregateId())) {
            //if incoming employee structure is not null
            if(employeeStructure != null) {
                //track employees whose structure is to be updated
                Set<DefaultPartner> defaultPartners = new HashSet<DefaultPartner>();
                //for each orgUnit
                employeeStructure.entrySet().stream().forEach(orgUnit -> {
                    //get its positions
                    HashMap<String, ArrayList<Map<String, String>>> positions = orgUnit.getValue();
                    //if positions found
                    if(positions != null) {
                        //foreach position
                        positions.entrySet().stream().forEach(position -> {
                            //get employees details
                            ArrayList<Map<String, String>> employeesList = position.getValue();
                            //if employee details found
                            if (employeesList != null && employeesList.size() > 0) {
                                //foreach employee details
                                employeesList.stream().forEach(employees -> {
                                    //for each employee
                                    employees.entrySet().stream().forEach(employee -> {
                                        //find partner by employee key which contains partnerUid
                                        DefaultPartner partner = findOne(tenantId, new AggregateId(employee.getKey()));
                                        //if employee found
                                        if (partner != null) {
                                            //set its position and organization
                                                partner.addPositionAndOrganization(new EntityId(employee.getValue()), new EntityId(position.getKey()), new EntityId(orgUnit.getKey()));
                                            //add to defaultPartners where updated employees are tracked
                                            defaultPartners.add(partner);
                                        }
                                    });
                                });
                            }
                        });
                    }
                });
                //get organizationUnit and position
                String orgUnitUid1 = null;
                String posUid1 = null;
                for(Map.Entry<String, HashMap<String, ArrayList<Map<String, String>>>> entrySet : employeeStructure.entrySet()) {
                    orgUnitUid1 = entrySet.getKey();
                    if(entrySet.getValue() != null) {
                        HashMap<String, ArrayList<Map<String, String>>> positionEntry = entrySet.getValue();
                        for(Map.Entry<String, ArrayList<Map<String, String>>> entry : positionEntry.entrySet()) {
                            posUid1 = entry.getKey();
                            break;
                        }
                    }
                }
                //get current employees assigned to organizationUnit And Position
                List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationAndPosition(organizationUid.getAggregateId(), orgUnitUid1, posUid1);
                //if current employees found
                if(partners != null && partners.size() > 0) {
                    //track current employees that are to be updated
                    //inner map key: positionUid
                    //outer map key: organizationUnitUid
                    Map<String, Map<String, ArrayList<DefaultPartner>>> currentEmployees = new HashMap<String, Map<String, ArrayList<DefaultPartner>>>();
                    //for each current employee
                    partners.stream().forEach(partner -> {
                        //get all assigned roles
                        Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                        //if roles found
                        if (partnerRoles != null) {
                            //foreach role
                            partnerRoles.stream().forEach(partnerRole -> {
                                //if role is employee
                                if (partnerRole instanceof DefaultEmployee) {
                                    DefaultEmployee employee = (DefaultEmployee) partnerRole;
                                    //if currentEmployees contains organizationUnitUid
                                    if (currentEmployees.containsKey(employee.getOrganizationUnit())) {
                                        //get its positions
                                        Map<String, ArrayList<DefaultPartner>> positions = currentEmployees.get(employee.getOrganizationUnit());
                                        //if positions contains positionUid
                                        if (positions.containsKey(employee.getPosition())) {
                                            //add partner to positions
                                            positions.get(employee.getPosition()).add(partner);
                                        }
                                        //if positions does not contain positionUid
                                        else {
                                            ArrayList<DefaultPartner> defaultEmployees = new ArrayList<DefaultPartner>();
                                            defaultEmployees.add(partner);
                                            //add position uid and employee to positions
                                            positions.put(employee.getPosition(), defaultEmployees);
                                        }
                                    }
                                    //if currentEmployees does not contains organizationUnitUid
                                    else {
                                        //positionEmployees to add new position and employees to it
                                        Map<String, ArrayList<DefaultPartner>> positionEmployees = new HashMap<String, ArrayList<DefaultPartner>>();
                                        ArrayList<DefaultPartner> defaultEmployees = new ArrayList<DefaultPartner>();
                                        defaultEmployees.add(partner);
                                        //add employee position and employees to it
                                        positionEmployees.put(employee.getPosition(), defaultEmployees);
                                        //set organizationUnit and positions
                                        currentEmployees.put(employee.getOrganizationUnit(), positionEmployees);
                                    }
                                }
                            });
                        }
                    });
                    //if current employee structure is built
                    //check if employees have been removed from the current structure
                    //iterate currentEmployees structure and iterate incoming employeeStructure, match employee ids, if match found employee is not removed, else employee is removed
                    if(currentEmployees.size() > 0) {
                        currentEmployees.entrySet().forEach(orgUnitUid -> {
                            orgUnitUid.getValue().entrySet().forEach(posUid -> {
                                posUid.getValue().forEach(partner -> {
                                    boolean found = false;
                                    for (Map.Entry<String, HashMap<String, ArrayList<Map<String, String>>>> orgEntry : employeeStructure.entrySet()) {
                                        for (Map.Entry<String, ArrayList<Map<String, String>>> posEntry : orgEntry.getValue().entrySet()) {
                                            for (Map<String, String> empEntry : posEntry.getValue()) {
                                                for (Map.Entry<String, String> emp : empEntry.entrySet()) {
                                                    if (partner.getAggregateId().getAggregateId().equals(emp.getKey())) {
                                                        //employee is not removed
                                                        found = true;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    //employee removed
                                    if (!found) {
                                        //remove its department and position and organization unit
                                        partner.removeDepartmentAndPositionAndOrganization();
                                        //employee modified add to tracked list
                                        defaultPartners.add(partner);
                                    }
                                });
                            });
                        });
                    }
                }
                //if employees updated found, save
                if(defaultPartners.size() > 0) {
                    return defaultPartners;
                }
            }
        }
        return null;
    }

    /**
     * Add Department
     * @param tenantId
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param departmentUid
     * @return DefaultPartner
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addDepartment(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId departmentUid) {
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId, tenantId);
        //if found
        if(partner != null) {
            //add department
            partner.addDepartment(partnerRoleUid, departmentUid);
            return partner;
        }
        return null;
    }

    /**
     *  Add Department Position And OrganizationUnit
     * @param tenantId
     * @param partnerUid
     * @param roleUid
     * @param organizationUnitUid
     * @param positionUid
     * @param departmentUid
     * @return
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addDepartmentPositionAndOrganizationUnit(String tenantId, AggregateId partnerUid, EntityId roleUid, String organizationUnitUid, String positionUid, String departmentUid) {
        //find partner
        DefaultPartner partner = partnerRepository.findOne(partnerUid, tenantId);
        //if found
        if(partner != null) {
            //add department, position and organizationUnit
            partner.addDepartmentPositionAndOrganizationUnit(roleUid, organizationUnitUid, positionUid, departmentUid);
            return partner;
        }
        return null;
    }

    /**
     * Get Employees Assigned To OrganizationUnit And Department
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @param departmentUid
     * @return Set<DefaultPartner>
     */
    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationUnitAndDepartment(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId departmentUid) {
        //getEmployeesAssignedToOrganizationUnitAndDepartment
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationUnitAndDepartment(organizationUid.getAggregateId(), organizationUnitUid.getId(), departmentUid.getId());
        //if found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Employee Who Are Users And Assigned To OrganizationUnit And Department
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @param departmentUid
     * @return
     */
    @Override
    public Set<DefaultPartner> getEmployeeUsersAssignedToOrganizationUnitAndDepartment(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId departmentUid) {
        //getEmployeeUsersAssignedToOrganizationUnitAndDepartment
        List<DefaultPartner> partners = partnerRepository.getEmployeeUsersAssignedToOrganizationUnitAndDepartment(organizationUid.getAggregateId(), organizationUnitUid.getId(), departmentUid.getId());
        //if found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Employee Who Are Users And Assigned To OrganizationUnit
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @return
     */
    @Override
    public Set<DefaultPartner> getEmployeeUsersAssignedToOrganizationUnit(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid) {
        //getEmployeeUsersAssignedToOrganizationUnit
        List<DefaultPartner> partners = partnerRepository.getEmployeeUsersAssignedToOrganizationUnit(organizationUid.getAggregateId(), organizationUnitUid.getId());
        //if found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Employees Who Are Users Of An Organization
     * @param tenantId
     * @param organizationUid
     * @return Set<DefaultPartner>
     */
    @Override
    public Set<DefaultPartner> getEmployeeUsers(String tenantId, AggregateId organizationUid) {
        //get employees who are users of an organization
        List<DefaultPartner> partners = partnerRepository.getEmployeeUsers(organizationUid.getAggregateId());
        //if found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Employees Assigned To OrganizationUnit And Position And Department
     * @param tenantId
     * @param organizationUid
     * @param organizationUnitUid
     * @param positionUid
     * @param departmentUid
     * @return Set<DefaultPartner>
     */
    @Override
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid, EntityId departmentUid) {
        //getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment
        List<DefaultPartner> partners = partnerRepository.getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment(organizationUid.getAggregateId(), organizationUnitUid.getId(), positionUid.getId(), departmentUid.getId());
        //if found
        if(partners != null && partners.size() > 0) {
            return new HashSet<>(partners);
        }
        return null;
    }

    /**
     * Get Logged In Employee Details
     * @param tenantId
     * @param username
     * @return
     */
    @Override
    public DefaultPartner getLoggedInEmployeeDetails(String tenantId, String username) {
        //return logged in employee details
        return partnerRepository.getLoggedInEmployeeDetails(tenantId, username);
    }

    /**
     * Get Logged In Employee Minimal Details
     * @param tenantId
     * @param username
     * @return Map<String, String>
     */
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
                        if(emp.getOrganizationUnit() != null) {
                            details.put("organizationUnitUid", emp.getOrganizationUnit());
                        }
                        if(emp.getDepartment() != null) {
                            details.put("departmentUid", emp.getDepartment());
                        }
                        if(emp.getPosition() != null) {
                            details.put("positionUid", emp.getPosition());
                        }
                        if(emp.getUserName() != null) {
                            details.put("username", emp.getUserName());
                        }
                        if(emp.getFirstName() != null) {
                            details.put("firstName", emp.getFirstName());
                        }
                        if(emp.getMiddleName() != null) {
                            details.put("middleName", emp.getMiddleName());
                        }
                        if(emp.getLastName() != null) {
                            details.put("lastName", emp.getLastName());
                        }
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

    /**
     * Get Employees Who Are Not Users
     * @param tenantId
     * @return
     */
    @Override
    public List<DefaultPartner> getEmployeesWhoAreNotUsers(String tenantId) {
        return partnerRepository.getEmployeesWhoAreNotUsers(tenantId);
    }

    /**
     * Add User Name
     * @param tenantId
     * @param userNames
     * @return Set<DefaultPartner>
     */
    @Override
    @PersistChanges(repository = "partnerRepository")
    public Set<DefaultPartner> addUsername(String tenantId, List<DefaultPartnerUsername> userNames) {
        //if incoming userNames found
        if(userNames != null && userNames.size() > 0) {
            //track all employees to whom username was added
            Set<DefaultPartner> updateEmployeesUsername = new HashSet<DefaultPartner>();
            //for each incoming user name
            userNames.stream().forEach(user -> {
                Map<String, String> details = user.getUserName();
                if(details.containsKey("partnerUid") && details.containsKey("roleUid") && details.containsKey("userName")) {
                    //find partner
                    DefaultPartner partner = partnerRepository.getEmployee(tenantId, details.get("partnerUid"), details.get("roleUid"));
                    //if partner found
                    if(partner != null) {
                        //add username
                        partner.addUserName(details.get("userName"));
                        updateEmployeesUsername.add(partner);
                    }
                }
            });
            if(updateEmployeesUsername.size() > 0) {
                return updateEmployeesUsername;
            }
        }
        return null;
    }
}