package com.tracebucket.x1.partner.api.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.partner.api.dictionary.Salutation;

import java.util.Date;
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
    private Set<DefaultPhoneResource> phone;
    private Set<DefaultEmailResource> email;
    private Set<DefaultAddressResource> addresses;
    private String position;
    private DefaultValidityResource validity;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;
    private boolean user;
    private String searchTerm;

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

    public Set<DefaultPhoneResource> getPhone() {
        return phone;
    }

    public void setPhone(Set<DefaultPhoneResource> phone) {
        this.phone = phone;
    }

    public Set<DefaultEmailResource> getEmail() {
        return email;
    }

    public void setEmail(Set<DefaultEmailResource> email) {
        this.email = email;
    }

    public Set<DefaultAddressResource> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<DefaultAddressResource> addresses) {
        this.addresses = addresses;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public DefaultValidityResource getValidity() {
        return validity;
    }

    public void setValidity(DefaultValidityResource validity) {
        this.validity = validity;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public String getSearchTerm() {
        return searchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        this.searchTerm = searchTerm;
    }
}
