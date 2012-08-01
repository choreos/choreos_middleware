package org.ow2.choreos.enactment.rest;

import java.net.URI;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
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

import org.ow2.choreos.enactment.ChorRegistry;
import org.ow2.choreos.enactment.EnactEngImpl;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.datamodel.ChorService;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

/**
 * Enactment Engine REST API.
 * Resource: chors (choreogrpahies).
 * 
 * We choose a "many calls" API rather than a "big single call" one,
 * since a not so complex choreography can have a big XML description 
 * 
 * @author leonardo
 *
 */
@Path("chors")
public class ChorResource {
	
	private EnactmentEngine ee = new EnactEngImpl();
	private ChorRegistry reg = ChorRegistry.getInstance();

	/**
	 * POST /chors
	 * 
	 * Body: empty
	 * Creates a new choreography that still have to be configured and then enacted.
	 * 
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 201 (CREATED)
	 * 			Location header: the just created choreography URI, containing the choreography ID.
	 */
	@POST
	public Response create(@Context UriInfo uriInfo) {

		Choreography chor = reg.newChor();
		String chorId = chor.getId();
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();
		
		return Response.created(location).build();
	}
	
	/**
	 * POST /chors/{chorID}/services
	 * 
	 * Body: a ChorService representation. 
	 * Adds a service description to a choreography specification. 
	 * After the required invocations to this operation, 
	 * the client can invoke the enact operation (POST /chor/{chorID}/enact).
	 * 
	 * @param service a service specifcation to the choreography
	 * @param chorId the choreography id provided in the URI
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 201 (CREATED)
	 * 			Location header: the choreography URI.
	 * 	 		HTTP code 400 (BAS_REQUEST) if chorId is not properly provided. 
	 *			HTTP code 404 (NOT_FOUND) if there is no choreography with id == chorID.
	 */
	@POST
	@Path("{chorID}/services")
	@Consumes(MediaType.APPLICATION_XML)
	public Response addService(ChorService service, 
			@PathParam("chorID") String chorId, @Context UriInfo uriInfo) {

		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		if (reg.get(chorId) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		reg.addService(service, chorId);
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();
		
		return Response.created(location).build();
	}

	
	/**
	 * GET /chors/{chorID}
	 * 
	 * Body: empty
	 * Retrieve choreography specification
	 * 
	 * @param chorId the choreography id provided in the URI
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 200 (OK). 
	 * 			Location header: the choreography URI.  
	 *			Body response: a List of ChorService representing service specifications. 
	 *			HTTP code 400 (BAS_REQUEST) if chorId is not properly provided. 
	 *			HTTP code 404 (NOT_FOUND) if there is no choreography with id == chorID.
	 */
	@GET
	@Path("{chorID}")
	@Produces(MediaType.APPLICATION_XML)
	public Response getSpec(@PathParam("chorID") String chorId, @Context UriInfo uriInfo) {
		
		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Choreography chor = reg.get(chorId);
		if (chor == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();

		List<ChorService> body = chor.getServices(); 
		return Response.ok(body).location(location).build();
	}

	/**
	 * GET /chors/{chorID}/deployed
	 * 
	 * Body: empty
	 * Retrieve information about deployed services of a choreography
	 * It is the same return of POST /chors/{chorID}/enact
	 * 
	 * @param chorId the choreography id provided in the URI
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 200 (OK). 
	 * 			Location header: choreography deployed services URI.  
	 *			Body response: a Collection of Service representing deployed services.
	 *			HTTP code 400 (BAS_REQUEST) if chorId is not properly provided. 
	 *			HTTP code 404 (NOT_FOUND) if there is no choreography with id == chorID.
	 */
	@GET
	@Path("{chorID}/deployed")
	@Produces(MediaType.APPLICATION_XML)
	public Response getDeployed(@PathParam("chorID") String chorId, @Context UriInfo uriInfo) {
		
		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Choreography chor = reg.get(chorId);
		if (chor == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		Map<String, Service> deployed = new HashMap<String, Service>();
		// TODO fill the map
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId).path("deployed");
		URI location = uriBuilder.build();

		Collection<Service> body = deployed.values(); 
		return Response.ok(body).location(location).build();
	}
	
	/**
	 * POST /chors/{chorID}/enact
	 * 
	 * Body: empty
	 * Enacts a choreography
	 * 
	 * @param chorId the choreography id is provided in the URI
	 * 			and the choreography must be already configured
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 200 (OK). 
	 * 			Location header: choreography deployed services URI. 
	 *			Body response: a Collection of Service representing deployed services. 
	 *			HTTP code 400 (BAS_REQUEST) if chorId is not properly provided. 
	 *			HTTP code 404 (NOT_FOUND) if there is no choreography with id == chorID.
	 */
	@POST
	@Path("{chorID}/enact")
	@Produces(MediaType.APPLICATION_XML)
	public Response enact(@PathParam("chorID") String chorId, @Context UriInfo uriInfo) {
		
		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Choreography chor = reg.get(chorId);
		if (chor == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		Map<String, Service> deployed = ee.enact(chor);
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId).path("deployed");
		URI location = uriBuilder.build();

		Collection<Service> body = deployed.values();
		return Response.ok(body).location(location).build();
	}
}
