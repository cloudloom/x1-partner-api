package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEmployeeResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultEmployeeBuilder;
import com.tracebucket.x1.partner.api.test.builder.DefaultEmployeeResourceBuilder;

/**
 * Created by Vishwajit on 12-06-2015.
 */
public class DefaultEmployeeResourceFixture {

    public static DefaultEmployeeResource standardEmployee() {

        DefaultEmployeeResource employee = DefaultEmployeeResourceBuilder.anEmployeeResourceBuilder()
                .withFirstName("Vishwa")
                .withMiddleName("V")
                .withLastName("B")
                .withEmployeeID("987654321")
                .build();
        return employee;
    }
}
