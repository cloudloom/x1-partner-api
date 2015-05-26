package com.tracebucket.x1.partner.api.dictionary;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by ffl on 26-05-2014.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum PartnerCategory {
    INDIVIDUAL("Individual", "INDIVIDUAL"),
    GROUP("Group", "GROUP"),
    ORGANIZATION("Organization", "ORGANIZATION");

    private final String categoryName;
    private final String abbreviation;

    PartnerCategory(String categoryName, String abbreviation){
        this.categoryName = categoryName;
        this.abbreviation = abbreviation;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public String getAbbreviation() {
        return abbreviation;
    }
}
