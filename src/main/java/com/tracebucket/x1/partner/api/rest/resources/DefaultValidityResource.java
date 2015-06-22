package com.tracebucket.x1.partner.api.rest.resources;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tracebucket.tron.assembler.BaseResource;

import java.util.Date;

/**
 * Created by sadath on 22-Jun-2015.
 */
public class DefaultValidityResource extends BaseResource {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date validFrom;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date validTill;

    public Date getValidTill() {
        return validTill;
    }

    public void setValidTill(Date validTill) {
        this.validTill = validTill;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }
}