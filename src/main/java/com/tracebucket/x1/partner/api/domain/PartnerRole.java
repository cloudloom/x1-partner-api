package com.tracebucket.x1.partner.api.domain;

import com.tracebucket.x1.dictionary.api.domain.Address;

import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public interface PartnerRole {
    public String getName();
    public void setName(String name);
    public Set<Address> getAddresses();
    public void setAddresses(Set<Address> addresses);
}