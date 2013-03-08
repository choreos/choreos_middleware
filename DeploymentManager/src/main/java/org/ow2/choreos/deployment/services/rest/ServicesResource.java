package org.ow2.choreos.deployment.services.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBElement;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.ServicesManagerImpl;
import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.deployment.services.ServiceNotDeletedException;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.ServiceNotFoundException;
import org.ow2.choreos.deployment.services.ServiceNotModifiedException;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.diff.UnhandledModificationException;

/**
 * Service Deployer REST API 
 * Resource: services
 * 
 * @author alfonso, leonardo, nelson
 * 
 */
@Path("services")
public class ServicesResource {
	
	private Logger logger = Logger.getLogger(ServicesResource.class);
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
	private ServicesManager serviceDeployer = new ServicesManagerImpl(npm);
	
	/**
	 * Deploys a service
	 * 
	 * @param serviceSpecXML
	 *            Request's body content with a ServiceSpec XML
	 * @return HTTP code 201 and Location header if the service was successfully deployed;
	 *         HTTP code 400 if request can not be properly parsed; 
	 *         HTTP code 500 if any other error occurs.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response deployService(JAXBElement<ServiceSpec> serviceSpecXML, 
			@Context UriInfo uriInfo) {

		ServiceSpec serviceSpec = serviceSpecXML.getValue();
		if (serviceSpec.getPackageUri() == null || serviceSpec.getPackageUri().isEmpty() 
				|| serviceSpec.getPackageType() == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		logger.debug("Request to deploy " + serviceSpec.getPackageUri());

		Service service;
		try {
			service = serviceDeployer.createService(serviceSpec);
		} catch (ServiceNotDeployedException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		//TODO: AVISA QUE NÃO TEM NÓ MEU CARO!!!
		logger.info(service.getName() + " deployed on " + service.getUris());
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ServicesResource.class).path(service.getName());
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
	@Path("{serviceID}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getService(@PathParam("serviceId") String serviceId) {
		
		if (serviceId == null || serviceId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		logger.debug("Request to get service " + serviceId);
		Service service;
		try {
			service = serviceDeployer.getService(serviceId);
		} catch (ServiceNotFoundException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		return Response.ok(service).build();
	}
	
	/**
	 * Client requests a service instances by ID
	 * 
	 * @param serviceID of service of required instances
	 * @return a service found
	 */
	@GET
	@Path("{serviceID}/instances")
	@Produces(MediaType.APPLICATION_XML)
	public Response getServiceInstances(@PathParam("serviceId") String serviceId) {
		
		if (serviceId == null || serviceId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		logger.debug("Request to get instance " + serviceId + " of service "+ serviceId);
		Service service;
		try {
			service = serviceDeployer.getService(serviceId);
		} catch (ServiceNotFoundException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();			
		}
		
		return Response.ok(service.getInstances()).build();
	}
	
	/**
	 * Client requests a service instance by ID
	 * 
	 * @param serviceID of service of required instance
	 * @param instanceID of required instance
	 * @return a service found
	 */
	@GET
	@Path("{serviceID}/instances/{instanceID}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getServiceInstance(@PathParam("serviceId") String serviceId,
			@PathParam("instanceId") String instanceId) {
		
		if (serviceId == null || serviceId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		logger.debug("Request to get instance " + serviceId + " of service "+ serviceId);
		Service service;
		ServiceInstance instance;
		try {
			service = serviceDeployer.getService(serviceId);
			instance = service.getInstance(instanceId);
		} catch (ServiceNotFoundException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();			
		} catch (ServiceInstanceNotFoundException e) {
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
	@Path("{serviceID}")
	public Response deleteService(@PathParam("serviceId") String serviceId) {

		if (serviceId == null || serviceId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}

		logger.debug("Request to delete service " + serviceId);
		
		try {
			serviceDeployer.deleteService(serviceId);
		} catch (ServiceNotDeletedException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (ServiceNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		logger.info("Service " + serviceId + " deleted");

		return Response.ok().build();
	}
	
	/**
	 * Update a service
	 * 
	 * @param serviceSpecXML
	 *            Request's body content with a ServiceSpec XML
	 * @return HTTP code 201 and Location header if the service was successfully deployed;
	 *         HTTP code 400 if request can not be properly parsed; 
	 *         HTTP code 500 if any other error occurs.
	 * @throws UnhandledModificationException 
	 */
	@PUT
	@Path("{serviceID}")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response updateService(JAXBElement<ServiceSpec> serviceSpecXML, 
			@Context UriInfo uriInfo) throws UnhandledModificationException {

		ServiceSpec serviceSpec = serviceSpecXML.getValue();
		if (serviceSpec.getPackageUri() == null || serviceSpec.getPackageUri().isEmpty() 
				|| serviceSpec.getPackageType() == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		logger.debug("Request to update " + serviceSpec.getName());

		//Service service;
		try {
			serviceDeployer.updateService(serviceSpec);
		} catch (ServiceNotModifiedException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
		
		logger.info(serviceSpec.getName() + " update. Running on somewhere" /*service.getUris()*/);
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ServicesResource.class).path(serviceSpec.getName());
		URI location = uriBuilder.build();

		return Response.created(location).entity(serviceSpec).build();
	}
}