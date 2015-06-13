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
    @Column(name = "ORGANIZATION__ID")
    @Basic(fetch = FetchType.EAGER)
    private String organizationUID;

    public DefaultOwner() {

    }

    public DefaultOwner(String organizationUID) {
        this.organizationUID = organizationUID;
    }

    public String getOrganizationUID() {
        return organizationUID;
    }

    public void setOrganizationUID(String organizationUID) {
        this.organizationUID = organizationUID;
    }
}
