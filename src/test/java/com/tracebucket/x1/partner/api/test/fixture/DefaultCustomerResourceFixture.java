package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultCustomerResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultCustomerResourceBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultCustomerResourceFixture {
    public static DefaultCustomerResource standardCustomer() {
        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>();
        addresses.add(DefaultAddressResourceFixture.standardAddress());

        DefaultCustomerResource customer = DefaultCustomerResourceBuilder.aCustomerBuilder()
                .withName(UUID.randomUUID().toString())
                .withBirthDay(new Date())
                .withEmail(UUID.randomUUID().toString())
                .withFirstName(UUID.randomUUID().toString())
                .withGender(Gender.MALE)
                .withInitial(UUID.randomUUID().toString())
                .withLastName(UUID.randomUUID().toString())
                .withMiddleName(UUID.randomUUID().toString())
                .withSalutation(Salutation.Mr)
                .withAddresses(addresses)
                .build();
        return customer;
    }
}