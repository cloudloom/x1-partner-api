package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.partner.api.domain.PartnerRole;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 05-Aug-14.
 */
@Entity
@Table(name = "PARTNER_ROLE")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "PARTNER_ROLE", discriminatorType = DiscriminatorType.STRING)
public abstract class DefaultPartnerRole extends BaseEntity implements PartnerRole, Serializable {

    @Column(name = "NAME")
    @Basic(fetch = FetchType.EAGER)
    private String name;

    @ElementCollection
    @JoinTable(name = "PARTNER_ADDRESS", joinColumns = @JoinColumn(name = "PARTNER__ID"))
    private Set<Address> addresses = new HashSet<Address>(0);

    public abstract String simpleName();

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Set<Address> getAddresses() {
        return addresses;
    }

    @Override
    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }
}

