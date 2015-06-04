package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerBuilder {

    private String organizationUID;

    private DefaultOwnerBuilder(){ }

    public static DefaultOwnerBuilder anOwnerBuilder(){
        return new DefaultOwnerBuilder();
    }

    public DefaultOwnerBuilder withOrganizationUID(String organizationUID){
        this.organizationUID = organizationUID;
        return this;
    }

    public DefaultOwner build(){
        DefaultOwner owner = new DefaultOwner();
        owner.setOrganizationUID(organizationUID);
        return owner;
    }
}
