package com.tracebucket.x1.partner.api.rest.resources;

import java.util.Map;

/**
 * Created by sadath on 26-Jul-2015.
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultLoggedInEmployeeMinimalResource {
    private Map<String, String> loggedInEmployee;

    public Map<String, String> getLoggedInEmployee() {
        return loggedInEmployee;
    }

    public void setLoggedInEmployee(Map<String, String> loggedInEmployee) {
        this.loggedInEmployee = loggedInEmployee;
    }
}