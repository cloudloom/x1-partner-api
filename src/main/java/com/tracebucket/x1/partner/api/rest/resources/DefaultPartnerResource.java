package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.tron.ddd.annotation.DomainMethod;
import com.tracebucket.tron.ddd.domain.BaseAggregateRoot;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.Partner;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public class DefaultPartnerResource extends BaseResource{
    private String title;
    private String image;
    private String website;
    private PartnerCategory partnerCategory;
    private DefaultOwnerResource owner;
    private Set<DefaultPartnerRoleResource> partnerRoles = new HashSet<DefaultPartnerRoleResource>(0);

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public PartnerCategory getPartnerCategory() {
        return partnerCategory;
    }

    public void setPartnerCategory(PartnerCategory partnerCategory) {
        this.partnerCategory = partnerCategory;
    }

    public DefaultOwnerResource getOwner() {
        return owner;
    }

    public void setOwner(DefaultOwnerResource owner) {
        this.owner = owner;
    }

    public Set<DefaultPartnerRoleResource> getPartnerRoles() {
        return partnerRoles;
    }

    public void setPartnerRoles(Set<DefaultPartnerRoleResource> partnerRoles) {
        this.partnerRoles = partnerRoles;
    }
}