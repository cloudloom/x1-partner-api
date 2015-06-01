package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultTransportProvider;
import com.tracebucket.x1.partner.api.test.builder.DefaultTransportProviderBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultTransportProviderFixture {
    public static DefaultTransportProvider standardTransportProvider() {
        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>();
        addresses.add(DefaultAddressFixture.standardAddress());

        DefaultTransportProvider transportProvider = DefaultTransportProviderBuilder.aTransportProviderBuilder()
                .withName(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .build();
        return transportProvider;
    }
}