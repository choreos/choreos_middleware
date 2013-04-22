package org.ow2.choreos.deployment.services.rest;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.ServiceNotFoundException;
import org.ow2.choreos.deployment.services.ServiceNotModifiedException;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;

/**
 * Access Service Deployer functionalities through the REST API.
 * 
 * The user of <code>ServiceDeployerClient</code> does not need to worry with the REST communication.
 * 
 * @author leonardo
 *
 */
public class ServicesClient implements ServicesManager {

	private String host;

	/**
	 * 
	 * @param host ex: 'http://localhost:9101/deploymentmanager'
	 * 
	 */
	public ServicesClient(String host) {
		this.host = host;
	}

	private WebClient setupClient() {

		WebClient client = WebClient.create(host);

		// remove time out
		// not proud of it!
		HTTPConduit http = (HTTPConduit)WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(0);//indefined
		httpClientPolicy.setReceiveTimeout(0);//indefined
		http.setClient(httpClientPolicy);

		return client;
	}

	// TODO: review this methods for new client path
	@Override
	public DeployedService createService(DeployedServiceSpec serviceSpec) throws ServiceNotDeployedException {

		WebClient client = setupClient();
		client.path("services");
		DeployedService service = null;
		try {
			service = client.post(serviceSpec, DeployedService.class);
		} catch (WebApplicationException e) {
			System.out.println(e.getMessage());
			System.out.println(service);
			System.out.println(service.getSpec());
			throw new ServiceNotDeployedException(service.getSpec().getUUID());
		}

		return service;
	}

	@Override
	public DeployedService getService(String uuid) throws ServiceNotFoundException{
		WebClient client = setupClient();
		client.path("services").path(uuid);
		DeployedService service = null;
		try {
			service = client.get(null);
		} catch (WebApplicationException e) {
			throw new ServiceNotFoundException(uuid);
		}

		return service;
	}

	@Override
	public void deleteService(String uuid) {

		throw new NotImplementedException();
	}

	@Override
	public DeployedService updateService(DeployedServiceSpec serviceSpec)
			throws ServiceNotModifiedException {
		System.out.println("sending   " + serviceSpec);
		WebClient client = setupClient();
		client.path("services").path(serviceSpec.getUUID());
		DeployedService service = null;
		try {
			service = client.post(serviceSpec, DeployedService.class);
		} catch (WebApplicationException e) {
			System.out.println(e.getMessage());
			throw new ServiceNotModifiedException(serviceSpec.getUUID(), e.getMessage());
		}
		return service;
	}

}
