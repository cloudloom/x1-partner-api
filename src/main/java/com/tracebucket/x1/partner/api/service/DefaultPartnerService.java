package com.tracebucket.x1.partner.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;

/**
 * Created by sadath on 26-May-2015.
 */
public interface DefaultPartnerService {
    public DefaultPartner save(DefaultPartner partner);
    public DefaultPartner findOne(AggregateId aggregateId);
    public boolean delete(AggregateId partnerAggregateId);
    public DefaultPartner setPartnerCategory(PartnerCategory partnerCategory, AggregateId partnerAggregateId);
    public DefaultPartner movePartnerToCategory(PartnerCategory newPartnerCategory,AggregateId partnerAggregateId);
    public DefaultPartner addPartnerRole(DefaultPartnerRole newPartnerRole, AggregateId partnerAggregateId);
    public DefaultPartner addAddressToRole(DefaultPartnerRole partnerRole, Address address, AggregateId partnerAggregateId);
    public DefaultPartner moveRoleAddressTo(DefaultPartnerRole partnerRole, Address newAddress,AggregateId partnerAggregateId);
    public DefaultPartner changeOwner(DefaultOwner newOwner, AggregateId partnerAggregateId);
    public Boolean hasPartnerRole(DefaultPartnerRole partnerRole, AggregateId partnerAggregateId);
}