package org.ow2.choreos.chors.bus;

import java.util.List;

import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.NodeNotUpgradedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;

/**
 * Always creates a new EasyESB node.
 * 
 * @author leonardo
 *
 */
class SimpleBusHandler implements BusHandler {

	private static final String EASY_ESB_RECIPE = "easyesb";
	
	private NodePoolManager npm;
	
	public SimpleBusHandler(NodePoolManager npm) {
		this.npm = npm;
	}
	
	// to test purposes
	SimpleBusHandler(NodePoolManager npm, int waitStep) {
		this.npm = npm;
	}
	
	@Override
	public EasyESBNode retrieveBusNode() throws NoBusAvailableException {

		String endpoint = null;
		try {
			List<Node> nodes = this.npm.applyConfig(new Config(EASY_ESB_RECIPE));
			// TODO verify if there is not other esb node there
			this.npm.upgradeNode(nodes.get(0).getId());
			endpoint = this.getEndpoint(nodes.get(0).getIp());
		} catch (ConfigNotAppliedException e) {
			throw new NoBusAvailableException();
		} catch (NodeNotUpgradedException e) {
			throw new NoBusAvailableException();
		} catch (NodeNotFoundException e) {
			throw new NoBusAvailableException();
		}
		return new EasyESBNodeImpl(endpoint);
	}
	
	private String getEndpoint(String nodeIp) {
		return "http://" + nodeIp + ":8180/services/adminExternalEndpoint";
	}

}
