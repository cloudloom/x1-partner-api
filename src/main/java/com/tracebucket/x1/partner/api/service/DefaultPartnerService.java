package com.tracebucket.x1.partner.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by sadath on 26-May-2015.
 */
public interface DefaultPartnerService {
    public DefaultPartner save(DefaultPartner partner);
    public DefaultPartner findOne(AggregateId aggregateId);
    public List<DefaultPartner> findAll();
    public boolean delete(AggregateId partnerAggregateId);
    public DefaultPartner setPartnerCategory(PartnerCategory partnerCategory, AggregateId partnerAggregateId);
    public DefaultPartner movePartnerToCategory(PartnerCategory newPartnerCategory,AggregateId partnerAggregateId);
    public DefaultPartner addPartnerRole(DefaultPartner partner);
    public DefaultPartner addAddressToRole(EntityId partnerRoleEntityId, Address address, AggregateId partnerAggregateId);
    public DefaultPartner moveRoleAddressTo(EntityId partnerRoleEntityId, Address newAddress,AggregateId partnerAggregateId);
/*
    public DefaultPartner changeOwner(DefaultOwner newOwner, AggregateId partnerAggregateId);
*/
    public Boolean hasPartnerRole(AggregateId partnerAggregateId, EntityId roleEntityId);
    public List<DefaultPartner> findPartnersByOrganization(String organizationUid);

}