/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.services.ServicesManagerImpl;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServiceNotDeletedException;
import org.ow2.choreos.services.ServiceNotFoundException;
import org.ow2.choreos.services.ServiceNotModifiedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.UnhandledModificationException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceInstance;

/**
 * Service Deployer REST API Resource: services
 * 
 * @author alfonso, leonardo, nelson
 * 
 */
@Path("services")
public class ServicesResource {

    private Logger logger = Logger.getLogger(ServicesResource.class);
    protected ServicesManager servicesManager;

    public ServicesResource() {
	ServicesManager servicesManager = new ServicesManagerImpl();
	this.servicesManager = servicesManager;
    }

    // constructor created to mock npm and servicesManager in tests
    public ServicesResource(ServicesManager servicesManager) {
	this.servicesManager = servicesManager;
    }

    /**
     * Deploys a service
     * 
     * @param serviceSpecXML
     *            Request's body content with a ServiceSpec XML
     * @return HTTP code 201 and Location header if the service was successfully
     *         deployed; HTTP code 400 if request can not be properly parsed;
     *         HTTP code 500 if any other error occurs.
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response deployService(DeployableServiceSpec serviceSpec, @Context UriInfo uriInfo) {

	if (serviceSpec.getPackageUri() == null || serviceSpec.getPackageUri().isEmpty()
		|| serviceSpec.getPackageType() == null)
	    return Response.status(Status.BAD_REQUEST).build();

	logger.debug("Request to deploy " + serviceSpec.getPackageUri());

	DeployableService service;
	try {
	    service = servicesManager.createService(serviceSpec);
	} catch (ServiceNotCreatedException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	logger.info(service.getSpec().getUuid() + " configured to be deployed on " + service.getUris());

	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
	uriBuilder = uriBuilder.path(ServicesResource.class).path(service.getSpec().getUuid());
	URI location = uriBuilder.build();

	return Response.created(location).entity(service).build();
    }

    /**
     * Client requests a service by ID
     * 
     * @param serviceID
     *            of required service
     * @return a service found
     */
    @GET
    @Path("{uuid}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getService(@PathParam("uuid") String uuid) {

	if (uuid == null || uuid.isEmpty()) {
	    return Response.status(Status.BAD_REQUEST).build();
	}

	logger.debug("Request to get service " + uuid);
	DeployableService service;
	try {
	    service = servicesManager.getService(uuid);
	} catch (ServiceNotFoundException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.ok(service).build();
    }

    /**
     * Client requests a service instances by ID
     * 
     * @param serviceID
     *            of service of required instances
     * @return a service found
     */
    @GET
    @Path("{uuid}/instances")
    @Produces(MediaType.APPLICATION_XML)
    public Response getServiceInstances(@PathParam("uuid") String uuid) {

	if (uuid == null || uuid.isEmpty()) {
	    return Response.status(Status.BAD_REQUEST).build();
	}

	logger.debug("Request to get instances of service " + uuid);
	DeployableService service;
	try {
	    service = servicesManager.getService(uuid);
	} catch (ServiceNotFoundException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.ok(service.getServiceInstances()).build();
    }

    /**
     * Client requests a service instance by ID
     * 
     * @param serviceID
     *            of service of required instance
     * @param instanceID
     *            of required instance
     * @return a service found
     */
    @GET
    @Path("{uuid}/instances/{instanceID}")
    @Produces(MediaType.APPLICATION_XML)
    public Response getServiceInstance(@PathParam("uuid") String uuid, @PathParam("instanceId") String instanceId) {

	if (uuid == null || uuid.isEmpty()) {
	    return Response.status(Status.BAD_REQUEST).build();
	}

	logger.debug("Request to get instance " + instanceId + " of service " + uuid);
	DeployableService service;
	ServiceInstance instance;
	try {
	    service = servicesManager.getService(uuid);
	    instance = service.getInstanceById(instanceId);
	} catch (ServiceNotFoundException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	} catch (IllegalArgumentException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	return Response.ok(instance).build();
    }

    /**
     * Client requests remove a service by ID
     * 
     * @param serviceID
     */
    @DELETE
    @Path("{uuid}")
    public Response deleteService(@PathParam("uuid") String uuid) {

	if (uuid == null || uuid.isEmpty()) {
	    return Response.status(Status.BAD_REQUEST).build();
	}

	logger.debug("Request to delete service " + uuid);

	try {
	    servicesManager.deleteService(uuid);
	} catch (ServiceNotDeletedException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	} catch (ServiceNotFoundException e) {
	    return Response.status(Status.NOT_FOUND).build();
	}

	logger.info("Service " + uuid + " deleted");

	return Response.ok().build();
    }

    /**
     * Update a service
     * 
     * @param serviceSpecXML
     *            Request's body content with a ServiceSpec XML
     * @return HTTP code 201 and Location header if the service was successfully
     *         deployed; HTTP code 400 if request can not be properly parsed;
     *         HTTP code 500 if any other error occurs.
     * @throws UnhandledModificationException
     */
    @POST
    @Path("{uuid}")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response updateService(DeployableServiceSpec serviceSpec, @PathParam("uuid") String uuid,
	    @Context UriInfo uriInfo) {

	if (serviceSpec.getPackageUri() == null || serviceSpec.getPackageUri().isEmpty()
		|| serviceSpec.getPackageType() == null)
	    return Response.status(Status.BAD_REQUEST).build();

	logger.debug("Request to update " + uuid);

	DeployableService service;
	try {
	    service = servicesManager.updateService(serviceSpec);
	} catch (ServiceNotModifiedException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	} catch (UnhandledModificationException e) {
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	logger.info(uuid + " updated. Running on " + service.getUris());

	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
	uriBuilder = uriBuilder.path(ServicesResource.class).path(service.getSpec().getUuid());

	Response build = Response.ok(service).build();
	return build;
    }
}
