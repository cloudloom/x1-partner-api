package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultMuseum;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultMuseumBuilder {
    private String name;
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    private DefaultMuseumBuilder() {

    }

    public static DefaultMuseumBuilder aMuseumBuilder() {
        return new DefaultMuseumBuilder();
    }

    public DefaultMuseumBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultMuseumBuilder withAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultMuseum build() {
        DefaultMuseum museum = new DefaultMuseum();
        museum.setAddresses(this.addresses);
        museum.setName(this.name);
        return museum;
    }
}