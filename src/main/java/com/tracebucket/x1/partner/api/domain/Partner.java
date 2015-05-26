package com.tracebucket.x1.partner.api.domain;

import com.tracebucket.tron.ddd.annotation.DomainMethod;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;

import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Partner {
    public void setPartnerCategory(PartnerCategory partnerCategory);
    public void movePartnerToCategory(PartnerCategory newPartnerCategory);
    public void addPartnerRole(DefaultPartnerRole newPartnerRole);
    public Boolean hasPartnerRole(DefaultPartnerRole partnerRole);
    public void addAddressToRole(DefaultPartnerRole partnerRole, Address address);
    public void moveRoleAddressTo(DefaultPartnerRole partnerRole, Address newAddress);
    public void changeOwner(DefaultOwner newOwner);
    public Set<DefaultPartnerRole> getAllAssignedRoles();
    public DefaultOwner getOwner();
    public void setOwner(DefaultOwner owner);
    public PartnerCategory getPartnerCategory();
}