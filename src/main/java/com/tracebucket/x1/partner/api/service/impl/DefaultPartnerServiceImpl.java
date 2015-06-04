package com.tracebucket.x1.partner.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.repository.jpa.DefaultPartnerRepository;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
@Service
@Transactional
public class DefaultPartnerServiceImpl implements DefaultPartnerService {
    
    @Autowired
    private DefaultPartnerRepository partnerRepository;

    @Override
    public DefaultPartner save(DefaultPartner partner) {
        return partnerRepository.save(partner);
    }

    @Override
    public DefaultPartner findOne(AggregateId aggregateId) {
        return partnerRepository.findOne(aggregateId);
    }

    @Override
    public boolean delete(AggregateId partnerAggregateId) {
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partnerRepository.delete(partner);
            return partnerRepository.findOne(partnerAggregateId) == null ? true : false;
        }
        return false;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner setPartnerCategory(PartnerCategory partnerCategory, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partner.setPartnerCategory(partnerCategory);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner movePartnerToCategory(PartnerCategory newPartnerCategory,AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partner.setPartnerCategory(newPartnerCategory);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPartnerRole(DefaultPartner addPartnerRole){
        DefaultPartner partner = partnerRepository.findOne(addPartnerRole.getAggregateId());
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
    public DefaultPartner addAddressToRole(EntityId partnerRoleEntityId, Address address, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
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
    public DefaultPartner moveRoleAddressTo(EntityId partnerRoleEntityId, Address newAddress, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
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

/*    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner changeOwner(DefaultOwner newOwner, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partner.changeOwner(newOwner);
            return partner;
        }
        return null;
    }*/

    @Override
    public Boolean hasPartnerRole(AggregateId partnerAggregateId, EntityId roleEntityId){
        Long found = null;
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            if(partner.getAllAssignedRoles() != null && partner.getAllAssignedRoles().size() > 0) {
                found = partner.getAllAssignedRoles().parallelStream()
                        .filter(t -> t.getEntityId().equals(roleEntityId.getId()))
                        .count();
            }
        }
        return (found != null && found > 0) ? true : false;
    }

    @Override
    public List<DefaultPartner> findPartnersByOrganization(String organizationUid) {
        return partnerRepository.findPartnersByOrganization(organizationUid);
    }
}