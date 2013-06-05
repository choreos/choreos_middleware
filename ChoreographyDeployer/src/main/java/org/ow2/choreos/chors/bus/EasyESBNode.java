package org.ow2.choreos.chors.bus;

import java.io.IOException;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public interface EasyESBNode {

    public String getAdminEndpoint();

    /**
     * Binds and exposes a service into the EasyESB node.
     * 
     * @param serviceUrl
     * @param serviceWsdl
     * @return the URI to access the service exposed by the bus
     * @throws IOException
     *             if could not complete the operation
     */
    public String proxifyService(String serviceUrl, String serviceWsdl) throws ManagementException;

}
