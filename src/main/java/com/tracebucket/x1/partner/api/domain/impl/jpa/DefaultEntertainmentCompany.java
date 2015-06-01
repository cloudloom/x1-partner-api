package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.domain.EntertainmentCompany;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by ffl on 29-10-2014.
 */
@Entity(name = "PARTNER_ENTERTAINMENT_COMPANY")
@Table(name = "PARTNER_ENTERTAINMENT_COMPANY")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_ENTERTAINMENT_COMPANY")
public class DefaultEntertainmentCompany extends DefaultPartnerRole implements EntertainmentCompany, Serializable{
    private static final String simpleName = "Entertainment Company";

    @Override
    public String simpleName() {
        return simpleName;
    }

    @Column(name = "NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String name;

    @Column(name = "WEBSITE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String website;

    @Column(name = "LOGO", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String logo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name="ENT_COMP_PERSONS",
            joinColumns={ @JoinColumn(name="ENTERTAINMENT_COMPANY__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="PERSON__ID", referencedColumnName="ID", unique=false) }
    )
    protected Set<DefaultPerson> contactPersons = new HashSet<DefaultPerson>(0);

/**Contract terms to be defined*/

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getWebsite() {
        return website;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public String getLogo() {
        return logo;
    }

    @Override
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public Set<DefaultPerson> getContactPersons() {
        return contactPersons;
    }

    @Override
    public void setContactPersons(Set<DefaultPerson> contactPersons) {
        this.contactPersons = contactPersons;
    }
}