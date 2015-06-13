package com.tracebucket.x1.partner.api.rest.assembler.resource;

import com.tracebucket.tron.assembler.AssemblerResolver;
import com.tracebucket.tron.assembler.ResourceAssembler;
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
public class PartnerResourceAssembler extends ResourceAssembler<DefaultPartnerResource, DefaultPartner> {

    @Autowired
    private AssemblerResolver assemblerResolver;

    @Override
    public DefaultPartnerResource toResource(DefaultPartner entity, Class<DefaultPartnerResource> resourceClass) {
        try {
            final DefaultPartnerResource partner = resourceClass.newInstance();
            if (entity != null) {
                partner.setUid(entity.getAggregateId().getAggregateId());
                partner.setPassive(entity.isPassive());
                partner.setTitle(entity.getTitle());
                partner.setImage(entity.getImage());
                partner.setWebsite(entity.getWebsite());
                partner.setPartnerCategory(entity.getPartnerCategory());
                if(entity.getOwner() != null) {
                    partner.setOwner(assemblerResolver.resolveResourceAssembler(DefaultOwnerResource.class, DefaultOwner.class).toResource(entity.getOwner(), DefaultOwnerResource.class));
                }
                Set<DefaultPartnerRole> partnerRoles = entity.getAllAssignedRoles();
                if(partnerRoles != null && partnerRoles.size() > 0) {
                    partnerRoles.parallelStream().forEach(role -> {
                        if(role instanceof DefaultAffiliate) {
                            partner.setAffiliate(assemblerResolver.resolveResourceAssembler(DefaultAffiliateResource.class, DefaultAffiliate.class).toResource((DefaultAffiliate)role, DefaultAffiliateResource.class));
                        } else if(role instanceof DefaultCustomer) {
                            partner.setCustomer(assemblerResolver.resolveResourceAssembler(DefaultCustomerResource.class, DefaultCustomer.class).toResource((DefaultCustomer) role, DefaultCustomerResource.class));
                        } else if(role instanceof DefaultEntertainmentCompany) {
                            partner.setEntertainmentCompany(assemblerResolver.resolveResourceAssembler(DefaultEntertainmentCompanyResource.class, DefaultEntertainmentCompany.class).toResource((DefaultEntertainmentCompany) role, DefaultEntertainmentCompanyResource.class));
                        } else if(role instanceof DefaultMuseum) {
                            partner.setMuseum(assemblerResolver.resolveResourceAssembler(DefaultMuseumResource.class, DefaultMuseum.class).toResource((DefaultMuseum) role, DefaultMuseumResource.class));
                        } else if(role instanceof DefaultTourCompany) {
                            partner.setTourCompany(assemblerResolver.resolveResourceAssembler(DefaultTourCompanyResource.class, DefaultTourCompany.class).toResource((DefaultTourCompany) role, DefaultTourCompanyResource.class));
                        } else if(role instanceof DefaultTransportProvider) {
                            partner.setTransportProvider(assemblerResolver.resolveResourceAssembler(DefaultTransportProviderResource.class, DefaultTransportProvider.class).toResource((DefaultTransportProvider) role, DefaultTransportProviderResource.class));
                        } else if(role instanceof DefaultEmployee) {
                            partner.setEmployee(assemblerResolver.resolveResourceAssembler(DefaultEmployeeResource.class, DefaultEmployee.class).toResource((DefaultEmployee) role, DefaultEmployeeResource.class));
                        }
                    });
                }
            }
            return partner;
        } catch (InstantiationException ie) {

        } catch (IllegalAccessException iae) {

        }
        return null;
    }

    @Override
    public Set<DefaultPartnerResource> toResources(Collection<DefaultPartner> entities, Class<DefaultPartnerResource> resourceClass) {
        Set<DefaultPartnerResource> partners = new HashSet<DefaultPartnerResource>();
        if(entities != null && entities.size() > 0) {
            Iterator<DefaultPartner> iterator = entities.iterator();
            while(iterator.hasNext()) {
                DefaultPartner partner = iterator.next();
                partners.add(toResource(partner, DefaultPartnerResource.class));
            }
        }
        return partners;
    }
}