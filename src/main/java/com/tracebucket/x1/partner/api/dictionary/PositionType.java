package com.tracebucket.x1.partner.api.dictionary;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Created by Vishwajit on 10-06-2015.
 */
@JsonFormat(shape = JsonFormat.Shape.SCALAR)
public enum PositionType {

    MANAGERIAL("Managerial","MANAGERIAL"),
    FRONT_OFFICE("Front_Office","FRONT_OFFICE"),
    BACK_OFFICE("Back_Office", "BACK_OFFICE"),
    FIELD_STAFF("Field_Staff","FIELD_STAFF");

    private final String name;
    private final String description;

    PositionType(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
