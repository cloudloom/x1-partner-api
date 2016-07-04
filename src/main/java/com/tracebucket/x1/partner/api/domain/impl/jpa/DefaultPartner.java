package com.tracebucket.x1.partner.api.domain.impl.jpa;

import com.tracebucket.tron.ddd.annotation.DomainMethod;
import com.tracebucket.tron.ddd.domain.BaseAggregateRoot;
import com.tracebucket.tron.ddd.domain.EntityId;
import com.tracebucket.x1.dictionary.api.domain.Address;
import com.tracebucket.x1.dictionary.api.domain.jpa.impl.DefaultAddress;
import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.dictionary.enums.converter.PartnerCategoryConverter;
import com.tracebucket.x1.partner.api.domain.Partner;
import org.dozer.Mapper;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 05-Aug-14.
 * JPA Entity For Partner
 */
@Entity
@Table(name = "PARTNER")
public class DefaultPartner extends BaseAggregateRoot implements Partner {
    @Column(name = "TITLE", nullable = false)
    @Basic(fetch = FetchType.EAGER)
    protected String title;

    @Column(name = "IMAGE")
    @Basic(fetch = FetchType.EAGER)
    protected String image;

    @Column(name = "WEBSITE")
    @Basic(fetch = FetchType.EAGER)
    protected String website;

    @Column(name = "PARTNER_CATEGORY")
    @Convert(converter = PartnerCategoryConverter.class)
    @Basic(fetch = FetchType.EAGER)
    protected PartnerCategory partnerCategory;

    @Embedded
    private DefaultOwner owner;

    @OneToMany(cascade = CascadeType.ALL, /*orphanRemoval = true, */fetch = FetchType.EAGER)
    @JoinColumn(name = "PARTNER__ID")
    @Fetch(value = FetchMode.JOIN)
    protected Set<DefaultPartnerRole> partnerRoles = new HashSet<DefaultPartnerRole>(0);

    /**
     * Default Constructor
     */
    public DefaultPartner() {
    }

    /**
     * Parameterized Constructor
     * @param title
     * @param website
     */
    public DefaultPartner(String title, String website){

        this.title = title;
        this.website = website;
    }

    /**
     * Parameterized Constructor
     * @param title
     * @param website
     * @param image
     * @param partnerCategory
     */
    public DefaultPartner(String title, String website, String image, PartnerCategory partnerCategory){

        this.title = title;
        this.website = website;
        this.image = image;
        this.partnerCategory = partnerCategory;
    }

    /**
     * Set Partner Category
     * @param partnerCategory
     */
    @Override
    @DomainMethod(event = "PartnerCategorySet")
    public void setPartnerCategory(PartnerCategory partnerCategory){
        this.partnerCategory = partnerCategory;
    }

    /**
     * Update / Move Partner Category
     * @param newPartnerCategory
     */
    @Override
    @DomainMethod(event = "PartnerMoved")
    public void movePartnerToCategory(PartnerCategory newPartnerCategory){
        this.partnerCategory = newPartnerCategory;
    }

    /**
     * Add Partner Role
     * @param newPartnerRole
     */
    @Override
    @DomainMethod(event = "PartnerRoleAdded")
    public void addPartnerRole(DefaultPartnerRole newPartnerRole){
        //incoming partnerRole is not null and no uid is specified
        if(newPartnerRole != null && newPartnerRole.getEntityId() == null) {
            //add incoming partnerRole to 'this' partnerRoles
            this.partnerRoles.add(newPartnerRole);
            //add and return
            return;
        }
        //if uid is specified, stream partnerRoles and check if there is no match for incoming partnerRole, if there is no match add it to 'this' partnerRoles
        boolean status = this.partnerRoles.stream().noneMatch(role -> role.getEntityId().getId().equals(newPartnerRole.getEntityId().getId()));
        if(status) {
            this.partnerRoles.add(newPartnerRole);
        }
    }

    /**
     * Update Partner Role
     * @param partnerRole
     * @param mapper
     */
    @Override
    @DomainMethod(event = "PartnerRoleUpdated")
    public void updatePartnerRole(DefaultPartnerRole partnerRole, Mapper mapper){
        //stream 'this' partnerRoles to find partnerRole that is to be updated
        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().equals(partnerRole.getEntityId()))
                .findFirst().orElse(null);
        //if the role to be updated is found
        if(roleFound != null){
            //clear its addresses
            roleFound.getAddresses().clear();
            boolean isUser = false;
            //if roleFound is an Employee
            if(roleFound instanceof DefaultEmployee) {
                //typecast role to employee and track if employee is a user
                isUser = ((DefaultEmployee) roleFound).isUser();
                DefaultEmployee employee = (DefaultEmployee) roleFound;
                //clear phones
                employee.getPhone().clear();
                //clear emails
                employee.getEmail().clear();
                //clear notifyTo
                employee.getNotifyTo().clear();
            }
            //map new values to found role
            mapper.map(partnerRole, roleFound);
            //set if employee is user, which was tracked above
            ((DefaultEmployee) roleFound).setAsUser(isUser);
        }
    }

    /**
     * Check If Partner Has A Role
     * @param partnerRole
     * @return Boolean
     */
    @Override
    public Boolean hasPartnerRole(DefaultPartnerRole partnerRole){
        //stream 'this' partnerRoles and check if role exists
        Long found = partnerRoles.parallelStream()
            .filter(t -> t.getEntityId() == partnerRole.getEntityId())
            .count();
        return (found != null && found > 0) ? true : false;
    }

    /**
     * Add Address To Role
     * @param partnerRole
     * @param address
     */
    @Override
    @DomainMethod(event = "AddressAddedToRole")
    public void addAddressToRole(DefaultPartnerRole partnerRole, Address address){
        //if incoming address is not null
        if(address != null) {
            //typecast address to defaultAddress
            DefaultAddress defaultAddress = (DefaultAddress) address;
            //set it as default address
            defaultAddress.setDefaultAddress(true);
            //add address to partnerRole
            partnerRole.getAddresses().add((DefaultAddress)address);
        }
    }

    /**
     * Get Owner
     * i'e get the organization to which the partner belongs to
     * @return
     */
    public DefaultOwner getOwner() {
        return owner;
    }

    /**
     * Update / Move Address Of PartnerRole
     * @param partnerRole
     * @param newAddress
     */
    @Override
    @DomainMethod(event = "RoleAddressMoved")
    public void moveRoleAddressTo(DefaultPartnerRole partnerRole, Address newAddress){
        //stream partnerRoles and find partnerRole whose address has to be moved/updated
        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().equals(partnerRole.getEntityId()))
                .findFirst().get();
        //if roleFound
        if(roleFound != null){
            //stream each of its address and set all default address status to false
            Set<DefaultAddress> addresses = roleFound.getAddresses();
            if(addresses != null) {
                addresses.forEach(a -> {
                    a.setDefaultAddress(false);
                });
            }
            DefaultAddress defaultAddress = (DefaultAddress) newAddress;
            //set incoming address default status as true
            defaultAddress.setDefaultAddress(true);
            //add address to addresses
            addresses.add(defaultAddress);
        }
    }

    /**
     * Change Owner Of Partner
     * i'e change the organization to which the partner belongs to
     * @param newOwner
     */
    @Override
    @DomainMethod(event = "OwnerChanged")
    public void changeOwner(DefaultOwner newOwner){
        this.owner = newOwner;
    }

    /**
     * Add Position To Partner
     * @param partnerRoleUid
     * @param positionUid
     */
    @Override
    @DomainMethod(event = "AddPosition")
    public void addPosition(EntityId partnerRoleUid, EntityId positionUid) {
        //stream 'this' partnerRoles and find the role to which position is to be added
        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().getId().equals(partnerRoleUid.getId()))
                .findFirst()
                .orElse(null);
        //if roleFound add positionUid to it
        if(roleFound != null && roleFound instanceof DefaultEmployee) {
            ((DefaultEmployee)roleFound).setPosition(positionUid.getId());
        }
    }

    /**
     * Get All Roles Of A Partner
     * @return
     */
    @Override
    public Set<DefaultPartnerRole> getAllAssignedRoles(){
        return partnerRoles;  //iteration
    }

    /**
     * Add Position And Organization Unit
     * @param partnerRoleUid
     * @param positionUid
     * @param organizationUnitUid
     */
    @Override
    @DomainMethod(event = "AddPositionAndOrganization")
    public void addPositionAndOrganization(EntityId partnerRoleUid, EntityId positionUid, EntityId organizationUnitUid) {
        //stream 'this' partnerRoles and find role to which position and organization is to be added
        DefaultPartnerRole roleFound = partnerRoles.stream()
                .filter(t -> t.getEntityId().getId().equals(partnerRoleUid.getId()))
                .findFirst()
                .orElse(null);
        //if roleFound, set positionUid and organizationUnitUid
        if(roleFound != null && roleFound instanceof DefaultEmployee) {
            ((DefaultEmployee)roleFound).setPosition(positionUid.getId());
            ((DefaultEmployee)roleFound).setOrganizationUnit(organizationUnitUid.getId());
        }
    }

    /**
     * Remove Position And Organization Unit
     */
    @Override
    @DomainMethod(event = "RemovePositionAndOrganization")
    public void removePositionAndOrganization() {
        //stream 'this' partnerRoles and find role from which position and organization is to be removed
        DefaultPartnerRole roleFound = partnerRoles.stream()
                .filter(t -> t instanceof DefaultEmployee)
                .findFirst()
                .orElse(null);
        //if roleFound, remove positionUid and organizationUid
        if(roleFound != null && roleFound instanceof DefaultEmployee) {
            ((DefaultEmployee)roleFound).setPosition(null);
            ((DefaultEmployee)roleFound).setOrganizationUnit(null);
        }
    }

    /**
     * Remove Department, Position And Organization
     */
    @Override
    @DomainMethod(event = "RemovePositionAndOrganization")
    public void removeDepartmentAndPositionAndOrganization() {
        //if 'this' has a role of employee, fetch it
        DefaultPartnerRole roleFound = partnerRoles.stream()
                .filter(t -> t instanceof DefaultEmployee)
                .findFirst()
                .orElse(null);
        //set position, organizationUnit and department as null
        if(roleFound != null && roleFound instanceof DefaultEmployee) {
            ((DefaultEmployee)roleFound).setPosition(null);
            ((DefaultEmployee)roleFound).setOrganizationUnit(null);
            ((DefaultEmployee)roleFound).setDepartment(null);
        }
    }

    /**
     * Set Title
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Set Image
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Set Website
     * @param website
     */
    public void setWebsite(String website) {
        this.website = website;
    }

    /**
     * Set Owner(Organization)
     * @param owner
     */
    public void setOwner(DefaultOwner owner) {
        this.owner = owner;
    }

    /**
     * Set PartnerRoles
     * @param partnerRoles
     */
    public void setPartnerRoles(Set<DefaultPartnerRole> partnerRoles) {
        this.partnerRoles = partnerRoles;
    }

    /**
     * Get PartnerCategory
     * @return
     */
    @Override
    public PartnerCategory getPartnerCategory(){return this.partnerCategory;}

    /**
     * Get PartnerRoles
     * @return
     */
    @Override
    public Set<DefaultPartnerRole> getPartnerRoles() {
        return partnerRoles;
    }

    /**
     * Get Title
     * @return
     */
    @Override
    public String getTitle() {
        return title;
    }

    /**
     * Get Image
     * @return
     */
    @Override
    public String getImage() {
        return image;
    }

    /**
     * Get Website
     * @return
     */
    @Override
    public String getWebsite() {
        return website;
    }

    /**
     * Add Department To Partner Role
     * @param partnerRoleUid
     * @param departmentUid
     */
    @Override
    @DomainMethod(event = "AddDepartment")
    public void addDepartment(EntityId partnerRoleUid, EntityId departmentUid) {
        //stream 'this' partnerRoles and find the role to which department has to be added
        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().getId().equals(partnerRoleUid.getId()))
                .findFirst()
                .orElse(null);
        //if roleFound, set its department
        if(roleFound != null && roleFound instanceof DefaultEmployee) {
            ((DefaultEmployee)roleFound).setDepartment(departmentUid.getId());
        }
    }

    /**
     * Add Department, Position And OrganizationUnit To Partner Role
     * @param roleUid
     * @param organizationUnitUid
     * @param positionUid
     * @param departmentUid
     */
    @Override
    @DomainMethod(event = "AddOrganizationUnitPositionAndDepartment")
    public void addDepartmentPositionAndOrganizationUnit(EntityId roleUid, String organizationUnitUid, String positionUid, String departmentUid){
        //stream 'this' partnerRoles and find the role to which department, position and organizationUnit has to be added
        DefaultPartnerRole roleFound = partnerRoles.parallelStream()
                .filter(t -> t.getEntityId().getId().equals(roleUid.getId()))
                .findFirst()
                .orElse(null);
        //if roleFound, set department, position and organizationUnit
        if(roleFound != null && roleFound instanceof DefaultEmployee) {
            ((DefaultEmployee)roleFound).setDepartment(departmentUid);
            ((DefaultEmployee)roleFound).setPosition(positionUid);
            ((DefaultEmployee)roleFound).setOrganizationUnit(organizationUnitUid);
        }
    }

    /**
     * Add UserName
     * @param userName
     */
    @Override
    @DomainMethod(event = "AddUserName")
    public void addUserName(String userName) {
        //get all assigned roles
        Set<DefaultPartnerRole> roles = this.getAllAssignedRoles();
        //if assigned roles found
        if(roles != null && roles.size() > 0) {
            //stream and find role Employee
            roles.stream().forEach(role -> {
                //role is an Employee
                if(role instanceof DefaultEmployee) {
                    DefaultEmployee employee = (DefaultEmployee) role;
                    //set userName to employee
                    employee.setUserName(userName);
                    employee.setAsUser(true);
                }
            });
        }
    }
}