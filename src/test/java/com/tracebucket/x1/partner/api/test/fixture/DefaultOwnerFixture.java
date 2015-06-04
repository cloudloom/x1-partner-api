package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.test.builder.DefaultOwnerBuilder;

import java.util.Date;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerFixture {

    public static DefaultOwner standardOwner(String organizationUID) {
        DefaultOwner owner = DefaultOwnerBuilder.anOwnerBuilder()
                .withOrganizationUID(organizationUID)
                .build();
        return owner;
    }
}