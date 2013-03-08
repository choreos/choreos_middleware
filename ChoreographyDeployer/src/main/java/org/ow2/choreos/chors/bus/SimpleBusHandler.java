package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;

/**
 * Always return the same EasyESB node, that is created in the first invocation.
 * 
 * @author leonardo, felps
 *
 */
public class SimpleBusHandler implements BusHandler {

	private static final String EASY_ESB_RECIPE = "easyesb";
	private static List<String> endpoints = new ArrayList<String>();
	private NodePoolManager npm;
	
	public SimpleBusHandler(NodePoolManager npm) {
		this.npm = npm;
	}
	
	@Override
	public EasyESBNode retrieveBusNode() throws NoBusAvailableException {
		
		if (endpoints.isEmpty()) {
			try {
				List<Node> nodes = this.npm.applyConfig(new Config(EASY_ESB_RECIPE), 1);
				this.npm.upgradeNode(nodes.get(0).getId());
				String endpoint = this.getEndpoint(nodes.get(0).getIp());
				endpoints.add(endpoint);
			} catch (ConfigNotAppliedException e) {
				throw new NoBusAvailableException();
			} catch (NodeNotUpgradedException e) {
				throw new NoBusAvailableException();
			} catch (NodeNotFoundException e) {
				throw new NoBusAvailableException();
			}
		}
		return new EasyESBNodeImpl(endpoints.get(0));
	}

	private String getEndpoint(String nodeIp) {
		return "http://" + nodeIp + ":8180/services/adminExternalEndpoint";
	}

}
