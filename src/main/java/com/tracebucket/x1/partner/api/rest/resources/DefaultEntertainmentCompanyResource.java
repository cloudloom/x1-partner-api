package com.tracebucket.x1.partner.api.rest.resources;

import org.hibernate.validator.constraints.URL;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sadath on 26-May-2015.
 */
public class DefaultEntertainmentCompanyResource extends DefaultPartnerRoleResource{
    private static final String simpleName = "Entertainment Company";

    @NotNull
    @Size(min = 1, max = 250)
    private String name;

    @NotNull
    @Size(min = 1, max = 250)
    @URL
    private String website;

    @NotNull
    @Size(min = 1, max = 250)
    @Pattern(regexp = "([^\\s]+(\\.(?i)(jpg|png|gif|bmp))$)")
    private String logo;

    @Valid
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