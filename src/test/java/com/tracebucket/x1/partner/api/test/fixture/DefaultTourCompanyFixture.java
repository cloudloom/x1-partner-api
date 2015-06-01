package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultTourCompany;
import com.tracebucket.x1.partner.api.test.builder.DefaultTourCompanyBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultTourCompanyFixture {
    public static DefaultTourCompany standardTourCompany() {
        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>();
        addresses.add(DefaultAddressFixture.standardAddress());

        DefaultTourCompany tourCompany = DefaultTourCompanyBuilder.aTourCompanyBuilder()
                .withName(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .build();
        return tourCompany;
    }
}