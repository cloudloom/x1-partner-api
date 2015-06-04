package com.tracebucket.x1.partner.api.repository.jpa;

import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.tron.ddd.jpa.BaseAggregateRepository;
import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * Created by sadath on 26-May-2015.
 */
public interface DefaultPartnerRepository extends BaseAggregateRepository<DefaultPartner, AggregateId> {
    @Query(value = "select p from com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultPartner p where p.owner.organizationUID = :organizationUid")
    public List<DefaultPartner> findPartnersByOrganization(@Param("organizationUid") String organizationUid);
}