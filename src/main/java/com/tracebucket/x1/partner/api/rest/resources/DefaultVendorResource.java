package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

/**
 * @author fahad
 * @since 17-05-2016.
 */
public class DefaultVendorResource extends BaseResource {

    private static final String simpleName = "Vendor";
    private String vendorID;

    protected String firstName;

    protected String lastName;

    protected String middleName;

    private Set<DefaultPhoneResource> phones;

    private Set<DefaultEmailResource> email;

    private Set<DefaultAddressResource> addresses;
    private String organizationUnit;
    private boolean user;
    private String userName;

    public static String getSimpleName() {
        return simpleName;
    }

    public String getVendorID() {
        return vendorID;
    }

    public void setVendorID(String vendorID) {
        this.vendorID = vendorID;
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

    public Set<DefaultPhoneResource> getPhones() {
        return phones;
    }

    public void setPhones(Set<DefaultPhoneResource> phones) {
        this.phones = phones;
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

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
