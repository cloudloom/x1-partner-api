package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;
import com.tracebucket.x1.partner.api.rest.resources.DefaultOwnerResource;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerResourceBuilder {

    private String name;

    private DefaultOwnerResourceBuilder(){ }

    public static DefaultOwnerResourceBuilder anOwnerBuilder(){
        return new DefaultOwnerResourceBuilder();
    }

    public DefaultOwnerResourceBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultOwnerResource build(){
        DefaultOwnerResource owner = new DefaultOwnerResource();
        owner.setName(name);
        return owner;
    }
}
