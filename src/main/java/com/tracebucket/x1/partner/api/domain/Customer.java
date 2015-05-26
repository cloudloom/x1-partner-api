package com.tracebucket.x1.partner.api.domain;

import com.tracebucket.x1.dictionary.api.domain.Gender;

import java.util.Date;

/**
 * Created by sadath on 26-May-2015.
 */
public interface Customer {
    public String getFirstName();
    public void setFirstName(String firstName);
    public String getMiddleName();
    public void setMiddleName(String middleName);
    public String getLastName();
    public void setLastName(String lastName);
    public Date getBirthDay();
    public void setBirthDay(Date birthDay);
    public Gender getGender();
    public void setGender(Gender gender);
    public String getEmail();
    public void setEmail(String email);
}