package com.tracebucket.x1.partner.api.domain;

import com.tracebucket.x1.dictionary.api.domain.Person;

import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public interface EntertainmentCompany {
    public String getName();
    public void setName(String name);
    public String getWebsite();
    public void setWebsite(String website);
    public String getLogo();
    public void setLogo(String logo);
    public Set<Person> getContactPersons();
    public void setContactPersons(Set<Person> contactPersons);
}