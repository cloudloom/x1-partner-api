package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultTourCompanyResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultTourCompanyResourceBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultTourCompanyResourceFixture {
    public static DefaultTourCompanyResource standardTourCompany() {
        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>();
        addresses.add(DefaultAddressResourceFixture.standardAddress());

        DefaultTourCompanyResource tourCompany = DefaultTourCompanyResourceBuilder.aTourCompanyBuilder()
                .withName(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .build();
        return tourCompany;
    }
}