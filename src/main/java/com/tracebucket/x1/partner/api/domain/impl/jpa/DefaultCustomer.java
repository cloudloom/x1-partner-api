package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.dictionary.api.domain.Gender;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.domain.Customer;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by sadath on 05-Aug-14.
 */
@Entity(name = "PARTNER_CUSTOMER")
@Table(name = "PARTNER_CUSTOMER")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_CUSTOMER")
public class DefaultCustomer extends DefaultPartnerRole implements Customer, Serializable {
    private static final String simpleName = "Customer";

    @Column(name = "INITIAL")
    @Basic(fetch = FetchType.EAGER)
    private String initial;

    @Column(name = "SALUTATION")
    @Basic(fetch = FetchType.EAGER)
    private Salutation salutation;

    @Column(name = "FIRST_NAME")
    @Basic(fetch = FetchType.EAGER)
    private String firstName;

    @Column(name = "MIDDLE_NAME")
    @Basic(fetch = FetchType.EAGER)
    private String middleName;

    @Column(name = "LAST_NAME")
    @Basic(fetch = FetchType.EAGER)
    private String lastName;

    @Column(name = "DATE_OF_BIRTH")
    @Basic(fetch = FetchType.EAGER)
    private Date birthDay;

    @Column(name = "GENDER")
    @Basic(fetch = FetchType.EAGER)
    private Gender gender;

    @Column(name = "EMAIL")
    @Basic(fetch = FetchType.EAGER)
    private String email;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getMiddleName() {
        return middleName;
    }

    @Override
    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Date getBirthDay() {
        return birthDay;
    }

    @Override
    public void setBirthDay(Date birthDay) {
        this.birthDay = birthDay;
    }

    @Override
    public Gender getGender() {
        return gender;
    }

    @Override
    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String simpleName() {
        return simpleName;
    }

    public String getInitial() {
        return initial;
    }

    public void setInitial(String initial) {
        this.initial = initial;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }
}
