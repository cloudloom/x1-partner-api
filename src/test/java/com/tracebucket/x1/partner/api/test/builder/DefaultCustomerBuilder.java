package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultCustomer;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultCustomerBuilder {
    private String initial;
    private Salutation salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDay;
    private Gender gender;
    private String email;
    private String name;
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

    private DefaultCustomerBuilder() {

    }

    public static DefaultCustomerBuilder aCustomerBuilder() {
        return new DefaultCustomerBuilder();
    }

    public DefaultCustomerBuilder withInitial(String initial) {
        this.initial = initial;
        return this;
    }

    public DefaultCustomerBuilder withSalutation(Salutation salutation) {
        this.salutation = salutation;
        return this;
    }

    public DefaultCustomerBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DefaultCustomerBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public DefaultCustomerBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DefaultCustomerBuilder withBirthDay(Date birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public DefaultCustomerBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public DefaultCustomerBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public DefaultCustomerBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultCustomerBuilder withAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultCustomer build() {
        DefaultCustomer customer = new DefaultCustomer();
        customer.setInitial(this.initial);
        customer.setSalutation(this.salutation);
        customer.setFirstName(this.firstName);
        customer.setMiddleName(this.middleName);
        customer.setLastName(this.lastName);
        customer.setBirthDay(this.birthDay);
        customer.setGender(this.gender);
        customer.setEmail(this.email);
        customer.setName(this.name);
        customer.setAddresses(this.addresses);
        return customer;
    }
}