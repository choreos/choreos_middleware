package org.ow2.choreos.chors.bus;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;
import org.ow2.choreos.utils.Concurrency;

import esstar.petalslink.com.service.management._1_0.ManagementException;


/**
 * Selects EasyESB nodes to each one of the service instances.
 * 
 * @author leonardo, thiago
 *
 */
public class ServiceInstancesProxifier {

	private static final int PROXIFY_TIMEOUT = 10; // minutes 
	
	private Logger logger = Logger.getLogger(ServiceInstancesProxifier.class);
	
	/**
	 * Selects EasyESB nodes to proxify service instances and proxify them.
	 * The property busUris (a map) of the serviceInstances objects are changed:
	 * the proxified address is the value to serviceInstance.busUris.get(ServiceType.SOAP)
	 * 
	 * If there are problems, there is no exception to not prevent other threads completion
	 * However, clients may check serviceInstance.busUris.get(ServiceType.SOAP) value to verify success
	 * 
	 * @param service whose instances will be proxified 
	 */
	public void proxify(List<ServiceInstance> serviceInstances) {
		
		final int N = serviceInstances.size();
		ExecutorService executor = Executors.newFixedThreadPool(N);

		logger.info("Requesting services proxification");
		for (ServiceInstance svcInstance: serviceInstances) {
			Proxifier proxifier = new Proxifier(svcInstance);
			executor.submit(proxifier);
		}
		
		Concurrency.waitExecutor(executor, PROXIFY_TIMEOUT, this.logger);
		
		logger.info("Proxification phase finished");
	}
	
	/**
	 * Chooses a EasyESBNode and proxifies a serviceIntance
	 * The property busUris (a map) of the serviceInstance is changed 
	 * It may throws the ManagementException and NoBusAvailableException
	 *
	 */
	private class Proxifier implements Runnable {

		ServiceInstance svcInstance;
		
		Proxifier(ServiceInstance svcInstance) {
			this.svcInstance = svcInstance;
		}
		
		@Override
		public void run() {
			
			String svcName = this.svcInstance.getMyParentServiceSpec().getName();
			logger.debug("Proxifying " + svcName + " instance");
			try {
				BusHandler busHandler = BusHandlerFactory.getInstance();
				EasyESBNode esbNode = busHandler.retrieveBusNode();
				String url = this.svcInstance.getNativeUri();
				String wsdl = url.replaceAll("/$", "").concat("?wsdl");
				String proxifiedAddress = esbNode.proxifyService(url, wsdl);
				this.svcInstance.setBusUri(ServiceType.SOAP, proxifiedAddress);
				this.svcInstance.setEasyEsbNodeAdminEndpoint(esbNode.getAdminEndpoint());
				logger.debug(svcName + " instance proxified as " + proxifiedAddress);
			} catch (NoBusAvailableException e) {
				logger.error("Could not proxify a service instance of " + svcName);
			} catch (ManagementException e) {
				logger.error("Could not proxify a service instance of " + svcName);
			}
		}
		
		
	}
}
