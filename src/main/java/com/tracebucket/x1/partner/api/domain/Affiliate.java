package com.tracebucket.x1.partner.api.domain;

import com.tracebucket.x1.dictionary.api.domain.Person;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;

import java.util.Date;
import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Affiliate {
    public String getBusinessName();
    public void setBusinessName(String businessName);
    public String getCode();
    public void setCode(String code);
    public Date getDateOfIncorporation();
    public void setDateOfIncorporation(Date dateOfIncorporation);
    public String getLogo();
    public void setLogo(String logo);
    public String getWebsite();
    public void setWebsite(String website);
    public Set<DefaultPerson> getPersons();
    public void setPersons(Set<DefaultPerson> persons);
}