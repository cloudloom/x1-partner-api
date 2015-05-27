package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.test.builder.DefaultOwnerBuilder;

import java.util.Date;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerFixture {

    public static DefaultOwner standardOwner() {
        DefaultOwner owner = DefaultOwnerBuilder.anOwnerBuilder()
                .withName("Name " + new Date().getTime())
                .build();
        return owner;
    }

    public static DefaultOwner standardOwner2() {
        DefaultOwner owner = DefaultOwnerBuilder.anOwnerBuilder()
                .withName("Name_UPDT "+ new Date().getTime())
                .build();
        return owner;
    }
}
