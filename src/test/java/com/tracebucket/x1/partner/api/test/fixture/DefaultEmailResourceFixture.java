package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.partner.api.rest.resources.DefaultEmailResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultEmailResourceBuilder;

import java.util.UUID;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultEmailResourceFixture {
    public static DefaultEmailResource standardEmail() {
        DefaultEmailResource email = DefaultEmailResourceBuilder.anEmailResourceBuilder()
                .withEmail(UUID.randomUUID().toString())
                .withEmailType(EmailType.BUSINESS)
                .build();
        return email;
    }
}