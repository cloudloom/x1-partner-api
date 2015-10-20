package com.tracebucket.x1.partner.api.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 20-Oct-2015.
 */
@JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultNotifyTo {
    private Set<String> emails = new HashSet<String>(0);
    private Set<String> phoneNos = new HashSet<String>(0);

    public Set<String> getEmails() {
        return emails;
    }

    public void setEmails(Set<String> emails) {
        this.emails = emails;
    }

    public Set<String> getPhoneNos() {
        return phoneNos;
    }

    public void setPhoneNos(Set<String> phoneNos) {
        this.phoneNos = phoneNos;
    }
}