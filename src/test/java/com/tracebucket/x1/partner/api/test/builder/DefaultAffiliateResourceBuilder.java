package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAffiliateResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPersonResource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 27-05-2015.
 */
public class DefaultAffiliateResourceBuilder {

    private String businessName;
    private String code;
    private Date dateOfIncorporation;
    private String logo;
    private String website;
    private Set<DefaultPersonResource> persons = new HashSet<DefaultPersonResource>(0);
    private String name;
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    private DefaultAffiliateResourceBuilder(){ }

    public static DefaultAffiliateResourceBuilder anAffiliateBuilder(){
        return new DefaultAffiliateResourceBuilder();
    }

    public DefaultAffiliateResourceBuilder withBusinessName(String businessName) {
        this.businessName = businessName;
        return this;
    }

    public DefaultAffiliateResourceBuilder withCode(String code) {
        this.code = code;
        return this;
    }

    public DefaultAffiliateResourceBuilder withDateOfIncorporation(Date dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
        return this;
    }

    public DefaultAffiliateResourceBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public DefaultAffiliateResourceBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public DefaultAffiliateResourceBuilder withPersons(Set<DefaultPersonResource> persons) {
        this.persons = persons;
        return this;
    }

    public DefaultAffiliateResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultAffiliateResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultAffiliateResource build() {

        DefaultAffiliateResource affiliateResource = new DefaultAffiliateResource();
        affiliateResource.setWebsite(website);
        affiliateResource.setBusinessName(businessName);
        affiliateResource.setCode(code);
        affiliateResource.setDateOfIncorporation(dateOfIncorporation );
        affiliateResource.setLogo(logo);
        affiliateResource.setPersons(persons);
        affiliateResource.setName(name);
        affiliateResource.setAddresses(addresses);
        return affiliateResource;
    }
}
