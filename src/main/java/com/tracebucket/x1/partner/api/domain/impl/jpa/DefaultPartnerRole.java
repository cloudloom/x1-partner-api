package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.domain.BaseEntity;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.domain.PartnerRole;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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

    @ElementCollection(fetch = FetchType.EAGER)
    @JoinTable(name = "PARTNER_ADDRESS", joinColumns = @JoinColumn(name = "PARTNER__ID"))
    @Fetch(value = FetchMode.JOIN)
    private Set<DefaultAddress> addresses = new HashSet<DefaultAddress>(0);

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
    public Set<DefaultAddress> getAddresses() {
        return addresses;
    }

    @Override
    public void setAddresses(Set<DefaultAddress> addresses) {
        if(addresses != null) {
            this.addresses.clear();
            this.addresses.addAll(addresses);
        }
    }
}