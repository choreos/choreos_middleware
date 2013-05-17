package org.ow2.choreos.deployment.nodes;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.nodes.NPMException;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.Concurrency;

/**
 * Maintains a pool of VMs.
 * 
 * The VMs in the pool are not knew by the NPM.
 * The idea is to provide a fast VM creation:
 * when NPM requests a VM to the pool,
 * the pool returns an already created VM that was in the pool,
 * but NPM will see it just like a new VM created quickly.
 * 
 * @author leonardo
 *
 */
public class IdlePool {

	private static final int FILLING_POOL_TIMEOUT_MINUTES = 10;
	
	private static IdlePool instance;

	private static Logger logger = Logger.getLogger(IdlePool.class);
	
	private Set<Node> idleNodes = new HashSet<Node>();
	private NodeCreator nodeCreator;
	private int poolSize;
	private ExecutorService fillerExecutor = Executors.newSingleThreadExecutor();
	
	private IdlePool(int poolSize, NodeCreator nodeCreator) {
		this.poolSize = poolSize;
		this.nodeCreator = nodeCreator;
	}
	
	public static IdlePool getInstance(NodeCreator nodeCreator) {
	
		int poolSize = 0;
		try {
			poolSize = Integer.parseInt(Configuration.get("POOL_SIZE"));
		} catch (NumberFormatException e) {
			String msg = "You should set POOL_SIZE on the property files";
			logger.error(msg);
			throw new IllegalStateException(msg);
		}
		return getInstance(poolSize, nodeCreator);
	}
	
	/**
	 * Thread safe
	 * @param poolSize
	 * @param nodeCreator
	 * @return
	 */
	public static IdlePool getInstance(int poolSize, NodeCreator nodeCreator) {
		
		synchronized(IdlePool.class) {
			if (instance == null) {
				instance = new IdlePool(poolSize, nodeCreator);
			}
		}
		return instance;
	}

	/**
	 * Not thread safe
	 * @param poolSize
	 * @param nodeCreator
	 * @return
	 */
	public static IdlePool getCleanInstance(int poolSize, NodeCreator nodeCreator) {
		instance = new IdlePool(poolSize, nodeCreator);
		return instance;
	}
	
	/**
	 * 
	 * @return an unmodifiable list with the ids of the nodes in the idle pool
	 */
	public Set<Node> getIdleNodes() {
		return Collections.unmodifiableSet(idleNodes);
	}
	
	/**
	 * Retrieves a node from the idle pool.
	 * 
	 * The node is removed from the pool.
	 * The method is synchronized, so multiple invocations will not get the same node.
	 * If the pool is empty, the client waits for the creation of a VM
	 * 
	 * @throws NodeNotCreatedException 
	 * @return
	 */
	public Node retriveNode() throws NodeNotCreatedException {
		
		if (idleNodes.isEmpty()) {
			VMCreator vmCreator = new VMCreator(nodeCreator);
			vmCreator.run();
			if (!vmCreator.ok) {
				throw new NodeNotCreatedException("");
			}
		}
		
		synchronized (this) {
			Node node = idleNodes.iterator().next();
			idleNodes.remove(node);
			return node;
		}
	}
	
	/**
	 * Creates extra VMs asynchronously 
	 * @param howManyVMs
	 * @param nodeCreator
	 */
	public void createExtraVMs(int howManyVMs) {
		
		for (int i=0; i<howManyVMs; i++) {
			VMCreator vmCreator = new VMCreator(nodeCreator);
			Thread thrd = new Thread(vmCreator);
			thrd.start();
		}
	}
	
	/**
	 * Give a order to fill the pool.
	 * The execution is asynchronous, i.e.,
	 * the client will not wait for the pool be filled.
	 */
	public void fillPool() {
		
		PoolFiller filler = new PoolFiller();
		this.fillerExecutor.execute(filler);
	}

	private class VMCreator implements Runnable {

		NodeCreator nodeCreator;
		boolean ok;

		public VMCreator(NodeCreator nodeCreator) {
			this.nodeCreator = nodeCreator;
		}
		
		@Override
		public void run() {
			try {
				Node node = nodeCreator.create(new Node(), null);
				ok = true;
				synchronized (IdlePool.this) {
					idleNodes.add(node);
				}
			} catch (NPMException e) {
				logger.error("Could not create one of the extra VMs to the pool");
				ok = false;
			}
		}
	}
	
	private class PoolFiller implements Runnable {

		@Override
		public void run() {

			int extra = poolSize - idleNodes.size();
			logger.info("Going to create " + extra + " extra VMs on the idle pool");
			if (extra > 0) {
				ExecutorService executor = Executors.newFixedThreadPool(extra);
				for (int i=0; i<extra; i++) {
					VMCreator vmCreator = new VMCreator(nodeCreator);
					executor.execute(vmCreator);
				}
				Concurrency.waitExecutor(executor, FILLING_POOL_TIMEOUT_MINUTES);
			}
		}
	}
}
