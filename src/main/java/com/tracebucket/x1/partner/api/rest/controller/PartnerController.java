package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.rest.exception.PartnerException;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerRoleResource;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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


    @RequestMapping(value = "/partner", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> createPartner(@RequestBody DefaultPartnerResource partnerResource) {

        DefaultPartner partner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partnerResource, DefaultPartner.class);
        try {
            partner = partnerService.save(partner);
        }
        catch (DataIntegrityViolationException dive) {
            throw new PartnerException("Partner With title : " + partnerResource.getTitle() + "Exists", HttpStatus.CONFLICT);
        }

        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.CREATED);
        }
        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/partner/{partnerUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> findOne(@PathVariable("partnerUid") String aggregateId) {

        DefaultPartner partner = partnerService.findOne(new AggregateId(aggregateId));
        DefaultPartnerResource partnerResource = null;

        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
        }

        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "/partner/{partnerUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deletePartner(@PathVariable("partnerUid") String partnerAggregateId) {
       return new ResponseEntity<Boolean>(partnerService.delete(new AggregateId(partnerAggregateId)), HttpStatus.OK);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnercategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> setPartnerCategory(@RequestBody PartnerCategory partnerCategory, @PathVariable("partnerUid") String partnerAggregateId) {

        DefaultPartner partner = partnerService.setPartnerCategory(partnerCategory, new AggregateId(partnerAggregateId));
        DefaultPartnerResource partnerResource = null;

        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
        }

        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partner/tocategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> movePartnerToCategory(@RequestBody PartnerCategory newPartnerCategory, @PathVariable("partnerUid") String partnerAggregateId) {

        DefaultPartner partner = partnerService.movePartnerToCategory(newPartnerCategory, new AggregateId(partnerAggregateId));
        DefaultPartnerResource partnerResource = null;

        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
        }

        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnerrole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPartnerRole(@RequestBody DefaultPartnerRoleResource newPartnerRole, @PathVariable("partnerUid")String partnerAggregateId) {
        DefaultPartnerRole defaultPartnerRole = assemblerResolver.resolveEntityAssembler(DefaultPartnerRole.class, DefaultPartnerRoleResource.class).toEntity(newPartnerRole, DefaultPartnerRole.class);
        DefaultPartner partner = partnerService.addPartnerRole(defaultPartnerRole, new AggregateId(partnerAggregateId));
        DefaultPartnerResource partnerResource = null;

        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);

        }
        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnerRole/{uid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addAddressToRole(@PathVariable("partnerUid") String partnerAggregateId, @PathVariable("uid") String partnerRoleUid, @RequestBody DefaultAddressResource address) {
        DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
        DefaultPartner partner = partnerService.addAddressToRole(new EntityId(partnerRoleUid), defaultAddress, new AggregateId(partnerAggregateId));
        DefaultPartnerResource partnerResource = null;
        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
        }
        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnerRole/{uid}/moveAddress", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> moveRoleAddressTo(@PathVariable("partnerUid") String partnerAggregateId, @PathVariable("uid") String partnerRoleUid, @RequestBody DefaultAddressResource address) {
        DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
        DefaultPartner partner = partnerService.moveRoleAddressTo(new EntityId(partnerRoleUid), defaultAddress, new AggregateId(partnerAggregateId));
        DefaultPartnerResource partnerResource = null;
        if(partner != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
        }
        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

/*    public ResponseEntity<DefaultPartnerResource> changeOwner(DefaultOwnerResource newOwner, String partnerAggregateId) {
        return null;
    }*/

    @RequestMapping(value = "/partner/{partnerUid}/hasrole", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasPartnerRole(@RequestBody DefaultPartnerRoleResource partnerRole, @PathVariable("partnerUid") String partnerAggregateId) {
        DefaultPartnerRole defaultPartnerRole = assemblerResolver.resolveEntityAssembler(DefaultPartnerRole.class, DefaultPartnerRoleResource.class).toEntity(partnerRole, DefaultPartnerRole.class);
        return new ResponseEntity<Boolean>(partnerService.hasPartnerRole(defaultPartnerRole, new AggregateId(partnerAggregateId)), HttpStatus.OK);
    }
}