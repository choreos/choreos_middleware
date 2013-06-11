/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services.client;

import javax.ws.rs.WebApplicationException;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.ServiceNotDeployedException;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServiceNotModifiedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

/**
 * Access Service Deployer functionalities through the REST API.
 * 
 * The user of ServiceDeployerClient does not need to worry with
 * the REST communication.
 * 
 * @author leonardo
 * 
 */
public class ServicesClient implements ServicesManager {

    private String host;

    /**
     * 
     * @param host
     *            ex: 'http://localhost:9101/deploymentmanager'
     * 
     */
    public ServicesClient(String host) {
	this.host = host;
    }

    private WebClient setupClient() {

	WebClient client = WebClient.create(host);

	// remove time out
	// not proud of it!
	HTTPConduit http = (HTTPConduit) WebClient.getConfig(client).getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setConnectionTimeout(0);// indefined
	httpClientPolicy.setReceiveTimeout(0);// indefined
	http.setClient(httpClientPolicy);

	return client;
    }

    @Override
    public DeployableService createService(DeployableServiceSpec serviceSpec) throws ServiceNotDeployedException {
	WebClient client = setupClient();
	client.path("services");
	DeployableService service = null;
	try {
	    service = client.post(serviceSpec, DeployableService.class);
	} catch (WebApplicationException e) {
	    throw new ServiceNotDeployedException(serviceSpec.getUuid());
	}
	return service;
    }

    @Override
    public DeployableService getService(String uuid) throws ServiceNotFoundException {
	WebClient client = setupClient();
	client.path("services").path(uuid);
	DeployableService service = null;
	try {
	    service = client.get(null);
	} catch (WebApplicationException e) {
	    throw new ServiceNotFoundException(uuid);
	}
	return service;
    }

    @Override
    public void deleteService(String uuid) throws ServiceNotDeletedException {
	WebClient client = setupClient();
	client.path("services").path(uuid);
	try {
	    client.delete();
	} catch (WebApplicationException e) {
	    throw new ServiceNotDeletedException(uuid);
	}
    }

    @Override
    public DeployableService updateService(DeployableServiceSpec serviceSpec) throws ServiceNotModifiedException {
	WebClient client = setupClient();
	client.path("services").path(serviceSpec.getUuid());
	DeployableService service = null;
	try {
	    service = client.post(serviceSpec, DeployableService.class);
	} catch (WebApplicationException e) {
	    throw new ServiceNotModifiedException(serviceSpec.getUuid(), e.getMessage());
	}
	return service;
    }

}
