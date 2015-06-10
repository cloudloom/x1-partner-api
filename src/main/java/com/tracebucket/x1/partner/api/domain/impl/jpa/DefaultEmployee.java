package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseAggregateRoot;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultEmail;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultPhone;
import com.tracebucket.x1.partner.api.dictionary.Salutation;
import com.tracebucket.x1.partner.api.domain.Employee;
import com.tracebucket.x1.partner.api.domain.Partner;

import javax.persistence.*;

/**
 * Created by Vishwajit on 10-06-2015.
 */

@Entity
@Table(name = "EMPLOYEE")
public class DefaultEmployee extends BaseAggregateRoot implements Employee {

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

    @Embedded
    private DefaultAddress address;

    @Embedded
    private DefaultPhone phone;

    @Embedded
    private DefaultEmail email;

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

    public DefaultAddress getAddress() {
        return address;
    }

    public DefaultPhone getPhone() {
        return phone;
    }

    public DefaultEmail getEmail() {
        return email;
    }
}
