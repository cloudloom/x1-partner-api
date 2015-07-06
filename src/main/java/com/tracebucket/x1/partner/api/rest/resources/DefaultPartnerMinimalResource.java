package com.tracebucket.x1.partner.api.rest.resources;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sadath on 06-Jul-2015.
 */
@com.fasterxml.jackson.databind.annotation.JsonSerialize(include = com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion.NON_NULL)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
public class DefaultPartnerMinimalResource {
    private List<Map<String, String>> partners = new ArrayList<Map<String, String>>();

    public List<Map<String, String>> getPartners() {
        return partners;
    }

    public void setPartners(List<Map<String, String>> partners) {
        this.partners = partners;
    }
}