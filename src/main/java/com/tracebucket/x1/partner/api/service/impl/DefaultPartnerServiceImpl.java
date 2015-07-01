package com.tracebucket.x1.partner.api.service.impl;

import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory;
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
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

}