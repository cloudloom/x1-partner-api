package com.tracebucket.x1.partner.api.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * Created by sadath on 29-Jun-2015.
 */
@JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultPartnerPositionAndOrganizationUnitResource {
    private String organizationUnitUid;
    private String positionUid;
    private String partnerUid;
    private String roleUid;

    public String getOrganizationUnitUid() {
        return organizationUnitUid;
    }

    public void setOrganizationUnitUid(String organizationUnitUid) {
        this.organizationUnitUid = organizationUnitUid;
    }

    public String getPositionUid() {
        return positionUid;
    }

    public void setPositionUid(String positionUid) {
        this.positionUid = positionUid;
    }

    public String getPartnerUid() {
        return partnerUid;
    }

    public void setPartnerUid(String partnerUid) {
        this.partnerUid = partnerUid;
    }

    public String getRoleUid() {
        return roleUid;
    }

    public void setRoleUid(String roleUid) {
        this.roleUid = roleUid;
    }
}