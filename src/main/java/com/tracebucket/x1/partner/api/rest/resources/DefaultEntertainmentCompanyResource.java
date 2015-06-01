package com.tracebucket.x1.partner.api.rest.resources;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public class DefaultEntertainmentCompanyResource extends DefaultPartnerRoleResource{
    private static final String simpleName = "Entertainment Company";
    private String name;
    private String website;
    private String logo;
    private Set<DefaultPersonResource> contactPersons = new HashSet<DefaultPersonResource>(0);

    public static String getSimpleName() {
        return simpleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public Set<DefaultPersonResource> getContactPersons() {
        return contactPersons;
    }

    public void setContactPersons(Set<DefaultPersonResource> contactPersons) {
        this.contactPersons = contactPersons;
    }
}