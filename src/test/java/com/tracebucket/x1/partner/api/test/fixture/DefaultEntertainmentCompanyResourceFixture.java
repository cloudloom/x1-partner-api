package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEntertainmentCompanyResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPersonResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultEntertainmentCompanyResourceBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultEntertainmentCompanyResourceFixture {
    public static DefaultEntertainmentCompanyResource standardEntertainmentCompany() {
        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>();
        addresses.add(DefaultAddressResourceFixture.standardAddress());

        Set<DefaultPersonResource> persons = new HashSet<DefaultPersonResource>();
        persons.add(DefaultPersonResourceFixture.standardPerson());

        DefaultEntertainmentCompanyResource entertainmentCompany = DefaultEntertainmentCompanyResourceBuilder.anEntertainmentCompanyBuilder()
                .withName(UUID.randomUUID().toString())
                .withLogo(UUID.randomUUID().toString())
                .withWebsite(UUID.randomUUID().toString())
                .withAddresses(addresses)
                .withContactPersons(persons)
                .build();
        return entertainmentCompany;
    }
}