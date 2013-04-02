package org.ow2.choreos.chors.bus;

import org.ow2.choreos.deployment.nodes.NodePoolManager;


/**
 * Selects the same EasyESBNode to all the instances.
 * 
 * @author leonardo
 *
 */
public class SingleESBNodeSelector extends BaseESBNodeSelector {

	public SingleESBNodeSelector(NodePoolManager npm) {
		
		super(SingleBusHandler.getInstance(npm));
	}

	// to test purposes
	SingleESBNodeSelector(BusHandler busHandler) {
		
		super(busHandler);
	}
}
