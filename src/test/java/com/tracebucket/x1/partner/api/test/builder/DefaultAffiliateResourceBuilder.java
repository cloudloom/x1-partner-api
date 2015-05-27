package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.Person;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAffiliateResource;

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
    private Set<Person> persons = new HashSet<Person>(0);

    private DefaultAffiliateResourceBuilder(){ }

    public static DefaultAffiliateResourceBuilder aPartnerBuilder(){
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

    public DefaultAffiliateResourceBuilder withPersons(Set<Person> persons) {
        this.persons = persons;
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

        return affiliateResource;
    }
}
