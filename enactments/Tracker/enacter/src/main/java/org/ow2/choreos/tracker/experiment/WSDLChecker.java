package org.ow2.choreos.tracker.experiment;

import javax.ws.rs.WebApplicationException;

import org.apache.cxf.jaxrs.client.WebClient;

public class WSDLChecker {

    private static final String WSDL_EXCERPT = "portType";
    
    private String wsdl;

    public WSDLChecker(String wsdl) {
        this.wsdl = wsdl;
    }
    
    public boolean check() {
        WebClient client = WebClient.create(wsdl);
        try {
            String wsdlStr = client.get(String.class);
            return wsdlStr.contains(WSDL_EXCERPT);
        } catch (WebApplicationException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
}
