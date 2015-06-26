package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Partner {
    public ResponseEntity<DefaultPartnerResource> createPartner(HttpServletRequest request, DefaultPartnerResource partner);
    public ResponseEntity<DefaultPartnerResource> findOne(HttpServletRequest request, String aggregateId);
    public ResponseEntity<Boolean> deletePartner(HttpServletRequest request, String partnerAggregateId);
    public ResponseEntity<DefaultPartnerResource> setPartnerCategory(HttpServletRequest request, PartnerCategory partnerCategory, String partnerAggregateId);
    public ResponseEntity<DefaultPartnerResource> movePartnerToCategory(HttpServletRequest request, PartnerCategory newPartnerCategory, String partnerAggregateId);
    public ResponseEntity<DefaultPartnerResource> addPartnerRole(HttpServletRequest request, DefaultPartnerResource partner);
    public ResponseEntity<DefaultPartnerResource> addAddressToRole(HttpServletRequest request, String partnerAggregateId, String partnerRoleUid,DefaultAddressResource address);
    public ResponseEntity<DefaultPartnerResource> moveRoleAddressTo(HttpServletRequest request, String partnerAggregateId, String partnerRoleUid,DefaultAddressResource address);
    public ResponseEntity<DefaultPartnerResource> changeOwner(HttpServletRequest request, String partnerAggregateId, DefaultOwnerResource newOwner);
    public ResponseEntity<Boolean> hasPartnerRole(HttpServletRequest request, String partnerAggregateId, String roleEntityId);
    public ResponseEntity<DefaultPartnerResource> addPosition(HttpServletRequest request, String partnerAggregateId, String partnerRoleUid, String positionUid);
    public ResponseEntity<DefaultPartnerResource> addPositionAndOrganization(HttpServletRequest request, String partnerAggregateId, String partnerRoleUid, String positionUid, String organizationUnitUid);
//    public ResponseEntity<DefaultPartnerResource> addPositionAndOrganization(HttpServletRequest request, String partnerAggregateId, Map<String, Map<String, Map<String, String>>> partnerDetails);
    public ResponseEntity<Boolean> isPositionAssigned(HttpServletRequest request, String organizationUID, String positionUid, String organizationUnitUid);

}