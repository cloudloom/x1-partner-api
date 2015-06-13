package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPartnerResource;
import com.tracebucket.x1.partner.api.test.builder.DefaultPartnerResourceBuilder;

/**
 * Created by Vishwajit on 27-05-2015.
 */
public class DefaultPartnerResourceFixture {

    public static DefaultPartnerResource standardPartner() {
        DefaultAddressResource address = DefaultAddressResourceFixture.standardAddress();

        DefaultPartnerResource partner = DefaultPartnerResourceBuilder.aPartnerBuilder()
                                          .withPartnerCategory(PartnerCategory.GROUP)
                                          .withImage("logo_1")
                                          .withTitle("title_1")
                                          .withWebsite("www.yyy.nl")

                .build();
        return partner;
    }
}
