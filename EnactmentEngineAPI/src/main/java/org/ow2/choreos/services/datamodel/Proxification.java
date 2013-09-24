package org.ow2.choreos.services.datamodel;

import java.util.HashMap;
import java.util.Map;

public class Proxification {

    // Map containing all URIs for access instance through the bus
    private Map<ServiceType, String> busUris = new HashMap<ServiceType, String>();
    private String easyEsbNodeAdminEndpoint; // The node that handles bus calls
   
    public Map<ServiceType, String> getBusUris() {
        return busUris;
    }
    
    public void setBusUris(Map<ServiceType, String> busUris) {
        this.busUris = busUris;
    }

    public String getEasyEsbNodeAdminEndpoint() {
        return easyEsbNodeAdminEndpoint;
    }

    public void setEasyEsbNodeAdminEndpoint(String easyEsbNodeAdminEndpoint) {
        this.easyEsbNodeAdminEndpoint = easyEsbNodeAdminEndpoint;
    }

    public String getBusUri(ServiceType type) {
        return this.busUris.get(type);
    }

    public void setBusUri(ServiceType type, String uri) {
        this.busUris.put(type, uri);
    }    

}
