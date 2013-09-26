package org.ow2.choreos.chors.datamodel;

import org.ow2.choreos.services.datamodel.Proxification;

public class LegacyServiceInstance {

    private String uri;
    private Proxification proxification;
    
    public String getUri() {
        return uri;
    }
    
    public void setUri(String uri) {
        this.uri = uri;
    }
    
    public Proxification getProxification() {
        return proxification;
    }
    
    public void setProxification(Proxification proxification) {
        this.proxification = proxification;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((uri == null) ? 0 : uri.hashCode());
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
        LegacyServiceInstance other = (LegacyServiceInstance) obj;
        if (uri == null) {
            if (other.uri != null)
                return false;
        } else if (!uri.equals(other.uri))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "LegacyServiceInstance [uri=" + uri + ", proxification=" + proxification + "]";
    }
    
}
