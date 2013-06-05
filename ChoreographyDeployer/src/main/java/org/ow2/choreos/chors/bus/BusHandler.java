package org.ow2.choreos.chors.bus;

/**
 * Retrieve EasyESB nodes
 * 
 * @author leonardo, felps
 * 
 */
public interface BusHandler {

    public EasyESBNode retrieveBusNode() throws NoBusAvailableException;
}
