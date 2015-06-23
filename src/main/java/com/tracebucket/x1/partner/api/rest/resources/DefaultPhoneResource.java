package com.tracebucket.x1.partner.api.rest.resources;

import com.tracebucket.tron.assembler.BaseResource;
import com.tracebucket.x1.dictionary.api.domain.PhoneType;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Created by sadath on 15-Jun-2015.
 */
public class DefaultPhoneResource extends BaseResource {
    @Size(min = 1, max = 50)
    @Pattern(regexp = "^[0-9\\-()+]*$")//numbers () only + -
    private String number;

    @Size(min = 1, max = 20)
    @Pattern(regexp = "^[0-9\\-()]*$")//numbers () -
    private String extension;

    private PhoneType phoneType;
    private boolean defaultPhone;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
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