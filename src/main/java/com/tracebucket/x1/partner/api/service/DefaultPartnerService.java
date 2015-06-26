package com.tracebucket.x1.partner.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;

import java.util.List;
import java.util.Map;

/**
 * Created by sadath on 26-May-2015.
 */
public interface DefaultPartnerService {
    public DefaultPartner save(String tenantId, DefaultPartner partner);
    public DefaultPartner findOne(String tenantId, AggregateId aggregateId);
    public List<DefaultPartner> findAll(String tenantId);
    public boolean delete(String tenantId, AggregateId partnerAggregateId);
    public DefaultPartner setPartnerCategory(String tenantId, PartnerCategory partnerCategory, AggregateId partnerAggregateId);
    public DefaultPartner movePartnerToCategory(String tenantId, PartnerCategory newPartnerCategory,AggregateId partnerAggregateId);
    public DefaultPartner addPartnerRole(String tenantId, DefaultPartner partner);
    public DefaultPartner updatePartnerRole(String tenantId, DefaultPartner partner, EntityId partnerRoleEntityId);
    public DefaultPartner addAddressToRole(String tenantId, EntityId partnerRoleEntityId, Address address, AggregateId partnerAggregateId);
    public DefaultPartner moveRoleAddressTo(String tenantId, EntityId partnerRoleEntityId, Address newAddress,AggregateId partnerAggregateId);
    public DefaultPartner changeOwner(String tenantId, DefaultOwner newOwner, AggregateId partnerAggregateId);
    public Boolean hasPartnerRole(String tenantId, AggregateId partnerAggregateId, EntityId roleEntityId);
    public List<DefaultPartner> findPartnersByOrganization(String organizationUid);
    public DefaultPartner addPosition(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid);
    public DefaultPartner addPositionAndOrganization(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid, EntityId organizationUnitUid);
    public Boolean isPositionAssigned(String tenantId, String organizationUid, String positionUid, String organizationUnitUid);
    }