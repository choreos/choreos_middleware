package org.ow2.choreos.experiment.vms;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.NodeNotDestroyed;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory.CloudProviderType;
import org.ow2.choreos.deployment.nodes.datamodel.Node;

public class VMsDestroyer {

	private CloudProvider cp;
	private Logger logger = Logger.getLogger(VMsDestroyer.class);
	
	public VMsDestroyer(CloudProviderType cpType) {
		this.cp = CloudProviderFactory.getInstance(cpType);
	}
	
	public void destroyAll() {
		
		logger.info("Destroying all the nodes...");
		
		AtomicInteger counter = new AtomicInteger();
		for (Node node: this.cp.getNodes()) {
			
			try {
				this.cp.destroyNode(node.getId());
				System.out.print(counter.incrementAndGet() + " ");
			} catch (NodeNotDestroyed e) {
				this.logger.error("Node " + node.getId() + " not destroyed", e);
			} catch (NodeNotFoundException e) {
				this.logger.error("Node " + node.getId() + " not destroyed", e);
			}
		}
		System.out.println("");
	}

}
