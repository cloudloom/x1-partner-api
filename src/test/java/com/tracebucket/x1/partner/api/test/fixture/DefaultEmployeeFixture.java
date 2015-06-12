package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;
import com.tracebucket.x1.partner.api.test.builder.DefaultEmployeeBuilder;

/**
 * Created by Vishwajit on 12-06-2015.
 */
public class DefaultEmployeeFixture {

    public static DefaultEmployee standardEmployee() {

        DefaultEmployee employee = DefaultEmployeeBuilder.anEmployeeBuilder()
                .withFirstName("Vishwa")
                .withMiddleName("V")
                .withLastName("B")
                .withEmployeeID("987654321")
                .build();
        return employee;
    }
}
