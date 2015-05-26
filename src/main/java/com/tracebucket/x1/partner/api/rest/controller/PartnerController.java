package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerRoleResource;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by sadath on 26-May-2015.
 */
@RestController
public class PartnerController implements Partner{
    private static Logger log = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private DefaultPartnerService partnerService;

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public ResponseEntity<DefaultPartner> createPartner(DefaultPartnerResource partner) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> findOne(String aggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> deletePartner(String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> setPartnerCategory(PartnerCategory partnerCategory, String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> movePartnerToCategory(PartnerCategory newPartnerCategory, String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> addPartnerRole(DefaultPartnerRoleResource newPartnerRole, String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> addAddressToRole(DefaultPartnerRoleResource partnerRole, DefaultAddressResource address, String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> moveRoleAddressTo(DefaultPartnerRoleResource partnerRole, DefaultAddressResource newAddress, String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<DefaultPartner> changeOwner(DefaultOwnerResource newOwner, String partnerAggregateId) {
        return null;
    }

    @Override
    public ResponseEntity<Boolean> hasPartnerRole(DefaultPartnerRoleResource partnerRole, String partnerAggregateId) {
        return null;
    }
}