package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.x1.partner.api.domain.TransportProvider;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by vishwa on 10-12-2014.
 */
@Entity(name = "PARTNER_TRANSPORT_PROVIDER")
@Table(name = "PARTNER_TRANSPORT_PROVIDER")
@PrimaryKeyJoinColumn(name="PARTNER_ROLE__ID")
@DiscriminatorValue(value = "PARTNER_TRANSPORT_PROVIDER")
public class DefaultTransportProvider extends DefaultPartnerRole implements TransportProvider, Serializable {

    private static final String simpleName = "Transport Provider";

    @Override
    public String simpleName() {
        return simpleName;
    }
}
