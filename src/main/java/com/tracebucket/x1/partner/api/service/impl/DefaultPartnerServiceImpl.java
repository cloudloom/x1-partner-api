package com.tracebucket.x1.partner.api.service.impl;

import com.tracebucket.tron.ddd.annotation.PersistChanges;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.repository.jpa.DefaultPartnerRepository;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
        //TODO
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addPartnerRole(DefaultPartnerRole newPartnerRole, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partner.addPartnerRole(newPartnerRole);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner addAddressToRole(DefaultPartnerRole partnerRole, Address address, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partner.addAddressToRole(partnerRole, address);
            return partner;
        }
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner moveRoleAddressTo(DefaultPartnerRole partnerRole, Address newAddress,AggregateId partnerAggregateId){
        //TODO
        return null;
    }

    @Override
    @PersistChanges(repository = "partnerRepository")
    public DefaultPartner changeOwner(DefaultOwner newOwner, AggregateId partnerAggregateId){
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            partner.changeOwner(newOwner);
            return partner;
        }
        return null;
    }

    @Override
    public Boolean hasPartnerRole(DefaultPartnerRole partnerRole, AggregateId partnerAggregateId){
        Long found = null;
        DefaultPartner partner = partnerRepository.findOne(partnerAggregateId);
        if(partner != null) {
            found = partner.getAllAssignedRoles().parallelStream()
                    .filter(t -> t.getEntityId().equals(partnerRole.getEntityId()))
                    .count();
        }
        return (found != null && found > 0) ? true : false;
    }
}