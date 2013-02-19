package org.ow2.choreos.deployment.services.bus;

/**
 * Used by DeploymentManager to retrieve EasyESB nodes
 * 
 * @author leonardo, felps
 *
 */
public interface BusHandler {

	public EasyESBNode retrieveBusNode() throws NoBusAvailableException;
}
