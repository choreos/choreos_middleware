package eu.choreos.servicedeployer.rest;

import java.net.MalformedURLException;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import eu.choreos.servicedeployer.ServiceDeployer;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;

/**
 * Service Deployer REST API resource: services
 * 
 * @author alfonso, leonardo, nelson
 * 
 */
@Path("services")
public class ServicesResource {

	ServiceDeployer serviceDeployer = new ServiceDeployer();

	/**
	 * Client requests a service (new or already created)
	 * 
	 * @param serviceXML
	 *            Service request XML
	 * @return A new service (or service already created) according to
	 *         specifications.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response requestService(JAXBElement<ServiceSpec> serviceSpecXML) {

		ServiceSpec serviceSpec = serviceSpecXML.getValue();
		if (serviceSpec.getCodeUri() == null || serviceSpec.getType() == null)
			return Response.status(Status.BAD_REQUEST).build();

		try {
			Service service = new Service(serviceSpec);
			String deployedHost = serviceDeployer.deploy(service);
			return Response.ok(deployedHost).build();
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
