package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.partner.api.domain.Museum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by vishwa on 10-12-2014.
 */
@Entity(name = "PARTNER_MUSEUMS")
@Table(name = "PARTNER_MUSEUMS")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_MUSEUMS")
public class DefaultMuseum extends DefaultPartnerRole implements Museum, Serializable {

    private static final String simpleName = "Museums";

    @Override
    public String simpleName() {
        return simpleName;
    }
}
