package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.partner.api.domain.Vendor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author fahad
 * @since 17-05-2016.
 */

@Entity(name = "PARTNER_VENDOR")
@Table(name = "PARTNER_VENDOR")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_VENDOR")
public class DefaultVendor extends DefaultPartnerRole implements Vendor,Serializable{

    private static final String simpleName = "Vendor";

    @Column(name = "VENDOR__ID")
    @Basic(fetch = FetchType.EAGER)
    private String vendorID;

    @Column(name = "FIRST_NAME")
    @Basic(fetch = FetchType.EAGER)
    protected String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String lastName;

    @Column(name = "MIDDLE_NAME")
    @Basic(fetch = FetchType.EAGER)
    protected String middleName;

    @Column(name = "ADDRESS")
    @Basic(fetch = FetchType.EAGER)
    protected Address address;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "VENDOR_PHONE", joinColumns = @JoinColumn(name = "PARTNER__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultPhone> phone = new HashSet<DefaultPhone>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "VENDOR_EMAIL", joinColumns = @JoinColumn(name = "PARTNER__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultEmail> email = new HashSet<DefaultEmail>(0);

    @Column(name = "ORGANIZATION_UNIT__ID")
    @Basic(fetch = FetchType.EAGER)
    private String organizationUnit;

    @Column(name = "IS_USER", columnDefinition = "boolean default false")
    @Basic(fetch = FetchType.EAGER)
    private boolean user;//Is Vendor a user

    @Column(name = "USERNAME", unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String userName;


    @Override
    public String simpleName() {
        return this.simpleName;
    }

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
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
