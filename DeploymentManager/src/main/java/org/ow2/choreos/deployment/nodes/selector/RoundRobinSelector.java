/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;

/**
 * Select nodes according the round robin rule among the existent nodes
 * 
 * The round robin order is ensured only if the client first creates the nodes
 * and just after uses them
 * 
 * @author leonardo
 * 
 */
public class RoundRobinSelector implements NodeSelector {

    private Logger logger = Logger.getLogger(RoundRobinSelector.class);

    private NodeSelectorMapper mapper = new NodeSelectorMapper(DeploymentManagerConfiguration.get("MAPPER_POLICY"));

    private AtomicInteger counter = new AtomicInteger();

    public List<Node> selectNodes(Config config, NodePoolManager npm) throws NodeNotSelectedException {

	int numberOfInstances = config.getNumberOfInstances();
	List<Node> allNodes = npm.getNodes();

	List<Node> compatibleNodes = mapper.filterByResourceImpact(config.getResourceImpact(), allNodes);

	if (compatibleNodes.size() < numberOfInstances) {
	    String message = "Not enough nodes (available: " + compatibleNodes.size()
		    + ") to deploy requested number of instances (requested: " + numberOfInstances + ")";
	    logger.error(message);
	    throw new NodeNotSelectedException(message);
	}

	List<Node> resultList = new ArrayList<Node>();

	int i = 0, j = 0;
	while (i < numberOfInstances && j < allNodes.size()) {
	    int idx = counter.getAndIncrement();
	    idx = idx % allNodes.size();
	    Node selected = allNodes.get(idx);
	    if (mapper.isAcceptable(config.getResourceImpact(), selected)) {
		logger.debug("Selector has chosen " + selected);
		resultList.add(selected);
		i++;
	    }
	    j++;
	}
	return resultList;
    }

}
