package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.Concurrency;


/**
 * Makes a simple selection: the same EasyESBNode to all the instances.
 * 
 * @author leonardo
 *
 */
public class SingleESBNodeSelector implements ESBNodesSelector {

	private static final int BUS_SELECTION_TIMEOUT = 10; // minutes 
	
	private Logger logger = Logger.getLogger(SingleESBNodeSelector.class);
	
	private Map<ServiceInstance, EasyESBNode> selectedNodes;
	private BusHandler busHandler;
	
	public SingleESBNodeSelector(BusHandler busHandler) {
		this.busHandler = busHandler;
	}
	
	@Override
	public Map<ServiceInstance, EasyESBNode> selectESBNodes(Choreography choreography) {
		
		logger.info("Selecting ESB Nodes");

		this.selectedNodes = new ConcurrentHashMap<ServiceInstance, EasyESBNode>();
		
		List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
		for (Service svc: choreography.getServices()) {
			instances.addAll(svc.getInstances());
		}
		
		final int N = instances.size();
		ExecutorService executor = Executors.newFixedThreadPool(N);

		for (ServiceInstance svcInstance: instances) {
			Selector selector = new Selector(svcInstance);
			executor.submit(selector);
		}
		
		Concurrency.waitExecutor(executor, BUS_SELECTION_TIMEOUT, this.logger);
		
		logger.info("Nodes are already selected");
		
		return Collections.unmodifiableMap(this.selectedNodes);
	}
	
	/**
	 * Chooses a EasyESBNode and proxifies a serviceIntance
	 * The property busUris (a map) of the serviceInstance is changed 
	 * It may throws the ManagementException and NoBusAvailableException
	 *
	 */
	private class Selector implements Runnable {

		ServiceInstance svcInstance;
		
		Selector(ServiceInstance svcInstance) {
			this.svcInstance = svcInstance;
		}
		
		@Override
		public void run() {
			
			String svcName = this.svcInstance.getMyParentServiceSpec().getName();
			try {
				EasyESBNode esbNode = SingleESBNodeSelector.this.busHandler.retrieveBusNode();
				SingleESBNodeSelector.this.selectedNodes.put(svcInstance, esbNode);
				logger.debug("ESB node " + esbNode.getAdminEndpoint()
						+ " selected to the " + svcName + " instance");
			} catch (NoBusAvailableException e) {
				logger.error("Could not select esb node to "+ svcName + " instance");
				SingleESBNodeSelector.this.selectedNodes.put(svcInstance, null);
			}
		}
		
		
	}

}
