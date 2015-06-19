package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEmailResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEmployeeResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPhoneResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultEmployeeResourceBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 12-06-2015.
 */
public class DefaultEmployeeResourceFixture {

    public static DefaultEmployeeResource standardEmployee() {
        Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>();
        addresses.add(DefaultAddressResourceFixture.standardAddress());

        Set<DefaultPhoneResource> phone = new HashSet<DefaultPhoneResource>();
        phone.add(DefaultPhoneResourceFixture.standardPhone());

        Set<DefaultEmailResource> email = new HashSet<DefaultEmailResource>();
        email.add(DefaultEmailResourceFixture.standardEmail());

        DefaultEmployeeResource employee = DefaultEmployeeResourceBuilder.anEmployeeResourceBuilder()
                .withFirstName("Vishwa")
                .withMiddleName("V")
                .withLastName("B")
                .withEmployeeID("987654321")
                .withAddresses(addresses)
                .withPhone(phone)
                .withEmail(email)
                .build();
        return employee;
    }
}
