package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.partner.api.dictionary.Salutation;

import java.util.Set;

/**
 * Created by Vishwajit on 12-06-2015.
 */
public class DefaultEmployeeResource extends BaseResource {

    private String employeeID;
    private Salutation salutation;
    protected String firstName;
    protected String lastName;
    protected String middleName;
    private Set<DefaultPhone> phone;
    private Set<DefaultEmail> email;

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Set<DefaultPhone> getPhone() {
        return phone;
    }

    public void setPhone(Set<DefaultPhone> phone) {
        this.phone = phone;
    }

    public Set<DefaultEmail> getEmail() {
        return email;
    }

    public void setEmail(Set<DefaultEmail> email) {
        this.email = email;
    }
}
