package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.rest.resources.*;

import java.util.Date;
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
    private Set<DefaultPhoneResource> phone;
    private Set<DefaultEmailResource> email;
    private Set<DefaultAddressResource> addresses;
    private DefaultValidityResource validity;
    private Date dateOfBirth;
    private boolean user;
    private String searchTerm;

    private DefaultEmployeeResourceBuilder(){ }

    public static DefaultEmployeeResourceBuilder anEmployeeResourceBuilder(){
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

    public DefaultEmployeeResourceBuilder withPhone(Set<DefaultPhoneResource> phone) {
        this.phone = phone;
        return this;
    }

    public DefaultEmployeeResourceBuilder withEmail(Set<DefaultEmailResource> email) {
        this.email = email;
        return this;
    }

    public DefaultEmployeeResourceBuilder withAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultEmployeeResourceBuilder withValidity(DefaultValidityResource validity) {
        this.validity = validity;
        return this;
    }

    public DefaultEmployeeResourceBuilder withDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
        return this;
    }

    public DefaultEmployeeResourceBuilder withUser(boolean user) {
        this.user = user;
        return this;
    }

    public DefaultEmployeeResourceBuilder withSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
        return this;
    }

    public DefaultEmployeeResource build() {
        DefaultEmployeeResource employee = new DefaultEmployeeResource();
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setEmployeeID(employeeID);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setSalutation(salutation);
        employee.setAddresses(addresses);
        employee.setValidity(validity);
        employee.setDateOfBirth(dateOfBirth);
        employee.setUser(user);
        employee.setSearchTerm(searchTerm);
        return employee;
    }
}
