package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.rest.exception.PartnerException;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    @RequestMapping(value = "/partners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> findAll() {
        List<DefaultPartner> partners = partnerService.findAll();
        if(partners != null && partners.size() > 0) {
            Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
            return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
        }

        return new ResponseEntity<Set<DefaultPartnerResource>>(Collections.emptySet(), HttpStatus.NOT_FOUND);
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

    @RequestMapping(value = "/partner/partnerrole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPartnerRole(@RequestBody DefaultPartnerResource partner) {
        DefaultPartner defaultPartner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partner, DefaultPartner.class);
        defaultPartner = partnerService.addPartnerRole(defaultPartner);
        DefaultPartnerResource partnerResource;
        if(defaultPartner != null && defaultPartner.getAggregateId() != null) {
            partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(defaultPartner, DefaultPartnerResource.class);
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

    @RequestMapping(value = "/partner/{partnerUid}/owner/{organizationUid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> changeOwner(@PathVariable("partnerUid") String partnerAggregateId, @PathVariable("organizationUid") String organizationAggregateId) {
        DefaultPartner partner = partnerService.changeOwner(new DefaultOwner(organizationAggregateId), new AggregateId(partnerAggregateId));
        if(partner != null) {
            DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
            return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
        }
        return new ResponseEntity<DefaultPartnerResource>(new DefaultPartnerResource(), HttpStatus.NOT_ACCEPTABLE);
    }

    @RequestMapping(value = "/partner/{partnerUid}/role/{roleUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasPartnerRole(@PathVariable("partnerUid") String partnerAggregateId, @PathVariable("roleUid") String roleEntityId) {
        return new ResponseEntity<Boolean>(partnerService.hasPartnerRole(new AggregateId(partnerAggregateId), new EntityId(roleEntityId)), HttpStatus.OK);
    }
}