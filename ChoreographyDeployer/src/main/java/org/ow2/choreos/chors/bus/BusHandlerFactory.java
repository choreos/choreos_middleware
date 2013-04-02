package org.ow2.choreos.chors.bus;

import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.rest.NodesClient;

/**
 * 
 * @author leonardo
 *
 */
public class BusHandlerFactory {

	private static BusHandler busHandler; // singleton
	
	/**
	 * Returns a BusHandler that will create EasyESB nodes 
	 * using the Deployment Manager nodes API.
	 * 
	 * @return a BusHandler
	 */
	public static BusHandler getInstance() {
		
		synchronized (BusHandlerFactory.class) {
			if (busHandler == null) {
				String host = Configuration.get(Option.DEPLOYMENT_MANAGER_URI);
				NodePoolManager npm = new NodesClient(host);
				busHandler = new SingleBusHandler(npm);
			}
		} 
		
		return busHandler;
	}

}
