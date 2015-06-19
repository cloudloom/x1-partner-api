package com.tracebucket.x1.partner.api.test.fixture;


import com.tracebucket.x1.dictionary.api.domain.EmailType;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartnerRole;
import com.tracebucket.x1.partner.api.test.builder.DefaultPartnerBuilder;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 11-Aug-14.
 */
public class DefaultPartnerFixture {

    public static DefaultPartner standardPartner() {

        Set<DefaultPartnerRole> partnerRoles = new HashSet<DefaultPartnerRole>();

        Set<DefaultPhone> empPhone = new HashSet<>();
        DefaultPhone phone = new DefaultPhone();
        phone.setNumber(9441009922L);
        phone.setDefaultPhone(true);
        phone.setPhoneType(PhoneType.MOBILE);
        empPhone.add(phone);

        Set<DefaultEmail> empEmail = new HashSet<>();
        DefaultEmail email = new DefaultEmail();
        email.setDefaultEmail(true);
        email.setEmail("xyz@mmpsd.nl");
        email.setEmailType(EmailType.BUSINESS);
        empEmail.add(email);

        DefaultEmployee employee = DefaultEmployeeFixture.standardEmployee();
        employee.setPhone(empPhone);
        employee.setEmail(empEmail);
        partnerRoles.add(employee);

        DefaultPartner partner = DefaultPartnerBuilder.aPartnerBuilder()
                .withPartnerCategory(PartnerCategory.GROUP)
                .withImage("logo_1")
                .withTitle("title_1")
                .withPartnerRoles(partnerRoles)
                .withWebsite("www.yyy.nl").build();
        return partner;
    }

    public static DefaultPartner standardPartnerWithOwner(DefaultOwner owner) {

        Set<DefaultPartnerRole> partnerRoles = new HashSet<DefaultPartnerRole>();

        Set<DefaultPhone> empPhone = new HashSet<>();
        DefaultPhone phone = new DefaultPhone();
        phone.setNumber(9441009922L);
        phone.setDefaultPhone(true);
        phone.setPhoneType(PhoneType.MOBILE);
        empPhone.add(phone);

        Set<DefaultEmail> empEmail = new HashSet<DefaultEmail>();
        DefaultEmail email = new DefaultEmail();
        email.setDefaultEmail(true);
        email.setEmail("xyz@mmpsd.nl");
        email.setEmailType(EmailType.BUSINESS);
        empEmail.add(email);

        DefaultEmployee employee = DefaultEmployeeFixture.standardEmployee();
        employee.setPhone(empPhone);
        employee.setEmail(empEmail);
        partnerRoles.add(employee);

        DefaultPartner partner = DefaultPartnerBuilder.aPartnerBuilder()
                .withPartnerCategory(PartnerCategory.GROUP)
                .withImage("logo_1")
                .withTitle("title_1")
                .withPartnerRoles(partnerRoles)
                .withOwner(owner)
                .withWebsite("www.yyy.nl").build();
        return partner;
    }
}
