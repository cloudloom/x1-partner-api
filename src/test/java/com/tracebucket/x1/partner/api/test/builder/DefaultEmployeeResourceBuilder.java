package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.partner.api.dictionary.Salutation;

import java.util.Set;

/**
 * Created by Vishwajit on 12-06-2015.
 */
public class DefaultEmployeeResourceBuilder {

    private String employeeID;
    private Salutation salutation;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    private Set<DefaultPhone> phone;
    private Set<DefaultEmail> email;

    private DefaultEmployeeResourceBuilder(){ }

    public static DefaultEmployeeResourceBuilder aPartnerBuilder(){
        return new DefaultEmployeeResourceBuilder();
    }

    public DefaultEmployeeResourceBuilder withEmployeeID(String employeeID) {
        this.employeeID = employeeID;
        return this;
    }

    public DefaultEmployeeResourceBuilder withSalutation(Salutation salutation) {
        this.salutation = salutation;
        return this;
    }

    public DefaultEmployeeResourceBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DefaultEmployeeResourceBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DefaultEmployeeResourceBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public DefaultEmployeeResourceBuilder withPhone(Set<DefaultPhone> phone) {
        this.phone = phone;
        return this;
    }

    public DefaultEmployeeResourceBuilder withEmail(Set<DefaultEmail> email) {
        this.email = email;
        return this;
    }
}
