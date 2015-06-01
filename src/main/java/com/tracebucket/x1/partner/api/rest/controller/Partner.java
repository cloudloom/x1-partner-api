package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerRoleResource;
import org.springframework.http.ResponseEntity;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Partner {
    public ResponseEntity<DefaultPartnerResource> createPartner(DefaultPartnerResource partner);
    public ResponseEntity<DefaultPartnerResource> findOne(String aggregateId);
    public ResponseEntity<Boolean> deletePartner(String partnerAggregateId);
    public ResponseEntity<DefaultPartnerResource> setPartnerCategory(PartnerCategory partnerCategory, String partnerAggregateId);
    public ResponseEntity<DefaultPartnerResource> movePartnerToCategory(PartnerCategory newPartnerCategory, String partnerAggregateId);
    public ResponseEntity<DefaultPartnerResource> addPartnerRole(DefaultPartnerResource partner);
    public ResponseEntity<DefaultPartnerResource> addAddressToRole(String partnerAggregateId, String partnerRoleUid,DefaultAddressResource address);
    public ResponseEntity<DefaultPartnerResource> moveRoleAddressTo(String partnerAggregateId, String partnerRoleUid,DefaultAddressResource address);
/*
    public ResponseEntity<DefaultPartnerResource> changeOwner(DefaultOwnerResource newOwner, String partnerAggregateId);
*/
    public ResponseEntity<Boolean> hasPartnerRole(String partnerAggregateId, String roleEntityId);
}