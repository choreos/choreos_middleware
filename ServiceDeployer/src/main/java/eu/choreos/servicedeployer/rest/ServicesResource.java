package eu.choreos.servicedeployer.rest;

import java.net.MalformedURLException;
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

import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.npm.NodePoolManager;
import eu.choreos.servicedeployer.npm.NodePoolManagerClient;

/**
 * Service Deployer REST API 
 * Resource: services
 * 
 * @author alfonso, leonardo, nelson
 * 
 */
@Path("services")
public class ServicesResource {

	private NodePoolManager npm = new NodePoolManagerClient();
	private ServiceDeployer serviceDeployer = new ServiceDeployer(npm);

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
		if (serviceSpec.getCodeUri() == null || serviceSpec.getType() == null)
			return Response.status(Status.BAD_REQUEST).build();

		try {
			Service service = new Service(serviceSpec);
			serviceDeployer.deploy(service);
			
			UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
			uriBuilder = uriBuilder.path(ServicesResource.class).path(service.getId());
			URI location = uriBuilder.build();

			return Response.created(location).entity(service).build();
		} catch (MalformedURLException e) {
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
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
	public Service getService(@PathParam("serviceID") String serviceID) {

		// TODO trocar bloco abaixo para o que precisamos fazer
		Service service = serviceDeployer.getService(serviceID);
		return service;
	}

	/**
	 * Client requests update specifications of a service
	 * 
	 * @param serviceXML
	 *            : new specifications in order to update
	 * @return
	 */
	@PUT
	@Consumes(MediaType.APPLICATION_XML)
	public Response updateService(JAXBElement<Service> serviceXML) {
		Service service = serviceXML.getValue();
		if (service.getId() == null || service.getUri() == null)
			return Response.status(Status.BAD_REQUEST).build();

		Service updatedService = serviceDeployer.updateService(service);
		return Response.ok(updatedService).build();
	}

	/**
	 * Client requests remove a service by ID
	 * 
	 * @param serviceID
	 */
	@DELETE
	@Path("{serviceID}")
	public Response deleteService(@PathParam("serviceID") String serviceID) {

		if (serviceID.length() == 0)
			return Response.status(Status.BAD_REQUEST).build();

		if (serviceDeployer.getService(serviceID) == null)
			return Response.status(Status.NOT_FOUND).build();

		serviceDeployer.deleteService(serviceID);

		System.out.println("deleting service " + serviceID);

		return Response.ok().build();
	}
}
