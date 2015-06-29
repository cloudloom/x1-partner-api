package com.tracebucket.x1.partner.api.service;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEmployeeRestructureResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerPositionAndOrganizationUnitResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
    public Set<DefaultPartner> searchPartners(String tenantId, AggregateId organizationAggregateId, String searchTerm);
    public Boolean isPositionAssigned(String tenantId, String organizationUid, String positionUid, String organizationUnitUid);
    public DefaultPartner addPositionAndOrganization(String tenantId, AggregateId partnerAggregateId, EntityId partnerRoleUid, EntityId positionUid, EntityId organizationUnitUid);
    public List<DefaultPartner> addPositionAndOrganization(String tenantId, String organizationUid, List<DefaultPartnerPositionAndOrganizationUnitResource> resource);
    public Map<Boolean, Set<DefaultPartner>> getEmployeesAssignedAndNotToOrganizationAndPosition(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid);
    public Set<DefaultPartner> getEmployeesAssignedToOrganizationAndPosition(String tenantId, AggregateId organizationUid, EntityId organizationUnitUid, EntityId positionUid);
    public Set<DefaultPartner> restructureEmployees(String tenantId, AggregateId organizationAggregateId, HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> employeeStructure);
    }