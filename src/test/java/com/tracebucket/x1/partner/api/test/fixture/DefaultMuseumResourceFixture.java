package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultMuseumResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultMuseumResourceBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultMuseumResourceFixture {
    public static DefaultMuseumResource standardMuseum() {
        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>();
        addresses.add(DefaultAddressResourceFixture.standardAddress());

        DefaultMuseumResource museum = DefaultMuseumResourceBuilder.aMuseumBuilder()
                .withName(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .build();
        return museum;
    }
}