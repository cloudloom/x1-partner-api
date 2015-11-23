package com.tracebucket.x1.partner.api.dictionary.enums.converter;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.dictionary.Salutation;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by sadath on 23-Nov-2015.
 */
@Converter(autoApply = true)
public class SalutationTypeConverter implements AttributeConverter<Salutation, String> {
    @Override
    public String convertToDatabaseColumn(Salutation salutation) {
        switch (salutation) {
            case Mr:
                return "Mr";
            case Mrs:
                return "Mrs";
            case Dr:
                return "Dr";
            default:
                throw new IllegalArgumentException("Unknown value: " + salutation);
        }
    }

    @Override
    public Salutation convertToEntityAttribute(String s) {
        switch (s) {
            case "Mr":
                return Salutation.Mr;
            case "Mrs":
                return Salutation.Mrs;
            case "Dr":
                return Salutation.Dr;
            default:
                throw new IllegalArgumentException("Unknown value: " + s);
        }
    }
}
