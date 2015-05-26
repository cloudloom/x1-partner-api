package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.partner.api.domain.TourCompany;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by vishwa on 10-12-2014.
 */
@Entity(name = "PARTNER_TOUR_COMPANY")
@Table(name = "PARTNER_TOUR_COMPANY")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_TOURCOMPANY")
public class DefaultTourCompany extends DefaultPartnerRole implements TourCompany, Serializable {

    private static final String simpleName = "Tour Company";

    @Override
    public String simpleName() {
        return simpleName;
    }
}
