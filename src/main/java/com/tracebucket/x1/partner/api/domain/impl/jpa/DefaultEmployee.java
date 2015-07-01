package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultValidity;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.domain.Employee;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Vishwajit on 10-06-2015.
 */
@Entity(name = "PARTNER_EMPLOYEE")
@Table(name = "PARTNER_EMPLOYEE")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_EMPLOYEE")
public class DefaultEmployee extends DefaultPartnerRole implements Employee, Serializable {

    private static final String simpleName = "Employee";

    @Column(name = "EMPLOYEE__ID")
    @Basic(fetch = FetchType.EAGER)
    private String employeeID;

    @Column(name = "SALUTATION")
    @Basic(fetch = FetchType.EAGER)
    private Salutation salutation;

    @Column(name = "FIRST_NAME")
    @Basic(fetch = FetchType.EAGER)
    protected String firstName;

    @Column(name = "LAST_NAME", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String lastName;

    @Column(name = "MIDDLE_NAME")
    @Basic(fetch = FetchType.EAGER)
    protected String middleName;

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "EMPLOYEE_PHONE", joinColumns = @JoinColumn(name = "PARTNER__ID"))
    private Set<DefaultPhone> phone = new HashSet<DefaultPhone>(0);

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "EMPLOYEE_EMAIL", joinColumns = @JoinColumn(name = "PARTNER__ID"))
    private Set<DefaultEmail> email = new HashSet<DefaultEmail>(0);

    @Column(name = "POSITION__ID")
    @Basic(fetch = FetchType.EAGER)
    private String position;

    @Column(name = "ORGANIZATION_UNIT__ID")
    @Basic(fetch = FetchType.EAGER)
    private String organizationUnit;

    @Embedded
    private DefaultValidity validity;

    @Column(name = "DATE_OF_BIRTH")
    @Basic(fetch = FetchType.EAGER)
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;

    @Column(name = "IS_USER", columnDefinition = "boolean default false")
    @Basic(fetch = FetchType.EAGER)
    private boolean user;//Is Employee a user

    @Column(name = "SEARCH_TERM")
    @Basic(fetch = FetchType.EAGER)
    private String searchTerm;

    public String getEmployeeID() {
        return employeeID;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setEmployeeID(String employeeID) {       this.employeeID = employeeID;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Set<DefaultPhone> getPhone() {
        return phone;
    }

    public void setPhone(Set<DefaultPhone> phone) {
        if(phone != null) {
            this.phone.clear();
            this.phone.addAll(phone);
        }
    }

    public Set<DefaultEmail> getEmail() {
        return email;
    }

    public void setEmail(Set<DefaultEmail> email) {
        if(email != null) {
            this.email.clear();
            this.email.addAll(email);
        }
    }

    @Override
    public String simpleName() {
        return simpleName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public DefaultValidity getValidity() {
        return validity;
    }

    public void setValidity(DefaultValidity validity) {
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

    public String getOrganizationUnit() {
        return organizationUnit;
    }

    public void setOrganizationUnit(String organizationUnit) {
        this.organizationUnit = organizationUnit;
    }
}
