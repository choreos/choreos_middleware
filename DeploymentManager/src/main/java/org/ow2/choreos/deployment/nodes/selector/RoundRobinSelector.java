package org.ow2.choreos.deployment.nodes.selector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.NPMException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;


/**
 * Select nodes according the round robin rule
 * among the existent nodes
 * 
 * The round robin order is ensured only if the client
 * first creates the nodes and just after uses them
 * 
 * @author leonardo
 *
 */
public class RoundRobinSelector implements NodeSelector {

	private Logger logger = Logger.getLogger(RoundRobinSelector.class);
	
	private AtomicInteger counter = new AtomicInteger();
	private CloudProvider cloudProvider;
	
	public RoundRobinSelector(CloudProvider cloudProvider) {
		this.cloudProvider = cloudProvider;
	}
	
	public List<Node> selectNodes(Config config) {
		
		int numberOfInstances = config.getNumberOfInstances();
		List<Node> allNodes = cloudProvider.getNodes();

		if(allNodes.size() < numberOfInstances)
			try {
				throw new NPMException("Not enough nodes (available: " +allNodes.size()+ 
						") to deploy requested number of instances (requested: " +numberOfInstances+ ")");
			} catch (NPMException e) {
				logger.error(e.getMessage());
			}
		
		List<Node> resultList = new ArrayList<Node>();
		for(int i=0;i < numberOfInstances; i++) {
			int idx = counter.getAndIncrement();
			idx = idx % allNodes.size();
			Node selected = allNodes.get(idx);
			resultList.add(selected);
			logger.debug("Selector has chosen " + selected);
		}
		
		return resultList;
	}

}
