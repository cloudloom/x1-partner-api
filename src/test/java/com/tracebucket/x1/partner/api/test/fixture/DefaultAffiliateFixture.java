package com.tracebucket.x1.partner.api.test.fixture;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPerson;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultAffiliate;
import com.tracebucket.x1.partner.api.test.builder.DefaultAffiliateBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 11-Aug-14.
 */
public class DefaultAffiliateFixture {
    public static DefaultAffiliate standardAffiliate() {
        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);
        //addresses.add(DefaultAddressFixture.standardAddress());
        //addresses.add(DefaultAddressFixture.headOffice());

        Set<DefaultPerson> persons = new HashSet<DefaultPerson>(0);
        persons.add(DefaultPersonFixture.standardPerson());
        persons.add(DefaultPersonFixture.standardPerson2());

        DefaultAffiliate affiliate = DefaultAffiliateBuilder.anAffiliateBuilder()
                .withName("Affiliate " + new Date().getTime())
                .withCode("Code " + new Date().getTime())
                .withBusinessName("Business Name "+ new Date().getTime())
                .withDateOfIncorporation(new Date())
                .withLogo("Logo")
                .withWebsite("http://test.com")
                        //.withAddresses(addresses)
                .withPersons(persons)
                        //.withSaleChannels(saleChannels)
                .build();
        return affiliate;
    }

    public static DefaultAffiliate standardAffiliate2() {
        Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);
        //addresses.add(DefaultAddressFixture.standardAddress());
        //addresses.add(DefaultAddressFixture.headOffice());

        Set<DefaultPerson> persons = new HashSet<DefaultPerson>(0);
        persons.add(DefaultPersonFixture.standardPerson());
        persons.add(DefaultPersonFixture.standardPerson2());

        DefaultAffiliate affiliate = DefaultAffiliateBuilder.anAffiliateBuilder()
                .withName("Affiliate " + new Date().getTime())
                .withCode("Code " + new Date().getTime())
                .withBusinessName("Business Name "+ new Date().getTime())
                .withDateOfIncorporation(new Date())
                .withLogo("Logo "+new Date().getTime())
                .withWebsite("http://test.com " + new Date().getTime())
                        //.withAddresses(addresses)
                .withPersons(persons)
                        //.withSaleChannels(saleChannels)
                .build();
        return affiliate;
    }
}
