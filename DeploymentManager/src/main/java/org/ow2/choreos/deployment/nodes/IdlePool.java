package org.ow2.choreos.deployment.nodes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jclouds.compute.RunNodesException;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.NodeRegistry;
import org.ow2.choreos.nodes.datamodel.Node;

public class IdlePool {
	
	private static IdlePool instance;

	private static Logger logger = Logger.getLogger(IdlePool.class);
	
	// value is the node id
	private Set<String> idleNodes = new HashSet<String>();
	private NodeRegistry nodeRegistry = NodeRegistry.getInstance(); 
	private CloudProvider cp;
	private int poolSize;
	
	private IdlePool(int poolSize, CloudProvider cp) {
		this.poolSize = poolSize;
		this.cp = cp;
	}
	
	public static IdlePool getInstance(CloudProvider cp) {
	
		int poolSize = 0;
		try {
			poolSize = Integer.parseInt(Configuration.get("POOL_SIZE"));
		} catch (NumberFormatException e) {
			String msg = "You should set POOL_SIZE on the property files";
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
		return getInstance(poolSize, cp);
	}
	
	public static IdlePool getInstance(int poolSize, CloudProvider cp) {
		
		synchronized(IdlePool.class) {
			if (instance == null) {
				instance = new IdlePool(poolSize, cp);
			}
		}
		return instance;
	}
	
	/**
	 * 
	 * @return an unmodifiable list with the ids of the nodes in the idle pool
	 */
	public Set<String> getIdleNodes() {
		return Collections.unmodifiableSet(idleNodes);
	}
	
	/**
	 * Creates extra VMs asynchronously 
	 * @param howManyVMs
	 * @param cp
	 */
	public void createExtraVMs(int howManyVMs) {
		
		for (int i=0; i<howManyVMs; i++) {
			VMCreator vmCreator = new VMCreator(cp);
			Thread thrd = new Thread(vmCreator);
			thrd.start();
		}
	}
	
	public void fillPool() {
		
		int extra = this.poolSize - this.idleNodes.size();
		this.createExtraVMs(extra);
	}

	private class VMCreator implements Runnable {

		CloudProvider cp;

		public VMCreator(CloudProvider cp) {
			this.cp = cp;
		}
		
		@Override
		public void run() {
			try {
				Node node = cp.createNode(new Node(), null);
				nodeRegistry.putNode(node);
				synchronized (IdlePool.this) {
					logger.info("Adding " + node.getId() + " to the idle pool");
					idleNodes.add(node.getId());
				}
			} catch (RunNodesException e) {
				logger.error("Could not create one of the extra VMs to the pool");
			}
		}
	}
}
