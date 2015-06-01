package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultTransportProviderResource;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultTransportProviderResourceBuilder {
    private String name;
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    private DefaultTransportProviderResourceBuilder() {

    }

    public static DefaultTransportProviderResourceBuilder aTransportProviderBuilder() {
        return new DefaultTransportProviderResourceBuilder();
    }

    public DefaultTransportProviderResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultTransportProviderResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultTransportProviderResource build() {
        DefaultTransportProviderResource museum = new DefaultTransportProviderResource();
        museum.setAddresses(this.addresses);
        museum.setName(this.name);
        return museum;
    }
}