package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultTourCompanyResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultTourCompanyResourceBuilder {
    private String name;
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    private DefaultTourCompanyResourceBuilder() {

    }

    public static DefaultTourCompanyResourceBuilder aTourCompanyBuilder() {
        return new DefaultTourCompanyResourceBuilder();
    }

    public DefaultTourCompanyResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultTourCompanyResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultTourCompanyResource build() {
        DefaultTourCompanyResource museum = new DefaultTourCompanyResource();
        museum.setAddresses(this.addresses);
        museum.setName(this.name);
        return museum;
    }
}