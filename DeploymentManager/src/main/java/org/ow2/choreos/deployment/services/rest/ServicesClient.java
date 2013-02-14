package org.ow2.choreos.deployment.services.rest;

import javax.ws.rs.WebApplicationException;

import org.apache.commons.lang.NotImplementedException;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.deployment.services.ServiceDeployer;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;

/**
 * Access Service Deployer functionalities through the REST API.
 * 
 * The user of <code>ServiceDeployerClient</code> does not need to worry with the REST communication.
 * 
 * @author leonardo
 *
 */
public class ServicesClient implements ServiceDeployer {

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
	
	@Override
	public Service deploy(ServiceSpec serviceSpec) throws ServiceNotDeployedException {
		
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
	public Service getService(String serviceId) {
		
		throw new NotImplementedException();
	}

	@Override
	public void deleteService(String serviceId) {
		
		throw new NotImplementedException();
	}

}
