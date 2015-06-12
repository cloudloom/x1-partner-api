package com.tracebucket.x1.partner.api.rest.assembler.entity;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.EntityAssembler;
import com.tracebucket.tron.ddd.domain.AggregateId;
import com.tracebucket.x1.partner.api.domain.impl.jpa.*;
import com.tracebucket.x1.partner.api.rest.resources.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sadath on 01-Jun-2015.
 */
@Component
public class PartnerEntityAssembler extends EntityAssembler<DefaultPartner, DefaultPartnerResource> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public DefaultPartner toEntity(DefaultPartnerResource resource, Class<DefaultPartner> entityClass) {
        DefaultPartner partner = null;
        Set<DefaultPartnerRole> partnerRoles = null;
        if(resource != null) {
            partner = new DefaultPartner();
            partnerRoles = new HashSet<DefaultPartnerRole>();
            if(resource.getUid() != null) {
                partner.setAggregateId(new AggregateId(resource.getUid()));
            }
            partner.setWebsite(resource.getWebsite());
            partner.setImage(resource.getImage());
            partner.setPartnerCategory(resource.getPartnerCategory());
            if(resource.getOwner() != null) {
                partner.setOwner(assemblerResolver.resolveEntityAssembler(DefaultOwner.class, DefaultOwnerResource.class).toEntity(resource.getOwner(), DefaultOwner.class));
            }
            partner.setTitle(resource.getTitle());
            if(resource.getAffiliate() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultAffiliate.class, DefaultAffiliateResource.class).toEntity(resource.getAffiliate(), DefaultAffiliate.class));
            }
            if(resource.getCustomer() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultCustomer.class, DefaultCustomerResource.class).toEntity(resource.getCustomer(), DefaultCustomer.class));
            }
            if(resource.getEntertainmentCompany() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultEntertainmentCompany.class, DefaultEntertainmentCompanyResource.class).toEntity(resource.getEntertainmentCompany(), DefaultEntertainmentCompany.class));
            }
            if(resource.getMuseum() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultMuseum.class, DefaultMuseumResource.class).toEntity(resource.getMuseum(), DefaultMuseum.class));
            }
            if(resource.getTourCompany() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultTourCompany.class, DefaultTourCompanyResource.class).toEntity(resource.getTourCompany(), DefaultTourCompany.class));
            }
            if(resource.getTransportProvider() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultTransportProvider.class, DefaultTransportProviderResource.class).toEntity(resource.getTransportProvider(), DefaultTransportProvider.class));
            }
            if(resource.getEmployee() != null) {
                partnerRoles.add(assemblerResolver.resolveEntityAssembler(DefaultEmployee.class, DefaultEmployeeResource.class).toEntity(resource.getEmployee(), DefaultEmployee.class));
            }
            if(partnerRoles.size() > 0) {
                partner.setPartnerRoles(partnerRoles);
            }
        }
        return partner;
    }

    @Override
    public Set<DefaultPartner> toEntities(Collection<DefaultPartnerResource> resources, Class<DefaultPartner> entityClass) {
        Set<DefaultPartner> partners = new HashSet<DefaultPartner>();
        if(resources != null) {
            Iterator<DefaultPartnerResource> iterator = resources.iterator();
            if(iterator.hasNext()) {
                DefaultPartnerResource partnerResource = iterator.next();
                partners.add(toEntity(partnerResource, DefaultPartner.class));
            }
        }
        return partners;
    }
}