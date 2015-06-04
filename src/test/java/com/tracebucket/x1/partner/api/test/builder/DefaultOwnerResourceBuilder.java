package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerResourceBuilder {

    private String organizationUID;

    private DefaultOwnerResourceBuilder(){ }

    public static DefaultOwnerResourceBuilder anOwnerBuilder(){
        return new DefaultOwnerResourceBuilder();
    }

    public DefaultOwnerResourceBuilder withOrganizationUID(String organizationUID){
        this.organizationUID = organizationUID;
        return this;
    }

    public DefaultOwnerResource build(){
        DefaultOwnerResource owner = new DefaultOwnerResource();
        owner.setOrganizationUID(organizationUID);
        return owner;
    }
}
