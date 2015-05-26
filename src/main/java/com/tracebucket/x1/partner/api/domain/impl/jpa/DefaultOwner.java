package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.partner.api.domain.Owner;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;

/**
 * Created by ffl on 22-01-2015.
 * This is Organization in the partner bounded context.
 */
@Embeddable
public class DefaultOwner implements Owner {

    /*@Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "aggregateId", column = @Column(name = "clientId", nullable = false))})
    private AggregateId aggregateId;*/

    @Column(name = "NAME", /*nullable = false, */unique = true)
    @Basic(fetch = FetchType.EAGER)
    private String name;

    public DefaultOwner() {
    }

    public DefaultOwner(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
