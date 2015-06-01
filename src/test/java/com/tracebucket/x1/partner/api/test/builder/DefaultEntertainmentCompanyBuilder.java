package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEntertainmentCompany;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultEntertainmentCompanyBuilder {
    private String name;
    private String website;
    private String logo;
    private Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    private DefaultEntertainmentCompanyBuilder() {

    }

    public static DefaultEntertainmentCompanyBuilder anEntertainmentCompanyBuilder() {
        return new DefaultEntertainmentCompanyBuilder();
    }

    public DefaultEntertainmentCompanyBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultEntertainmentCompanyBuilder withWebsite(String website) {
        this.website = website;
        return this;
    }

    public DefaultEntertainmentCompanyBuilder withLogo(String logo) {
        this.logo = logo;
        return this;
    }

    public DefaultEntertainmentCompanyBuilder withContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
        return this;
    }

    public DefaultEntertainmentCompanyBuilder withAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultEntertainmentCompany build() {
        DefaultEntertainmentCompany entertainmentCompany = new DefaultEntertainmentCompany();
        entertainmentCompany.setName(this.name);
        entertainmentCompany.setContactPersons(this.contactPersons);
        entertainmentCompany.setLogo(this.logo);
        entertainmentCompany.setWebsite(this.website);
        entertainmentCompany.setAddresses(this.addresses);
        return entertainmentCompany;
    }
}