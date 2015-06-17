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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
    public ResponseEntity<DefaultPartnerResource> createPartner(HttpServletRequest request, @RequestBody DefaultPartnerResource partnerResource) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultPartner partner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partnerResource, DefaultPartner.class);
            try {
                partner = partnerService.save(tenantId, partner);
            } catch (DataIntegrityViolationException dive) {
                throw new PartnerException("Partner With title : " + partnerResource.getTitle() + "Exists", HttpStatus.CONFLICT);
            }

            if (partner != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/{partnerUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> findOne(HttpServletRequest request, @PathVariable("partnerUid") String aggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultPartner partner = partnerService.findOne(tenantId, new AggregateId(aggregateId));
            DefaultPartnerResource partnerResource = null;

            if (partner != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/partners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> findAll(HttpServletRequest request) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            List<DefaultPartner> partners = partnerService.findAll(tenantId);
            if (partners != null && partners.size() > 0) {
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/partner/{partnerUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deletePartner(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            return new ResponseEntity<Boolean>(partnerService.delete(tenantId, new AggregateId(partnerAggregateId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnercategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> setPartnerCategory(HttpServletRequest request, @RequestBody PartnerCategory partnerCategory, @PathVariable("partnerUid") String partnerAggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultPartner partner = partnerService.setPartnerCategory(tenantId, partnerCategory, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;

            if (partner != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partner/tocategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> movePartnerToCategory(HttpServletRequest request, @RequestBody PartnerCategory newPartnerCategory, @PathVariable("partnerUid") String partnerAggregateId) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultPartner partner = partnerService.movePartnerToCategory(tenantId, newPartnerCategory, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;

            if (partner != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/partnerrole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPartnerRole(HttpServletRequest request, @RequestBody DefaultPartnerResource partner) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultPartner defaultPartner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partner, DefaultPartner.class);
            defaultPartner = partnerService.addPartnerRole(tenantId, defaultPartner);
            DefaultPartnerResource partnerResource;
            if (defaultPartner != null && defaultPartner.getAggregateId() != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(defaultPartner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/partnerrole/{partnerRoleUid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> updatePartnerRole(HttpServletRequest request, @RequestBody DefaultPartnerResource partner, @PathVariable("partnerRoleUid") String partnerRoleUid) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultPartner defaultPartner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partner, DefaultPartner.class);
            defaultPartner = partnerService.updatePartnerRole(tenantId, defaultPartner, new EntityId(partnerRoleUid));
            DefaultPartnerResource partnerResource;
            if (defaultPartner != null && defaultPartner.getAggregateId() != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(defaultPartner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnerRole/{uid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addAddressToRole(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("uid") String partnerRoleUid, @RequestBody DefaultAddressResource address) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            DefaultPartner partner = partnerService.addAddressToRole(tenantId, new EntityId(partnerRoleUid), defaultAddress, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;
            if (partner != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/{partnerUid}/partnerRole/{uid}/moveAddress", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> moveRoleAddressTo(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("uid") String partnerRoleUid, @RequestBody DefaultAddressResource address) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            DefaultPartner partner = partnerService.moveRoleAddressTo(tenantId, new EntityId(partnerRoleUid), defaultAddress, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;
            if (partner != null) {
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/{partnerUid}/owner", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> changeOwner(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @RequestBody DefaultOwnerResource ownerResource) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            DefaultOwner owner = assemblerResolver.resolveEntityAssembler(DefaultOwner.class, DefaultOwnerResource.class).toEntity(ownerResource, DefaultOwner.class);
            DefaultPartner partner = partnerService.changeOwner(tenantId, owner, new AggregateId(partnerAggregateId));
            if (partner != null) {
                DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "/partner/{partnerUid}/role/{roleUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasPartnerRole(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("roleUid") String roleEntityId) {
        String tenantId = request.getHeader("tenant_id");
        if(tenantId != null) {
            return new ResponseEntity<Boolean>(partnerService.hasPartnerRole(tenantId, new AggregateId(partnerAggregateId), new EntityId(roleEntityId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}