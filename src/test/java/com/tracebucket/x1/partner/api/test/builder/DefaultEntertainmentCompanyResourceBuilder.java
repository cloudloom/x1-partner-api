package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEntertainmentCompanyResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPersonResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultEntertainmentCompanyResourceBuilder {
    private String name;
    private String website;
    private String logo;
    private Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    private DefaultEntertainmentCompanyResourceBuilder() {

    }

    public static DefaultEntertainmentCompanyResourceBuilder anEntertainmentCompanyBuilder() {
        return new DefaultEntertainmentCompanyResourceBuilder();
    }

    public DefaultEntertainmentCompanyResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultEntertainmentCompanyResourceBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public DefaultEntertainmentCompanyResourceBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public DefaultEntertainmentCompanyResourceBuilder withContactPersons(Set<DefaultPersonResource> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }

    public DefaultEntertainmentCompanyResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultEntertainmentCompanyResource build() {
        DefaultEntertainmentCompanyResource entertainmentCompany = new DefaultEntertainmentCompanyResource();
        entertainmentCompany.setName(this.name);
        entertainmentCompany.setContactPersons(this.contactPersons);
        entertainmentCompany.setLogo(this.logo);
        entertainmentCompany.setWebsite(this.website);
        entertainmentCompany.setAddresses(this.addresses);
        return entertainmentCompany;
    }
}