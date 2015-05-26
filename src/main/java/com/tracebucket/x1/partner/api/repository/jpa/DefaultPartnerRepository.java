package com.tracebucket.x1.partner.api.repository.jpa;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.jpa.BaseAggregateRepository;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;

/**
 * Created by sadath on 26-May-2015.
 */
public interface DefaultPartnerRepository extends BaseAggregateRepository<DefaultPartner, AggregateId> {
}