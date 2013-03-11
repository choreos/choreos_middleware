package org.ow2.choreos.chors.bus;

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
 * This class is intended to be used as a singleton.
 * 
 * @author leonardo, felps
 *
 */
class SimpleBusHandler implements BusHandler {

	private static final String EASY_ESB_RECIPE = "easyesb";
	private int SYNC_WAIT_STEP = 30; // seconds 
	
	
	private volatile String endpoint;
	private NodePoolManager npm;
	private boolean creating = false;
	
	public SimpleBusHandler(NodePoolManager npm) {
		this.npm = npm;
	}
	
	// to test purposes
	SimpleBusHandler(NodePoolManager npm, int waitStep) {
		this.npm = npm;
		this.SYNC_WAIT_STEP = 1;
	}
	
	@Override
	public EasyESBNode retrieveBusNode() throws NoBusAvailableException {

		this.sync();
		
		if (endpoint == null) {
			try {
				List<Node> nodes = this.npm.applyConfig(new Config(EASY_ESB_RECIPE));
				this.npm.upgradeNode(nodes.get(0).getId());
				this.endpoint = this.getEndpoint(nodes.get(0).getIp());
				this.creating = false;
			} catch (ConfigNotAppliedException e) {
				throw new NoBusAvailableException();
			} catch (NodeNotUpgradedException e) {
				throw new NoBusAvailableException();
			} catch (NodeNotFoundException e) {
				throw new NoBusAvailableException();
			}
		}
		return new EasyESBNodeImpl(this.endpoint);
	}
	
	private void sync() {
		
		boolean wait = false;
		synchronized (this) {
			if (this.creating) {
				wait = true;
			}
			if (!this.creating && this.endpoint == null) {
				this.creating = true;
			}
		}
		
		if (wait) {
			while (this.creating) {
				sleep();
			}
		}
	}

	private void sleep() {
		try {
			Thread.sleep(SYNC_WAIT_STEP * 1000);
		} catch (InterruptedException e) {
			// hope never get here ^^
		}
	}

	private String getEndpoint(String nodeIp) {
		return "http://" + nodeIp + ":8180/services/adminExternalEndpoint";
	}

}
