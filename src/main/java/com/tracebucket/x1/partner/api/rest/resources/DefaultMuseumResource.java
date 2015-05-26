package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.partner.api.domain.Museum;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by sadath on 26-May-2015.
 */
public class DefaultMuseumResource extends BaseResource{
    private static final String simpleName = "Museums";

    public static String getSimpleName() {
        return simpleName;
    }
}
