package org.ow2.choreos.chors.bus;

import org.ow2.choreos.nodes.NodePoolManager;

/**
 * Selects a different ESB Node to each service.
 * 
 * Note it relies on NPM to provide different nodes.
 * 
 * @author leonardo
 *
 */
public class AlwaysCreateESBNodeSelector extends BaseESBNodeSelector {

	public AlwaysCreateESBNodeSelector(NodePoolManager npm) {
		
		super(new SimpleBusHandler(npm));
	}
	
	// to test purposes
	AlwaysCreateESBNodeSelector(BusHandler busHandler) {
		
		super(busHandler);
	}

}
