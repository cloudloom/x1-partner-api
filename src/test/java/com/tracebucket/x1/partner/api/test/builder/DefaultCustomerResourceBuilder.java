package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.rest.resources.DefaultAddressResource;
import com.tracebucket.x1.partner.api.rest.resources.DefaultCustomerResource;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
public class DefaultCustomerResourceBuilder {
    private String initial;
    private Salutation salutation;
    private String firstName;
    private String middleName;
    private String lastName;
    private Date birthDay;
    private Gender gender;
    private String email;
    private String name;
    private Set<DefaultAddressResource> addresses = new HashSet<DefaultAddressResource>(0);

    private DefaultCustomerResourceBuilder() {

    }

    public static DefaultCustomerResourceBuilder aCustomerBuilder() {
        return new DefaultCustomerResourceBuilder();
    }

    public DefaultCustomerResourceBuilder withInitial(String initial) {
        this.initial = initial;
        return this;
    }

    public DefaultCustomerResourceBuilder withSalutation(Salutation salutation) {
        this.salutation = salutation;
        return this;
    }

    public DefaultCustomerResourceBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DefaultCustomerResourceBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public DefaultCustomerResourceBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DefaultCustomerResourceBuilder withBirthDay(Date birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public DefaultCustomerResourceBuilder withGender(Gender gender) {
        this.gender = gender;
        return this;
    }

    public DefaultCustomerResourceBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public DefaultCustomerResourceBuilder withName(String name) {
        this.name = name;
        return this;
    }

    public DefaultCustomerResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultCustomerResource build() {
        DefaultCustomerResource customer = new DefaultCustomerResource();
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