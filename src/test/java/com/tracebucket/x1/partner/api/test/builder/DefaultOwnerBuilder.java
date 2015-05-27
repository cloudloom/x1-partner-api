package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.partner.api.domain.impl.jpa.DefaultOwner;

/**
 * Created by vishwa on 23-01-2015.
 */
public class DefaultOwnerBuilder {

    private String name;

    private DefaultOwnerBuilder(){ }

    public static DefaultOwnerBuilder anOwnerBuilder(){
        return new DefaultOwnerBuilder();
    }

    public DefaultOwnerBuilder withName(String name){
        this.name = name;
        return this;
    }

    public DefaultOwner build(){
        DefaultOwner owner = new DefaultOwner();
        owner.setName(name);

        return owner;
    }
}
