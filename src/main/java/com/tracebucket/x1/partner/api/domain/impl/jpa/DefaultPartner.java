package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.annotation.DomainMethod;
import com.tracebucket.tron.ddd.domain.BaseAggregateRoot;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.domain.Partner;
import org.dozer.Mapper;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by sadath on 05-Aug-14.
 */
@Entity
@Table(name = "PARTNER")
public class DefaultPartner extends BaseAggregateRoot implements Partner{
    @Column(name = "TITLE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String title;

    @Column(name = "IMAGE")
    @Basic(fetch = FetchType.EAGER)
    protected String image;

    @Column(name = "WEBSITE")
    @Basic(fetch = FetchType.EAGER)
    protected String website;

    @Column(name = "PARTNER_CATEGORY", nullable = false, columnDefinition = "ENUM('INDIVIDUAL', 'GROUP', 'ORGANIZATION') default 'ORGANIZATION'")
    @Enumerated(EnumType.STRING)
    @Basic(fetch = FetchType.EAGER)
    protected PartnerCategory partnerCategory;

    @Embedded
    private DefaultOwner owner;


    @OneToMany(cascade = CascadeType.ALL, /*orphanRemoval = true, */fetch = FetchType.EAGER)
    @JoinColumn(name = "PARTNER__ID")
    protected Set<DefaultPartnerRole> partnerRoles = new HashSet<DefaultPartnerRole>(0);

    public DefaultPartner() {
    }

    public DefaultPartner(String title, String website){

        this.title = title;
        this.website = website;
    }

    public DefaultPartner(String title, String website, String image, PartnerCategory partnerCategory){

        this.title = title;
        this.website = website;
        this.image = image;
        this.partnerCategory = partnerCategory;
    }

    @Override
    @DomainMethod(event = "PartnerCategorySet")
    public void setPartnerCategory(PartnerCategory partnerCategory){

        this.partnerCategory = partnerCategory;
    }

    @Override
    @DomainMethod(event = "PartnerMoved")
    public void movePartnerToCategory(PartnerCategory newPartnerCategory){
        this.partnerCategory = newPartnerCategory;
    }

    @Override
    @DomainMethod(event = "PartnerRoleAdded")
    public void addPartnerRole(DefaultPartnerRole newPartnerRole){
        if(newPartnerRole != null && newPartnerRole.getEntityId() == null) {
            this.partnerRoles.add(newPartnerRole);
            return;
        }
        boolean status = this.partnerRoles.stream().noneMatch(role -> role.getEntityId().getId().equals(newPartnerRole.getEntityId().getId()));
        if(status) {
            this.partnerRoles.add(newPartnerRole);
        }
    }

    @Override
    @DomainMethod(event = "PartnerRoleUpdated")
    public void updatePartnerRole(DefaultPartnerRole partnerRole, Mapper mapper){
        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().equals(partnerRole.getEntityId()))
                .findFirst().get();
        if(roleFound != null){
            mapper.map(partnerRole, roleFound);
        }
    }

    @Override
    public Boolean hasPartnerRole(DefaultPartnerRole partnerRole){
        Long found = partnerRoles.parallelStream()
            .filter(t -> t.getEntityId() == partnerRole.getEntityId())
            .count();
        return (found != null && found > 0) ? true : false;
    }

    @Override
    @DomainMethod(event = "AddressAddedToRole")
    public void addAddressToRole(DefaultPartnerRole partnerRole, Address address){
        if(address != null) {
            DefaultAddress defaultAddress = (DefaultAddress) address;
            defaultAddress.setDefaultAddress(true);
            partnerRole.getAddresses().add((DefaultAddress)address);
        }
    }

    public DefaultOwner getOwner() {
        return owner;
    }

    @Override
    @DomainMethod(event = "RoleAddressMoved")
    public void moveRoleAddressTo(DefaultPartnerRole partnerRole, Address newAddress){

        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().equals(partnerRole.getEntityId()))
                .findFirst().get();

        if(roleFound != null){
            Set<DefaultAddress> addresses = roleFound.getAddresses();
            if(addresses != null) {
                addresses.forEach(a -> {
                    a.setDefaultAddress(false);
                });
            }
            DefaultAddress defaultAddress = (DefaultAddress) newAddress;
            defaultAddress.setDefaultAddress(true);
            addresses.add(defaultAddress);
        }
    }

    @Override
    @DomainMethod(event = "OwnerChanged")
    public void changeOwner(DefaultOwner newOwner){
        this.owner = newOwner;
    }

    @Override
    public Set<DefaultPartnerRole> getAllAssignedRoles(){

        return partnerRoles;  //iteration

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setOwner(DefaultOwner owner) {
        this.owner = owner;
    }

    public void setPartnerRoles(Set<DefaultPartnerRole> partnerRoles) {
        this.partnerRoles = partnerRoles;
    }

    @Override
    public PartnerCategory getPartnerCategory(){return this.partnerCategory;}

    @Override
    public Set<DefaultPartnerRole> getPartnerRoles() {
        return partnerRoles;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getImage() {
        return image;
    }

    @Override
    public String getWebsite() {
        return website;
    }
}
