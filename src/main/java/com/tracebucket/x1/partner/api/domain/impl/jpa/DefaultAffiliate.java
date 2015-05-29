package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.domain.Affiliate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 05-Aug-14.
 */
@Entity(name = "PARTNER_AFFILIATE")
@Table(name = "PARTNER_AFFILIATE")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_AFFILIATE")
public class DefaultAffiliate extends DefaultPartnerRole implements Affiliate, Serializable {
    private static final String simpleName = "Affiliate";

    @Column(name = "BUSINESS_NAME")
    @Basic(fetch = FetchType.EAGER)
    private String businessName;

    @Column(name = "CODE")
    @Basic(fetch = FetchType.EAGER)
    private String code;

    @Column(name = "DATE_OF_INCORPORATION")
    @Basic(fetch = FetchType.EAGER)
    private Date dateOfIncorporation;

    @Column(name = "LOGO")
    @Basic(fetch = FetchType.EAGER)
    private String logo;

    @Column(name = "WEBSITE")
    @Basic(fetch = FetchType.EAGER)
    private String website;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinTable(
            name="AFFILIATE_PERSON",
            joinColumns={ @JoinColumn(name="AFFILIATE__ID", referencedColumnName="ID") },
            inverseJoinColumns={ @JoinColumn(name="PERSON__ID", referencedColumnName="ID", unique=false) }
    )
    private Set<DefaultPerson> persons = new HashSet<DefaultPerson>(0);

    @Override
    public String getBusinessName() {
        return businessName;
    }

    @Override
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public Date getDateOfIncorporation() {
        return dateOfIncorporation;
    }

    @Override
    public void setDateOfIncorporation(Date dateOfIncorporation) {
        this.dateOfIncorporation = dateOfIncorporation;
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
    public String getWebsite() {
        return website;
    }

    @Override
    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public Set<DefaultPerson> getPersons() {
        return persons;
    }

    @Override
    public void setPersons(Set<DefaultPerson> persons) {
        this.persons = persons;
    }

    @Override
    public String simpleName() {
        return simpleName;
    }
}
