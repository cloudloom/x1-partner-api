package com.tracebucket.x1.partner.api.rest.resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadath on 26-Jun-2015.
 */
@JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultEmployeeRestructureResource {
    private HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> employeeStructure;

    public HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> getEmployeeStructure() {
        return employeeStructure;
    }

    public void setEmployeeStructure(HashMap<String, HashMap<String, ArrayList<Map<String, String>>>> employeeStructure) {
        this.employeeStructure = employeeStructure;
    }
}