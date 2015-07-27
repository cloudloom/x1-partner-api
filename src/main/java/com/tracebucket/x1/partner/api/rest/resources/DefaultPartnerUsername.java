package com.tracebucket.x1.partner.api.rest.resources;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sadath on 27-Jul-2015.
 */
public class DefaultPartnerUsername {
    private Map<String, String> userName = new HashMap<String, String>();

    public Map<String, String> getUserName() {
        return userName;
    }

    public void setUserName(Map<String, String> userName) {
        this.userName = userName;
    }
}
