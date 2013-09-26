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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((busUris == null) ? 0 : busUris.hashCode());
        result = prime * result + ((easyEsbNodeAdminEndpoint == null) ? 0 : easyEsbNodeAdminEndpoint.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Proxification other = (Proxification) obj;
        if (busUris == null) {
            if (other.busUris != null)
                return false;
        } else if (!busUris.equals(other.busUris))
            return false;
        if (easyEsbNodeAdminEndpoint == null) {
            if (other.easyEsbNodeAdminEndpoint != null)
                return false;
        } else if (!easyEsbNodeAdminEndpoint.equals(other.easyEsbNodeAdminEndpoint))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Proxification [busUris=" + busUris + ", easyEsbNodeAdminEndpoint=" + easyEsbNodeAdminEndpoint + "]";
    }    

}
