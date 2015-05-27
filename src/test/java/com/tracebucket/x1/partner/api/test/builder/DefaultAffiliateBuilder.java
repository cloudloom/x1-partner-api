package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultAffiliate;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 11-Aug-14.
 */
public class DefaultAffiliateBuilder {
    private String name;
    private String businessName;
    private String code;
    private Date dateOfIncorporation;
    private String logo;
    private String website;
    private Set<DefaultPerson> persons = new HashSet<DefaultPerson>(0);
  //  private Set<SaleChannel> saleChannels = new HashSet<>(0);
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    public DefaultAffiliateBuilder(){

    }

    public static DefaultAffiliateBuilder aAffiliate(){
        return new DefaultAffiliateBuilder();
    }

    public DefaultAffiliateBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultAffiliateBuilder withBusinessName(String businessName){
        this.businessName = businessName;
        return this;
    }

    public DefaultAffiliateBuilder withCode(String code){
        this.code = code;
        return this;
    }

    public DefaultAffiliateBuilder withDateOfIncorporation(Date dateOfIncorporation){
        this.dateOfIncorporation = dateOfIncorporation;
        return this;
    }

    public DefaultAffiliateBuilder withLogo(String logo){
        this.logo = logo;
        return this;
    }

    public DefaultAffiliateBuilder withWebsite(String website){
        this.website = website;
        return this;
    }

    public DefaultAffiliateBuilder withPersons(Set<DefaultPerson> persons){
        this.persons = persons;
        return this;
    }

   /* public AffiliateBuilder withSaleChannels(Set<SaleChannel> saleChannels){
        this.saleChannels = saleChannels;
        return this;
    }*/

    public DefaultAffiliateBuilder withAddresses(Set<DefaultAddress> addresses){
        this.addresses = addresses;
        return this;
    }

    public DefaultAffiliate build() {
        DefaultAffiliate affiliate = new DefaultAffiliate();
        affiliate.setName(name);
        affiliate.setCode(code);
        affiliate.setBusinessName(businessName);
        affiliate.setDateOfIncorporation(dateOfIncorporation);
        affiliate.setLogo(logo);
        //affiliate.setAddresses(addresses);
        affiliate.setPersons(persons);
       // affiliate.setSaleChannels(saleChannels);
        affiliate.setWebsite(website);
        return affiliate;
    }
}
