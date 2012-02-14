package eu.choreos.ServiceDeployer.rest;

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

import eu.choreos.ServiceDeployer.datamodel.Service;
import eu.choreos.ServiceDeployer.datamodel.ServiceSpec;

/**
 * Service Deployer REST API
 * resource: services
 * 
 * @author alfonso, leonardo, nelson
 *
 */
@Path("/serviceDeployer")
public class ServicesResource {

	/**
	 * Client requests a service (new or already created) 
	 * @param serviceXML Service request XML
	 * @return A new service (or service already created) according to specifications.  
	 */
	@POST
	@Path("/services")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response requestService(JAXBElement<ServiceSpec> serviceSpecXML) {
		
		ServiceSpec serviceSpec = serviceSpecXML.getValue();
		if (serviceSpec.getCodeUri() == null || serviceSpec.getType() == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		// TODO trocar bloco abaixo para o que precisamos fazer
		Service service = new Service();
		service.setId("12345"); 
		
		return Response.ok(service).build();
	}
	
	/**
	 * Client requests a service by ID
	 * @param serviceID of required service 
	 * @return a service found
	 */
	@GET
	@Path("/services/{serviceID}")
	@Produces(MediaType.APPLICATION_XML)
	public Service getService(@PathParam("serviceID") String serviceID){
		
		// TODO trocar bloco abaixo para o que precisamos fazer
		Service service = new Service();
		service.setId(serviceID+"007");
		service.setUri("localhost");
		
		return service;
	}

	/**
	 * Client requests update specifications of a service 
	 * @param serviceXML: new specifications in order to update 
	 */
	@PUT
	@Path("/services/")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateService(JAXBElement<Service> serviceXML) {
		//TODO
	}
	
	
	/**
	 * Client requests remove a service by ID	
	 * @param serviceID
	 */
	@DELETE
	@Path("/services/{serviceID}")
	public void deleteService(@PathParam("serviceID") String serviceID) {
		
		System.out.println("deleting service " + serviceID);
		// TODO se servico não existir, retornar erro
	}

}
