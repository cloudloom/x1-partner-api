package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;

/**
 * Created by sadath on 15-Jun-2015.
 */
public class DefaultPhoneResource extends BaseResource {
    private Long number;
    private Integer extension;
    private PhoneType phoneType;
    private boolean defaultPhone;

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Integer getExtension() {
        return extension;
    }

    public void setExtension(Integer extension) {
        this.extension = extension;
    }

    public PhoneType getPhoneType() {
        return phoneType;
    }

    public void setPhoneType(PhoneType phoneType) {
        this.phoneType = phoneType;
    }

    public boolean isDefaultPhone() {
        return defaultPhone;
    }

    public void setDefaultPhone(boolean defaultPhone) {
        this.defaultPhone = defaultPhone;
    }
}