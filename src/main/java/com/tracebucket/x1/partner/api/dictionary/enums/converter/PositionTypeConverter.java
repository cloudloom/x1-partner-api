package com.tracebucket.x1.partner.api.dictionary.enums.converter;

import com.tracebucket.x1.partner.api.dictionary.PartnerCategory;
import com.tracebucket.x1.partner.api.dictionary.PositionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * Created by sadath on 23-Nov-2015.
 */
@Converter(autoApply = true)
public class PositionTypeConverter implements AttributeConverter<PositionType, String> {
    @Override
    public String convertToDatabaseColumn(PositionType positionType) {
        switch (positionType) {
            case MANAGERIAL:
                return "MANAGERIAL";
            case FRONT_OFFICE:
                return "FRONT_OFFICE";
            case BACK_OFFICE:
                return "ORGANIZATION";
            case FIELD_STAFF:
                return "FIELD_STAFF";
            default:
                throw new IllegalArgumentException("Unknown value: " + positionType);
        }    }

    @Override
    public PositionType convertToEntityAttribute(String s) {
        switch (s) {
            case "MANAGERIAL":
                return PositionType.MANAGERIAL;
            case "FRONT_OFFICE":
                return PositionType.FRONT_OFFICE;
            case "BACK_OFFICE":
                return PositionType.BACK_OFFICE;
            case "FIELD_STAFF":
                return PositionType.FIELD_STAFF;
            default:
                throw new IllegalArgumentException("Unknown value: " + s);
        }       }
}
