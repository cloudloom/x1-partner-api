package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.*;

/**
 * Created by Vishwajit on 27-05-2015.
 */
public class DefaultPartnerResourceBuilder {

    private String title;
    private String image;
    private String website;
    private PartnerCategory partnerCategory;
    private DefaultOwnerResource ownerResource;
    private DefaultAffiliateResource affiliate;
    private DefaultCustomerResource customer;
    private DefaultEntertainmentCompanyResource entertainmentCompany;
    private DefaultMuseumResource museum;
    private DefaultTourCompanyResource tourCompany;
    private DefaultTransportProviderResource transportProvider;

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

    public DefaultPartnerResourceBuilder withAffiliate(DefaultAffiliateResource affiliate) {
        this.affiliate = affiliate;
        return this;
    }

    public DefaultPartnerResourceBuilder withCustomer(DefaultCustomerResource customer) {
        this.customer = customer;
        return this;
    }

    public DefaultPartnerResourceBuilder withEntertainmentCompany(DefaultEntertainmentCompanyResource entertainmentCompany) {
        this.entertainmentCompany = entertainmentCompany;
        return this;
    }

    public DefaultPartnerResourceBuilder withMuseum(DefaultMuseumResource museum) {
        this.museum = museum;
        return this;
    }

    public DefaultPartnerResourceBuilder withTourCompany(DefaultTourCompanyResource tourCompany) {
        this.tourCompany = tourCompany;
        return this;
    }

    public DefaultPartnerResourceBuilder withTransportProvider(DefaultTransportProviderResource transportProvider) {
        this.transportProvider = transportProvider;
        return this;
    }

    public DefaultPartnerResource build(){
        DefaultPartnerResource partner = new DefaultPartnerResource();
        partner.setTitle(title);
        partner.setImage(image);
        partner.setWebsite(website);
        partner.setPartnerCategory(partnerCategory);
        partner.setOwner(ownerResource);
        partner.setAffiliate(affiliate);
        partner.setCustomer(customer);
        partner.setEntertainmentCompany(entertainmentCompany);
        partner.setMuseum(museum);
        partner.setTourCompany(tourCompany);
        partner.setTransportProvider(transportProvider);
        return partner;
    }
}
