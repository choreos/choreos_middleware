package org.ow2.choreos.enactment.rest;

import java.net.URI;

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

import org.ow2.choreos.enactment.EnactEngImpl;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;

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

	/**
	 * POST /chors
	 * 
	 * Body: a choreography specification (without id)
	 * Creates a new choreography that still have to be enacted (POST /chors/{chorID}/enactment).
	 * 
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 201 (CREATED)
	 * 			Location header: the just created choreography URI, containing the choreography ID.
	 * 			HTTP code 400 (BAD_REQUEST) if the ChorService list is not properly provided in the request body
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(ChorSpec chor, @Context UriInfo uriInfo) {

		if (chor == null || chor.getServiceSpecs() == null || chor.getServiceSpecs().isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		String chorId = ee.createChoreography(chor);
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();
		
		return Response.created(location).build();
	}
	
	/**
	 * GET /chors/{chorID}
	 * 
	 * Body: empty
	 * Retrieve the choreography specification
	 * 
	 * @param chorId the choreography id provided in the URI
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 200 (OK). 
	 * 			Location header: the choreography URI.  
	 *			Body response: the Choreography specification
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
		
		Choreography chor = ee.getChoreography(chorId);
		if (chor == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();

		return Response.ok(chor).location(location).build();
	}

	/**
	 * POST /chors/{chorID}/enactment
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
	@Path("{chorID}/enactment")
	@Produces(MediaType.APPLICATION_XML)
	public Response enact(@PathParam("chorID") String chorId, @Context UriInfo uriInfo) {
		
		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();
		
		Choreography chor;
		try {
			chor = ee.enact(chorId);
		} catch (EnactmentException e) {
			return Response.serverError().build(); // 500
		}
		if (chor == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(chor).location(location).build();
	}
	
}
