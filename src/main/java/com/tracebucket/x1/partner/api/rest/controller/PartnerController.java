package com.tracebucket.x1.partner.api.rest.controller;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.tron.rest.exception.X1Exception;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.rest.exception.PartnerException;
import com.tracebucket.x1.partner.api.rest.resources.*;
import com.tracebucket.x1.partner.api.service.DefaultPartnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;

/**
 * Created by sadath on 26-May-2015.
 * Controller For Partner
 */
@RestController
public class PartnerController implements Partner {
    private static Logger log = LoggerFactory.getLogger(PartnerController.class);

    @Autowired
    private DefaultPartnerService partnerService;

    @Autowired
    private AssemblerResolver assemblerResolver;

    /**
     * Create A New Partner
     * @param request
     * @param partnerResource
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> createPartner(HttpServletRequest request, @Valid @RequestBody DefaultPartnerResource partnerResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPartnerResource to DefaultPartner Entity
            DefaultPartner partner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partnerResource, DefaultPartner.class);
            try {
                //save partner
                partner = partnerService.save(tenantId, partner);
            } catch (DataIntegrityViolationException dive) {
                throw new PartnerException(dive.getMessage(), HttpStatus.CONFLICT);
            }
            //if partner saved
            if (partner != null) {
                //Map DefaultPartnerResource to DefaultPartner
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.CREATED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Find Partner By Uid
     * @param request
     * @param aggregateId
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/{partnerUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> findOne(HttpServletRequest request, @PathVariable("partnerUid") String aggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //findOne
            DefaultPartner partner = partnerService.findOne(tenantId, new AggregateId(aggregateId));
            DefaultPartnerResource partnerResource = null;
            //if found, else return http status 404
            if (partner != null) {
                //Map DefaultPartner Entity to DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employee
     * @param request
     * @param partnerUid
     * @param roleUid
     * @return DefaultEmployeeResource
     */
    @RequestMapping(value = "/partner/{partnerUid}/employee/{roleUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultEmployeeResource> getEmployee(HttpServletRequest request, @PathVariable("partnerUid") String partnerUid, @PathVariable("roleUid") String roleUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employee by partnerUid and roleUid
            DefaultEmployee employee = partnerService.getEmployee(tenantId, new AggregateId(partnerUid), new EntityId(roleUid));
            DefaultEmployeeResource employeeResource = null;
            //if employee found
            if (employee != null) {
                //Map DefaultEmployee Entity To DefaultEmployeeResource
                employeeResource = assemblerResolver.resolveResourceAssembler(DefaultEmployeeResource.class, DefaultEmployee.class).toResource(employee, DefaultEmployeeResource.class);
                return new ResponseEntity<DefaultEmployeeResource>(employeeResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get The Users Reporting Manager(s) Email And Phone No By Users Username
     * @param request
     * @param userName
     * @return DefaultNotifyTo
     */
    @Override
    @RequestMapping(value = "/reporting/managers/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultNotifyTo> notifyToByUsername(HttpServletRequest request, @PathVariable("userName")String userName) {
/*        String tenantId = request.getHeader("tenant_id");
        if (tenantId != null) {
            DefaultNotifyTo notifyTo = partnerService.notifyToByUsername(tenantId, userName);*/
            //get details
            DefaultNotifyTo notifyTo = partnerService.notifyToByUsername(null, userName);
        //if details found
        if (notifyTo != null) {
                return new ResponseEntity<DefaultNotifyTo>(notifyTo, HttpStatus.OK);
            }
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        /*} else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }*/
    }

    /**
     * Check If User With Username Is An Employee
     * @param request
     * @param userName
     * @return Boolean
     */
    @Override
    @RequestMapping(value = "/employee/user/{userName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isUserAnEmployee(HttpServletRequest request, @PathVariable(value = "userName") String userName) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //return status
              return new ResponseEntity<Boolean>(partnerService.isUserAnEmployee(tenantId, userName), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Find All Partners
     * @param request
     * @return Set<DefaultPartnerResource>
     */
    @RequestMapping(value = "/partners", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> findAll(HttpServletRequest request) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //findAll partners
            List<DefaultPartner> partners = partnerService.findAll(tenantId);
            //if partners found
            if (partners != null && partners.size() > 0) {
                //Map DefaultPartner Entities To DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Delete Partner By Uid
     * @param request
     * @param partnerAggregateId
     * @return Boolean
     */
    @RequestMapping(value = "/partner/{partnerUid}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> deletePartner(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //return status
            return new ResponseEntity<Boolean>(partnerService.delete(tenantId, new AggregateId(partnerAggregateId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Set Partner Category
     * @param request
     * @param partnerCategory
     * @param partnerAggregateId
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/{partnerUid}/partnercategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> setPartnerCategory(HttpServletRequest request, @RequestBody PartnerCategory partnerCategory, @PathVariable("partnerUid") String partnerAggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //set partner category
            DefaultPartner partner = partnerService.setPartnerCategory(tenantId, partnerCategory, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;
            //if partnerCategory set
            if (partner != null) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Update / Move PartnerCategory
     * @param request
     * @param newPartnerCategory
     * @param partnerAggregateId
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/{partnerUid}/partner/tocategory", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> movePartnerToCategory(HttpServletRequest request, @RequestBody PartnerCategory newPartnerCategory, @PathVariable("partnerUid") String partnerAggregateId) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //update / move partnerCategory
            DefaultPartner partner = partnerService.movePartnerToCategory(tenantId, newPartnerCategory, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;
            //if updated
            if (partner != null) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Role To An Existing Partner
     * @param request
     * @param partner
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/partnerrole", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPartnerRole(HttpServletRequest request, @Valid @RequestBody DefaultPartnerResource partner) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPartnerResource To DefaultPartner Entity
            DefaultPartner defaultPartner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partner, DefaultPartner.class);
            //add partnerRole
            defaultPartner = partnerService.addPartnerRole(tenantId, defaultPartner);
            DefaultPartnerResource partnerResource;
            //if partnerRole added
            if (defaultPartner != null && defaultPartner.getAggregateId() != null) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(defaultPartner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add UserName(s)
     * @param request
     * @param userNames
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partner/employees/username", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> addUsername(HttpServletRequest request, @RequestBody List<DefaultPartnerUsername> userNames) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add username
            Set<DefaultPartner> partners = partnerService.addUsername(tenantId, userNames);
            //if username added
            if (partners != null && partners.size() > 0) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Update PartnerRole
     * @param request
     * @param partner
     * @param partnerRoleUid
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/partnerrole/{partnerRoleUid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> updatePartnerRole(HttpServletRequest request, @Valid @RequestBody DefaultPartnerResource partner, @PathVariable("partnerRoleUid") String partnerRoleUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultPartnerResource to DefaultPartner Entity
            DefaultPartner defaultPartner = assemblerResolver.resolveEntityAssembler(DefaultPartner.class, DefaultPartnerResource.class).toEntity(partner, DefaultPartner.class);
            //update partnerRole
            defaultPartner = partnerService.updatePartnerRole(tenantId, defaultPartner, new EntityId(partnerRoleUid));
            DefaultPartnerResource partnerResource;
            //if updated
            if (defaultPartner != null && defaultPartner.getAggregateId() != null) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(defaultPartner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Address To Partner Role
     * @param request
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param address
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/{partnerUid}/partnerRole/{uid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addAddressToRole(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("uid") String partnerRoleUid, @Valid @RequestBody DefaultAddressResource address) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultAddress Entity To DefaultAddressResource
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            //add address to role
            DefaultPartner partner = partnerService.addAddressToRole(tenantId, new EntityId(partnerRoleUid), defaultAddress, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;
            //if updated
            if (partner != null) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Move / Update Role Address
     * @param request
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param address
     * @return
     */
    @RequestMapping(value = "/partner/{partnerUid}/partnerRole/{uid}/moveAddress", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> moveRoleAddressTo(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("uid") String partnerRoleUid, @Valid @RequestBody DefaultAddressResource address) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultAddressResource to DefaultAddress Entity
            DefaultAddress defaultAddress = assemblerResolver.resolveEntityAssembler(DefaultAddress.class, DefaultAddressResource.class).toEntity(address, DefaultAddress.class);
            //move role address to
            DefaultPartner partner = partnerService.moveRoleAddressTo(tenantId, new EntityId(partnerRoleUid), defaultAddress, new AggregateId(partnerAggregateId));
            DefaultPartnerResource partnerResource = null;
            //if updated
            if (partner != null) {
                //Map DefaultPartner Entity To DefaultPartnerResource
                partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Change Owner
     * @param request
     * @param partnerAggregateId
     * @param ownerResource
     * @return DefaultPartnerResource
     */
    @RequestMapping(value = "/partner/{partnerUid}/owner", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> changeOwner(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @RequestBody DefaultOwnerResource ownerResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //Map DefaultOwnerResource to DefaultOwner Entity
            DefaultOwner owner = assemblerResolver.resolveEntityAssembler(DefaultOwner.class, DefaultOwnerResource.class).toEntity(ownerResource, DefaultOwner.class);
            //change owner
            DefaultPartner partner = partnerService.changeOwner(tenantId, owner, new AggregateId(partnerAggregateId));
            //if updated
            if (partner != null) {
                //Map DefaultPartnerEntity to DefaultPartnerResource
                DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Partner Has Role
     * @param request
     * @param partnerAggregateId
     * @param roleEntityId
     * @return Boolean
     */
    @RequestMapping(value = "/partner/{partnerUid}/has/role/{roleUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> hasPartnerRole(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("roleUid") String roleEntityId) {
        //tenantID
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //return status
            return new ResponseEntity<Boolean>(partnerService.hasPartnerRole(tenantId, new AggregateId(partnerAggregateId), new EntityId(roleEntityId)), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Position To Partner Role
     * @param request
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param positionUid
     * @return DefaultPartnerResource
     */
    @Override
    @RequestMapping(value = "/partner/{partnerUid}/role/{roleUid}/position/{positionUid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPosition(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("roleUid") String partnerRoleUid, @PathVariable("positionUid") String positionUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add position
            DefaultPartner partner = partnerService.addPosition(tenantId, new AggregateId(partnerAggregateId), new EntityId(partnerRoleUid), new EntityId(positionUid));
            //if added
            if (partner != null) {
                //Map DefaultPartner Entity to DefaultPartnerResource
                DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Search Partners
     * @param request
     * @param organizationAggregateId
     * @param searchTerm
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partner/organization/{organizationUID}/search/{searchTerm}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> searchPartners(HttpServletRequest request, @PathVariable("organizationUID") String organizationAggregateId, @PathVariable("searchTerm") String searchTerm) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            searchTerm = "(.*)" + searchTerm.toLowerCase() + "(.*)";
            //search partners by search term
            Set<DefaultPartner> partners = partnerService.searchPartners(tenantId, new AggregateId(organizationAggregateId), searchTerm);
            //if partners found
            if (partners != null && partners.size() > 0) {
                //Map DefaultPartner Entities to DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Check If Position Is Assigned
     * @param request
     * @param organizationUID
     * @param positionUid
     * @param organizationUnitUid
     * @return Boolean
     */
    @Override
    @RequestMapping(value = "/partner/organization/{organizationUID}/position/{positionUid}/organizationUnit/{organizationUnitUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isPositionAssigned(HttpServletRequest request, @PathVariable("organizationUID") String organizationUID, @PathVariable("positionUid") String positionUid, @PathVariable("organizationUnitUid") String organizationUnitUid) {
        //tenantID
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //check if position is assigned
            return new ResponseEntity<Boolean>(partnerService.isPositionAssigned(tenantId, organizationUID, positionUid, organizationUnitUid), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Is OrganizationUnit Assigned
     * @param request
     * @param organizationUID
     * @param organizationUnitUid
     * @return Boolean
     */
    @Override
    @RequestMapping(value = "/partner/organization/{organizationUID}/organizationUnit/{organizationUnitUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> isOrganizationUnitAssigned(HttpServletRequest request, @PathVariable("organizationUID") String organizationUID, @PathVariable("organizationUnitUid") String organizationUnitUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //check if organizationUnit Is Assigned
            return new ResponseEntity<Boolean>(partnerService.isOrganizationUnitAssigned(tenantId, organizationUID, organizationUnitUid), HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Position And Organization
     * @param request
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param positionUid
     * @param organizationUnitUid
     * @return DefaultPartnerResource
     */
    @Override
    @RequestMapping(value = "/partner/{partnerUid}/role/{roleUid}/position/{positionUid}/organizationUnit/{organizationUnitUid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addPositionAndOrganization(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("roleUid") String partnerRoleUid, @PathVariable("positionUid") String positionUid, @PathVariable("organizationUnitUid") String organizationUnitUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add position and organization to partner Role
            DefaultPartner partner = partnerService.addPositionAndOrganization(tenantId, new AggregateId(partnerAggregateId), new EntityId(partnerRoleUid), new EntityId(positionUid), new EntityId(organizationUnitUid));
            //if added
            if (partner != null) {
                //Map DefaultPartner Entity to DefaultPartnerResource
                DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Position And Organization
     * @param request
     * @param organizationUid
     * @param resource
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partner/organization/{organizationUid}/position/organizationUnit", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> addPositionAndOrganization(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @RequestBody List<DefaultPartnerPositionAndOrganizationUnitResource> resource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add position and organization
            List<DefaultPartner> partners = partnerService.addPositionAndOrganization(tenantId, organizationUid, resource);
            //if added
            if (partners != null) {
                //Map DefaultPartner Entity to DefaultPartnerResource
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned To Organization And Position
     * @param request
     * @param organizationUid
     * @param organizationUnitUid
     * @param positionUid
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partners/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/position/{positionUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesAssignedToOrganizationAndPosition(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @PathVariable("organizationUnitUid") String organizationUnitUid, @PathVariable("positionUid") String positionUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned to organization and position
            Set<DefaultPartner> partners = partnerService.getEmployeesAssignedToOrganizationAndPosition(tenantId, new AggregateId(organizationUid), new EntityId(organizationUnitUid), new EntityId(positionUid));
            //if employees found
            if (partners != null) {
                //Map DefaultPartner Entities to DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned To OrganizationUnit
     * @param request
     * @param organizationUid
     * @param organizationUnitUid
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partners/organization/{organizationUid}/organizationUnit/{organizationUnitUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesAssignedToOrganizationUnit(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @PathVariable("organizationUnitUid") String organizationUnitUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned to organization  unit
            Set<DefaultPartner> partners = partnerService.getEmployeesAssignedToOrganizationUnit(tenantId, new AggregateId(organizationUid), new EntityId(organizationUnitUid));
            //if employees found
            if (partners != null) {
                //Map DefaultPartner Entities to DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned And Not Assigned To Organization And Position
     * @param request
     * @param organizationUid
     * @param organizationUnitUid
     * @param positionUid
     * @return Map<Boolean, Set<DefaultPartnerResource>
     *     key: if true means value: contains employees assigned to organization and position
     *     key: if false means value: contains employees not assigned to organization and position
     */
    @Override
    @RequestMapping(value = "/partners/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/position/{positionUid}/union", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<Boolean, Set<DefaultPartnerResource>>> getEmployeesAssignedAndNotToOrganizationAndPosition(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @PathVariable("organizationUnitUid") String organizationUnitUid, @PathVariable("positionUid") String positionUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned and not assigned to organization and position
            Map<Boolean, Set<DefaultPartner>> partners = partnerService.getEmployeesAssignedAndNotToOrganizationAndPosition(tenantId, new AggregateId(organizationUid), new EntityId(organizationUnitUid), new EntityId(positionUid));
            //if found
            if (partners != null) {
                Map<Boolean, Set<DefaultPartnerResource>> result = new HashMap<Boolean, Set<DefaultPartnerResource>>();
                partners.entrySet().stream().forEach(entry -> {
                    //Map DefaultPartner Entity to DefaultPartnerResource
                    Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(entry.getValue(), DefaultPartnerResource.class);
                    result.put(entry.getKey(), partnerResources);
                });
                return new ResponseEntity<Map<Boolean, Set<DefaultPartnerResource>>>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Restructure Employees
     * @param request
     * @param organizationUid
     * @param employeeStructure
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/employees/organization/{organizationUid}/restructure", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> restructureEmployees(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @RequestBody DefaultEmployeeRestructureResource employeeStructure) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //restructure employees
            Set<DefaultPartner> partners = partnerService.restructureEmployees(tenantId, new AggregateId(organizationUid), employeeStructure.getEmployeeStructure());
            //if restructured
            if(partners != null) {
                //Map DefaultPartner Entities to DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Employees To Positions
     * @param request
     * @param organizationUid
     * @param employeeStructure
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/employees/organization/{organizationUid}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> addEmployeesToPositions(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @RequestBody DefaultEmployeeRestructureResource employeeStructure) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add employees to positions
            Set<DefaultPartner> partners = partnerService.addEmployeesToPositions(tenantId, new AggregateId(organizationUid), employeeStructure.getEmployeeStructure());
            //if added
            if(partners != null) {
                //Map DefaultPartner Entities To DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees User Names By Partner UIDS
     * @param request
     * @param partnerUIDS
     * @return Map<String, String>
     *     key: partnerUid
     *     value: userName
     */
    @Override
    @RequestMapping(value = "/employees/userNames/uids", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, String>> getEmployeesUserNameByPartnerUIDS(HttpServletRequest request, @RequestBody List<String> partnerUIDS) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees user names by partner uids
            Map<String, String> partners = partnerService.getEmployeesUserNameByPartnerUIDS(tenantId, partnerUIDS);
            //if found
            if(partners != null) {
                return new ResponseEntity<Map<String, String>>(partners, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assgined To Organization And Position
     * @param request
     * @param organizationUid
     * @return Map<String, Map<String, Set<DefaultPartnerResource>
     *     outer map key: organizationUnitUid
     *     inner map key: positionUid
     */
    @Override
    @RequestMapping(value = "/employees/organization/{organizationUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Map<String, Set<DefaultPartnerResource>>>> getEmployeesAssignedToOrganizationAndPosition(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned to organization and position
            Map<String, Map<String, ArrayList<DefaultPartner>>> partners = partnerService.getEmployeesAssignedToOrganizationAndPosition(tenantId, new AggregateId(organizationUid));
            //if employees found
            if(partners != null) {
                //Map DefaultPosition Entities to DefaultPositionResources
                HashMap<String, HashMap<String, Set<DefaultPartnerResource>>> orgPosPartnerResources = new HashMap<String, HashMap<String, Set<DefaultPartnerResource>>>();
                partners.entrySet().stream().forEach(orgUnit -> {
                    orgUnit.getValue().entrySet().stream().forEach(position -> {
                        ArrayList<DefaultPartner> defaultPartners = position.getValue();
                        if(defaultPartners != null && defaultPartners.size() > 0) {
                            Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(defaultPartners, DefaultPartnerResource.class);
                            if(partnerResources != null) {
                                if(orgPosPartnerResources.containsKey(orgUnit.getKey())) {
                                    orgPosPartnerResources.get(orgUnit.getKey()).put(position.getKey(), partnerResources);
                                } else {
                                    HashMap<String, Set<DefaultPartnerResource>> positions = new HashMap<String, Set<DefaultPartnerResource>>();
                                    positions.put(position.getKey(), partnerResources);
                                    orgPosPartnerResources.put(orgUnit.getKey(), positions);
                                }
                            }
                        }
                    });
                });
                return new ResponseEntity(orgPosPartnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned To OrganizationUnit And Position
     * @param request
     * @param organizationUid
     * @param organizationUnitUid
     * @param positionUid
     * @return DefaultPartnerMinimalResource
     */
    @Override
    @RequestMapping(value = "/employees/organization/{organizationUid}/organizationUnit/{organizationUnitUid}/position/{positionUid}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerMinimalResource> getEmployeesAssignedToOrganizationUnitAndPosition(HttpServletRequest request, @PathVariable("organizationUid") String organizationUid, @PathVariable("organizationUnitUid") String organizationUnitUid, @PathVariable("positionUid") String positionUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned to organization and position
            Set<DefaultPartner> partners = partnerService.getEmployeesAssignedToOrganizationAndPosition(tenantId, new AggregateId(organizationUid), new EntityId(organizationUnitUid), new EntityId(positionUid));
            //if employees found
            if(partners != null) {
                List<Map<String, String>> partnerList = new ArrayList<Map<String, String>>();
                //stream and forEach partner build minimal resource which contains only partnerUid, roleUid and name of the employee
                partners.stream().forEach(partner -> {
                    Set<DefaultPartnerRole> partnerRoles = partner.getAllAssignedRoles();
                    if(partnerRoles != null) {
                        DefaultPartnerRole partnerRole = partnerRoles.stream().filter(role -> role instanceof DefaultEmployee).findFirst().orElse(null);
                        if(partnerRole != null && partnerRole instanceof DefaultEmployee) {
                            DefaultEmployee employee = (DefaultEmployee) partnerRole;
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("partnerUid", partner.getAggregateId().getAggregateId());
                            map.put("roleUid", employee.getEntityId().getId());
                            StringBuffer empName = new StringBuffer();
                            if(employee.getFirstName() != null) {
                                empName.append(employee.getFirstName());
                            }
                            if(employee.getMiddleName() != null) {
                                empName.append(" ");
                                empName.append(employee.getMiddleName());
                            }
                            if(employee.getLastName() != null) {
                                empName.append(" ");
                                empName.append(employee.getLastName());
                            }
                            map.put("name", empName.toString());
                            partnerList.add(map);
                        }
                    }
                });
                DefaultPartnerMinimalResource resource = new DefaultPartnerMinimalResource();
                resource.setPartners(partnerList);
                return new ResponseEntity(resource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Add Department
     * @param request
     * @param partnerAggregateId
     * @param partnerRoleUid
     * @param departmentUid
     * @return DefaultPartnerResource
     */
    @Override
    @RequestMapping(value = "/partner/{partnerUid}/role/{roleUid}/department/{departmentUid}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addDepartment(HttpServletRequest request, @PathVariable("partnerUid") String partnerAggregateId, @PathVariable("roleUid") String partnerRoleUid, @PathVariable("departmentUid") String departmentUid) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add department
            DefaultPartner partner = partnerService.addDepartment(tenantId, new AggregateId(partnerAggregateId), new EntityId(partnerRoleUid), new EntityId(departmentUid));
            //if added
            if (partner != null) {
                //Map DefaultPartner Entity to DefaultPartnerResource
                DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    /**
     * Add Department, Position And OrganizationUnit
     * @param request
     * @param employeeResource
     * @return DefaultPartnerResource
     */
    @Override
    @RequestMapping(value = "/partner/organizationUnit/department/position", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> addDepartmentPositionAndOrganizationUnit(HttpServletRequest request, @RequestBody DefaultEmployeeResource employeeResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //add department, position and organizationUnit
            DefaultPartner partner = partnerService.addDepartmentPositionAndOrganizationUnit(tenantId, new AggregateId(employeeResource.getPartnerUid()), new EntityId(employeeResource.getRoleUid()), employeeResource.getOrganizationUnit(), employeeResource.getPosition(), employeeResource.getDepartment());
            //if added
            if (partner != null) {
                //Map DefaultPartner Entity to DefaultPartnerResource
                DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_MODIFIED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned To OrganizationUnit And Department
     * @param request
     * @param employeeResource
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partners/organizationUnit/department", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesAssignedToOrganizationUnitAndDepartment(HttpServletRequest request, @RequestBody DefaultEmployeeResource employeeResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned to organizationunit and department
            Set<DefaultPartner> partners = partnerService.getEmployeesAssignedToOrganizationUnitAndDepartment(tenantId, new AggregateId(employeeResource.getOrganization()), new EntityId(employeeResource.getOrganizationUnit()), new EntityId(employeeResource.getDepartment()));
            //if employees founf
            if (partners != null) {
                //Map DefaultPartner Entities To DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned To OrganizationUnit And Department
     * @param request
     * @param organizationUnit
     * @param department
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/employees/organizationUnit/department", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesAssignedToOrganizationUnitAndDepartment(HttpServletRequest request, @RequestParam("organizationUnit") String organizationUnit, @RequestParam("department") String department) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            if(organizationUnit != null && department != null) {
                //get employees assigned to organizationunit and department
                Set<DefaultPartner> partners = partnerService.getEmployeesAssignedToOrganizationUnitAndDepartment(tenantId, new AggregateId(tenantId), new EntityId(organizationUnit), new EntityId(department));
                //if employees found
                if (partners != null) {
                    //Map DefaultPartner Entities to DefaultPartnerResources
                    Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                    return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            } else {
                throw new X1Exception("Organization Unit And Department Not Specified.", HttpStatus.PRECONDITION_REQUIRED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Who Are Users And Assigned to OrganizationUnit And/OR Department
     * @param request
     * @param organizationUnit
     * @param department
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/employees/users/organizationUnit/department", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeeUsersAssignedToOrganizationUnitAndDepartment(HttpServletRequest request, @RequestParam(value = "organizationUnit", required = false) String organizationUnit, @RequestParam(value = "department", required = false) String department) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            Set<DefaultPartner> partners = null;
            if(organizationUnit != null && department != null) {
                //get employees who are users by organization unit and department
                partners = partnerService.getEmployeeUsersAssignedToOrganizationUnitAndDepartment(tenantId, new AggregateId(tenantId), new EntityId(organizationUnit), new EntityId(department));
            } else if(organizationUnit != null && department == null) {
                //get employees who are users by organization unit
                partners = partnerService.getEmployeeUsersAssignedToOrganizationUnit(tenantId, new AggregateId(tenantId), new EntityId(organizationUnit));
            } else if(organizationUnit == null && department == null) {
                //get employees who are users
                partners = partnerService.getEmployeeUsers(tenantId, new AggregateId(tenantId));
            } else if(organizationUnit == null && department != null) {
                throw new X1Exception("Organization Unit Not Specified. Specify Organization Unit Before Selecting Department.", HttpStatus.PRECONDITION_REQUIRED);
            }
            if (partners != null) {
                //Map DefaultPartner Entities to DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Assigned To OrganizationUnit And Position And Department
     * @param request
     * @param employeeResource
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/partners/organizationUnit/position/department", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment(HttpServletRequest request, DefaultEmployeeResource employeeResource) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees assigned to organizationunit and position and department
            Set<DefaultPartner> partners = partnerService.getEmployeesAssignedToOrganizationUnitAndPositionAndDepartment(tenantId, new AggregateId(employeeResource.getOrganization()), new EntityId(employeeResource.getOrganizationUnit()), new EntityId(employeeResource.getPosition()), new EntityId(employeeResource.getDepartment()));
            //if employees found
            if (partners != null) {
                //Map DefaultPartner Entities To DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Logged In Employee Details
     * @param request
     * @param principal
     * @return DefaultPartnerResource
     */
    @Override
    @RequestMapping(value = "/employee/loggedIn", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultPartnerResource> getLoggedInEmployeeDetails(HttpServletRequest request, Principal principal) {
        //tenantID
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //if logged in user name is not null
            if(principal.getName() != null) {
                //get logged in employee details
                DefaultPartner partner = partnerService.getLoggedInEmployeeDetails(tenantId, principal.getName());
                //if found
                if (partner != null) {
                    //Map DefaultPartner Entity to DefaultPartnerResource
                    DefaultPartnerResource partnerResource = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResource(partner, DefaultPartnerResource.class);
                    return new ResponseEntity<DefaultPartnerResource>(partnerResource, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Logged In Employee Minimal Details
     * @param request
     * @param principal
     * @return DefaultLoggedInEmployeeMinimalResource
     */
    @Override
    @RequestMapping(value = "/employee/loggedIn/minimal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DefaultLoggedInEmployeeMinimalResource> getLoggedInEmployeeMinimalDetails(HttpServletRequest request, Principal principal) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //if logged in user name is not null
            if(principal.getName() != null) {
                //get logged in employee minimal details
                Map<String, String> partner = partnerService.getLoggedInEmployeeMinimalDetails(tenantId, principal.getName());
                //if found
                if (partner != null) {
                    DefaultLoggedInEmployeeMinimalResource loggedInEmployee = new DefaultLoggedInEmployeeMinimalResource();
                    loggedInEmployee.setLoggedInEmployee(partner);
                    return new ResponseEntity<DefaultLoggedInEmployeeMinimalResource>(loggedInEmployee, HttpStatus.OK);
                } else {
                    return new ResponseEntity("User '" + principal.getName() + "' is not mapped to any employee.", HttpStatus.PRECONDITION_REQUIRED);
                }
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Get Employees Who Are Not Users
     * @param request
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/employees/user/notassigned", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesWhoAreNotUsers(HttpServletRequest request) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees who are not users
                List<DefaultPartner> partners = partnerService.getEmployeesWhoAreNotUsers(tenantId);
            //if employees found
            if (partners != null) {
                //Map DefaultPartner Entities To DefaultPartnerResources
                    Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                    return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
    }

    /**
     * Get Employees By Login Names
     * @param request
     * @param userNames
     * @return Set<DefaultPartnerResource>
     */
    @Override
    @RequestMapping(value = "/employees/userNames", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Set<DefaultPartnerResource>> getEmployeesByLoginNames(HttpServletRequest request, @RequestBody List<String> userNames) {
        //tenantId
        String tenantId = request.getHeader("tenant_id");
        //if tenantId is not null, else raise an exception
        if (tenantId != null) {
            //get employees by login names
            List<DefaultPartner> partners = partnerService.getEmployeesByLoginNames(tenantId, userNames);
            //if found
            if (partners != null) {
                //Map DefaultPartner Entities To DefaultPartnerResources
                Set<DefaultPartnerResource> partnerResources = assemblerResolver.resolveResourceAssembler(DefaultPartnerResource.class, DefaultPartner.class).toResources(partners, DefaultPartnerResource.class);
                return new ResponseEntity<Set<DefaultPartnerResource>>(partnerResources, HttpStatus.OK);
            } else {
                return new ResponseEntity(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }
}