package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultOwnerResourceBuilder;

import java.util.Date;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerResourceFixture {

    public static DefaultOwnerResource standardOwner() {
        DefaultOwnerResource owner = DefaultOwnerResourceBuilder.anOwnerBuilder()
                .withName("Name " + new Date().getTime())
                .build();
        return owner;
    }

    public static DefaultOwnerResource standardOwner2() {
        DefaultOwnerResource owner = DefaultOwnerResourceBuilder.anOwnerBuilder()
                .withName("Name_UPDT "+ new Date().getTime())
                .build();
        return owner;
    }
}
