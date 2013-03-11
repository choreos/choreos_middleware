package org.ow2.choreos.deployment.services.rest;

import java.util.Collections;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.ResponseReader;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.deployment.services.ServiceNotFoundException;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.ServiceNotModifiedException;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.registry.DeployedServicesRegistry;

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
	public Service createService(ServiceSpec serviceSpec) throws ServiceNotDeployedException {

		WebClient client = setupClient();
		client.path("services");
		Service service = null;
		try {
			service = client.post(serviceSpec, Service.class);
		} catch (WebApplicationException e) {
			throw new ServiceNotDeployedException(serviceSpec.getName());
		}

		return service;
	}

	@Override
	public Service getService(String serviceId) throws ServiceNotFoundException{
		WebClient client = setupClient();
		client.path("services").path(serviceId);
		Service service = null;
		try {
			service = client.get(null);
		} catch (WebApplicationException e) {
			throw new ServiceNotFoundException(serviceId);
		}

		return service;
	}

	@Override
	public void deleteService(String serviceId) {

		throw new NotImplementedException();
	}

	@Override
	public Service updateService(String serviceId, ServiceSpec serviceSpec)
			throws ServiceNotModifiedException {

		WebClient client = setupClient();
		client.path("services").path(serviceId);
		Service service;
		try {
			service = client.post(serviceSpec, Service.class);
		} catch (WebApplicationException e) {
			throw new ServiceNotModifiedException(serviceSpec.getName());
		}
		
		//System.out.println("\n\n Entity \n\n" + response.getEntity() + "\n\n");
		
		//if(response.getStatus() != 200) {
		//	ServiceNotModifiedException e = new ServiceNotModifiedException(serviceId, "Error: Status code = " + response.getStatus());
			//System.out.println(e.getMessage());
		//	throw e;
		//}
		//System.out.println("ULTIMA CHANCE!!!!n\n\n\n");
		//Service svc = (Service) response.getEntity();
		//System.out.println("OIEEE!!!! " + svc);
		return service;
	}

}
