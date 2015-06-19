package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultEmployee;

import java.util.Set;

/**
 * Created by Vishwajit on 12-06-2015.
 */
public class DefaultEmployeeBuilder {

    private String employeeID;
    private Salutation salutation;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    private Set<DefaultPhone> phone;
    private Set<DefaultEmail> email;
    private Set<DefaultAddress> addresses;

    private DefaultEmployeeBuilder(){

    }

    public static DefaultEmployeeBuilder anEmployeeBuilder(){
        return new DefaultEmployeeBuilder();
    }


    public DefaultEmployeeBuilder withEmployeeID(String employeeID) {
        this.employeeID = employeeID;
        return this;
    }

    public DefaultEmployeeBuilder withSalutation(Salutation salutation) {
        this.salutation = salutation;
        return this;
    }

    public DefaultEmployeeBuilder withFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public DefaultEmployeeBuilder withLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public DefaultEmployeeBuilder withMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }



    public DefaultEmployeeBuilder withPhone(Set<DefaultPhone> phone) {
        this.phone = phone;
        return this;
    }

    public DefaultEmployeeBuilder withEmail(Set<DefaultEmail> email) {
        this.email = email;
        return this;
    }

    public DefaultEmployeeBuilder withAddresses(Set<DefaultAddress> addresses) {
        this.addresses = addresses;
        return this;
    }

    public DefaultEmployee build() {
        DefaultEmployee employee = new DefaultEmployee();
        employee.setFirstName(firstName);
        employee.setMiddleName(middleName);
        employee.setLastName(lastName);
        employee.setEmployeeID(employeeID);
        employee.setAddresses(addresses);
        employee.setEmail(email);
        employee.setPhone(phone);
        employee.setSalutation(salutation);

        return employee;
    }
}
