package com.tracebucket.x1.partner.api.test.builder;

import com.tracebucket.x1.dictionary.api.domain.PhoneType;
import com.tracebucket.x1.partner.api.rest.resources.DefaultPhoneResource;

/**
 * Created by sadath on 16-Apr-15.
 */
public class DefaultPhoneResourceBuilder {
    private Long number;
    private Integer extension;
    private PhoneType phoneType;
    private boolean defaultPhone;

    private DefaultPhoneResourceBuilder(){ }

    public static DefaultPhoneResourceBuilder aPhoneBuilder(){
        return new DefaultPhoneResourceBuilder();
    }

    public DefaultPhoneResourceBuilder withNumber(Long number){
        this.number = number;
        return this;
    }

    public DefaultPhoneResourceBuilder withExtension(Integer extension){
        this.extension = extension;
        return this;
    }

    public DefaultPhoneResourceBuilder withPhoneType(PhoneType phoneType){
        this.phoneType = phoneType;
        return this;
    }

    public DefaultPhoneResourceBuilder withDefaultPhonee(boolean defaultPhone){
        this.defaultPhone = defaultPhone;
        return this;
    }

    public DefaultPhoneResource build() {
        DefaultPhoneResource phone = new DefaultPhoneResource();
        phone.setNumber(number);
        phone.setExtension(extension);
        phone.setPhoneType(phoneType);
        phone.setDefaultPhone(defaultPhone);
        return phone;
    }
}
