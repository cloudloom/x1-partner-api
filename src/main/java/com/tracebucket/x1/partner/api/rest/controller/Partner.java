package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerRoleResource;
import org.springframework.http.ResponseEntity;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Partner {
    public ResponseEntity<DefaultPartner> createPartner(DefaultPartnerResource partner);
    public ResponseEntity<DefaultPartner> findOne(String aggregateId);
    public ResponseEntity<Boolean> deletePartner(String partnerAggregateId);
    public ResponseEntity<DefaultPartner> setPartnerCategory(PartnerCategory partnerCategory, String partnerAggregateId);
    public ResponseEntity<DefaultPartner> movePartnerToCategory(PartnerCategory newPartnerCategory, String partnerAggregateId);
    public ResponseEntity<DefaultPartner> addPartnerRole(DefaultPartnerRoleResource newPartnerRole, String partnerAggregateId);
    public ResponseEntity<DefaultPartner> addAddressToRole(DefaultPartnerRoleResource partnerRole, DefaultAddressResource address, String partnerAggregateId);
    public ResponseEntity<DefaultPartner> moveRoleAddressTo(DefaultPartnerRoleResource partnerRole, DefaultAddressResource newAddress,String partnerAggregateId);
    public ResponseEntity<DefaultPartner> changeOwner(DefaultOwnerResource newOwner, String partnerAggregateId);
    public ResponseEntity<Boolean> hasPartnerRole(DefaultPartnerRoleResource partnerRole, String partnerAggregateId);
}