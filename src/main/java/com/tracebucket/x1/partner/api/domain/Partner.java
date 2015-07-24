package com.tracebucket.x1.partner.api.domain;

import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import org.dozer.Mapper;

import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Partner {
    public void setPartnerCategory(PartnerCategory partnerCategory);
    public void movePartnerToCategory(PartnerCategory newPartnerCategory);
    public void addPartnerRole(DefaultPartnerRole newPartnerRole);
    public void updatePartnerRole(DefaultPartnerRole newPartnerRole, Mapper mapper);
    public Boolean hasPartnerRole(DefaultPartnerRole partnerRole);
    public void addAddressToRole(DefaultPartnerRole partnerRole, Address address);
    public void moveRoleAddressTo(DefaultPartnerRole partnerRole, Address newAddress);
    public void changeOwner(DefaultOwner newOwner);
    public void addPosition(EntityId partnerRoleUid, EntityId positionUid);
    public void addPositionAndOrganization(EntityId partnerRoleUid, EntityId positionUid, EntityId organizationUnitUid);
    public void removePositionAndOrganization();
    public Set<DefaultPartnerRole> getAllAssignedRoles();
    public DefaultOwner getOwner();
    public void setOwner(DefaultOwner owner);
    public PartnerCategory getPartnerCategory();
    public Set<DefaultPartnerRole> getPartnerRoles();
    public String getTitle();
    public String getImage();
    public String getWebsite();
    public void addDepartment(EntityId partnerRoleUid, EntityId departmentUid);
    public void addDepartmentPositionAndOrganizationUnit(EntityId roleUid, String organizationUnitUid, String positionUid, String departmentUid);
}