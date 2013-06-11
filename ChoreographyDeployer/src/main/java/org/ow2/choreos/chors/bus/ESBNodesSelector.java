/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.bus.selector.ESBNodeSelector;
import org.ow2.choreos.chors.bus.selector.ESBNodeSelectorFactory;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.ServiceInstance;

/**
 * 
 * @author leonardo
 * 
 */
public class ESBNodesSelector {

    private Logger logger = Logger.getLogger(ESBNodesSelector.class);

    /**
     * Selects EasyESB nodes to each one of the service instances. 
     * EASY_ESB services (CDs) will be not proxified.
     * 
     * @param choreography
     * @return a map whose key is a serviceInstance and the value is the
     *         EasyESB node that must be used to proxify the serviceInstance; if
     *         it was not possible to select an esb node to a specific instance,
     *         there will be not a key to this instance.
     */
    public Map<ServiceInstance, EasyESBNode> selectESBNodes(Choreography choreography) {
	Map<ServiceInstance, EasyESBNode> selectedESBNodes = new HashMap<ServiceInstance, EasyESBNode>();
	ESBNodeSelector selector = ESBNodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
	InstancesFilter filter = new InstancesFilter();
	List<ServiceInstance> instancesToBeProxified = filter.filter(choreography.getServices());
	for (ServiceInstance serviceInstance : instancesToBeProxified) {
	    ResourceImpact resourceImpact = serviceInstance.getServiceSpec().getResourceImpact();
	    try {
		EasyESBNode esbNode = selector.select(resourceImpact, 1).get(0);
		selectedESBNodes.put(serviceInstance, esbNode);
	    } catch (NotSelectedException e) {
		logger.error("Could not select ESB node to an instance of "
			+ serviceInstance.getServiceSpec().getUuid());
	    }
	}
	return selectedESBNodes;
    }

}
