package com.tracebucket.x1.partner.api.test.builder;


import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 11-Aug-14.
 */
public class DefaultPartnerBuilder {

    protected String title;
    protected String image;
    protected String website;
    protected PartnerCategory partnerCategory;
    private DefaultOwner owner;
    protected Set<DefaultPartnerRole> partnerRole = new HashSet<DefaultPartnerRole>(0);

    private DefaultPartnerBuilder(){ }

    public static DefaultPartnerBuilder aPartnerBuilder(){
        return new DefaultPartnerBuilder();
    }


    public DefaultPartnerBuilder withTitle(String title) {
        this.title = title;
        return this;
    }

    public DefaultPartnerBuilder withImage(String image) {
        this.image = image;
        return this;
    }

    public DefaultPartnerBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public DefaultPartnerBuilder withPartnerCategory(PartnerCategory partnerCategory) {
        this.partnerCategory = partnerCategory;
        return this;
    }

    public DefaultPartnerBuilder withOwner(DefaultOwner owner) {
        this.owner = owner;
        return this;
    }

    public DefaultPartnerBuilder withPartnerRoles(Set<DefaultPartnerRole> partnerRole) {
        this.partnerRole = partnerRole;
        return this;
    }

    public DefaultPartner build(){

        DefaultPartner partner = new DefaultPartner();

        partner.setTitle(title);
        partner.setImage(image);
        partner.setWebsite(website);
        partner.setPartnerCategory(partnerCategory);
        partner.setOwner(owner);
        partner.setPartnerRoles(partnerRole);
        return partner;
    }
}
