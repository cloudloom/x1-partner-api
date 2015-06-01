package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultTransportProviderResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultTransportProviderResourceBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultTransportProviderResourceFixture {
    public static DefaultTransportProviderResource standardTransportProvider() {
        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>();
        addresses.add(DefaultAddressResourceFixture.standardAddress());

        DefaultTransportProviderResource transportProvider = DefaultTransportProviderResourceBuilder.aTransportProviderBuilder()
                .withName(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .build();
        return transportProvider;
    }
}