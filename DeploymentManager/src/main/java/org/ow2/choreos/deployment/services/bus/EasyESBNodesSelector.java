package org.ow2.choreos.deployment.services.bus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.Concurrency;

/**
 * Selects EasyESB nodes to each one of the service instances.
 * 
 * @author leonardo, thiago
 *
 */
public class EasyESBNodesSelector {

	private static final int PROXIFY_TIMEOUT = 5; // minutes 
	
	private Logger logger = Logger.getLogger(EasyESBNodesSelector.class);
	
	/**
	 * Selects EasyESB nodes to proxify the instances of the services and proxify them.
	 * The property busUris (a map) of the serviceInstances objects are changed:
	 * the proxified address is the value to serviceInstance.busUris.get(ServiceType.SOAP)
	 * 
	 * If there are problems, there is no exception to not prevent other threads completion
	 * However, clients may check serviceInstance.busUris.get(ServiceType.SOAP) value to verify success
	 * 
	 * @param service whose instances will be proxified 
	 */
	public void selecESBtNodes(Service service, NodePoolManager npm) {
		
		final int N = service.getInstances().size();
		ExecutorService executor = Executors.newFixedThreadPool(N);
		List<Future<Void>> futures = new ArrayList<Future<Void>>();

		logger.info("Requesting service " + service.getName() + " proxification");
		for (ServiceInstance svcInstance: service.getInstances()) {
			Proxifier proxifier = new Proxifier(svcInstance, npm);
			Future<Void> future = executor.submit(proxifier);
			futures.add(future);
		}
		
		Concurrency.waitExecutor(executor, PROXIFY_TIMEOUT, this.logger);
		
		for (Future<Void> f: futures) {
			
			try {
				Concurrency.checkFuture(f);
			} catch (ExecutionException e) {
				String msg = "Could not proxify a service instance of " + service.getName();
				logger.error(msg, e.getCause());
			} catch (IllegalStateException e) {
				String msg = "Could not proxify a service instance of " + service.getName();
				logger.error(msg, e.getCause());				
			}
		}
		
		logger.info(service.getName() + " proxification completed");
	}
	
	/**
	 * Chooses a EasyESBNode and proxifies a serviceIntance
	 * The property busUris (a map) of the serviceInstance is changed 
	 * It may throws the ManagementException and NoBusAvailableException
	 *
	 */
	private class Proxifier implements Callable<Void> {

		ServiceInstance svcInstance;
		NodePoolManager npm;
		
		Proxifier(ServiceInstance svcInstance, NodePoolManager npm) {
			this.svcInstance = svcInstance;
			this.npm = npm;
		}
		
		@Override
		public Void call() throws Exception  {
			
			BusHandler busHandler = new SimpleBusHandler(this.npm);
			EasyESBNode esbNode = busHandler.retrieveBusNode();
//			this.svcInstance.setBusUri(ServiceType.SOAP, proxifiedAddress);
			this.svcInstance.setEasyEsbNodeAdminEndpoint(esbNode.getAdminEndpoint());
			return null;
		}
		
		
	}
}
