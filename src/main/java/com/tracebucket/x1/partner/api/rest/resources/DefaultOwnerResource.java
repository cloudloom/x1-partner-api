package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;

/**
 * Created by sadath on 26-May-2015.
 */
public class DefaultOwnerResource extends BaseResource {
    private String organizationUID;

    public String getOrganizationUID() {
        return organizationUID;
    }

    public void setOrganizationUID(String organizationUID) {
        this.organizationUID = organizationUID;
    }
}
