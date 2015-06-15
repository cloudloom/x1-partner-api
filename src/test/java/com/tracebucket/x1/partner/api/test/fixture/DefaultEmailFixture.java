package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.partner.api.test.builder.DefaultEmailBuilder;

import java.util.UUID;

/**
 * Created by sadath on 25-Nov-14.
 */
public class DefaultEmailFixture {
    public static DefaultEmail standardEmail() {
        DefaultEmail email = DefaultEmailBuilder.anEmailBuilder()
                .withEmail(UUID.randomUUID().toString())
                .withEmailType(EmailType.BUSINESS)
                .build();
        return email;
    }
}
