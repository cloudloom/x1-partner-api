package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerRoleResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 27-05-2015.
 */
public class DefaultPartnerResourceBuilder {

    protected String title;
    protected String image;
    protected String website;
    protected PartnerCategory partnerCategory;
    private DefaultOwnerResource ownerResource;
    protected Set<DefaultPartnerRoleResource> partnerRoleResources = new HashSet<DefaultPartnerRoleResource>(0);

    private DefaultPartnerResourceBuilder(){ }

    public static DefaultPartnerResourceBuilder aPartnerBuilder(){
        return new DefaultPartnerResourceBuilder();
    }


    public DefaultPartnerResourceBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public DefaultPartnerResourceBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public DefaultPartnerResourceBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public DefaultPartnerResourceBuilder withPartnerCategory(PartnerCategory partnerCategory) {
        this.partnerCategory = partnerCategory;
        return this;
    }

    public DefaultPartnerResourceBuilder withOwner(DefaultOwnerResource ownerResource) {
        this.ownerResource = ownerResource;
        return this;
    }

    public DefaultPartnerResourceBuilder withPartnerRoles(Set<DefaultPartnerRoleResource> partnerRoleResources) {
        this.partnerRoleResources = partnerRoleResources;
        return this;
    }

    public DefaultPartnerResource build(){

        DefaultPartnerResource partner = new DefaultPartnerResource();

        partner.setTitle(title);
        partner.setImage(image);
        partner.setWebsite(website);
        partner.setPartnerCategory(partnerCategory);
        partner.setOwner(ownerResource);
        partner.setPartnerRoles(partnerRoleResources);

        return partner;
    }
}
