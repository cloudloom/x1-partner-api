package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultMuseumResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultMuseumResourceBuilder {
    private String name;
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    private DefaultMuseumResourceBuilder() {

    }

    public static DefaultMuseumResourceBuilder aMuseumBuilder() {
        return new DefaultMuseumResourceBuilder();
    }

    public DefaultMuseumResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultMuseumResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultMuseumResource build() {
        DefaultMuseumResource museum = new DefaultMuseumResource();
        museum.setAddresses(this.addresses);
        museum.setName(this.name);
        return museum;
    }
}