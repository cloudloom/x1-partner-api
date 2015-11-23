package com.tracebucket.x1.partner.api.dictionary.enums.converter;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by sadath on 23-Nov-2015.
 */
@Converter(autoApply = true)
public class PartnerCategoryConverter implements AttributeConverter<PartnerCategory, String>  {
    @Override
    public String convertToDatabaseColumn(PartnerCategory partnerCategory) {
        switch (partnerCategory) {
            case INDIVIDUAL:
                return "INDIVIDUAL";
            case GROUP:
                return "GROUP";
            case ORGANIZATION:
                return "ORGANIZATION";
            default:
                throw new IllegalArgumentException("Unknown value: " + partnerCategory);
        }
    }

    @Override
    public PartnerCategory convertToEntityAttribute(String s) {
        switch (s) {
            case "INDIVIDUAL":
                return PartnerCategory.INDIVIDUAL;
            case "GROUP":
                return PartnerCategory.GROUP;
            case "ORGANIZATION":
                return PartnerCategory.ORGANIZATION;
            default:
                throw new IllegalArgumentException("Unknown value: " + s);
        }
    }
}
