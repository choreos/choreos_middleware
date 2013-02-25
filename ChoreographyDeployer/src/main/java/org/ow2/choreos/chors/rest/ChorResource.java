package org.ow2.choreos.chors.rest;

import java.net.URI;

import javax.ws.rs.Consumes;
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

import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.ChorDeployerImpl;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;

/**
 * Enactment Engine REST API.
 * Resource: chors (choreographies).
 * 
 * @author leonardo
 *
 */
@Path("chors")
public class ChorResource {
	
	private ChoreographyDeployer ee = new ChorDeployerImpl();

	/**
	 * POST /chors
	 * 
	 * Body: a choreography specification
	 * Creates a new choreography that still have to be enacted (POST /chors/{chorID}/enactment).
	 * 
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 201 (CREATED)
	 * 			Location header: the just created choreography URI, containing the choreography ID.
	 * 			HTTP code 400 (BAD_REQUEST) if the chorSpec is not properly provided in the request body
	 */
	@POST
	@Consumes(MediaType.APPLICATION_XML)
	public Response create(ChorSpec chor, @Context UriInfo uriInfo) {

		if (chor == null || chor.getChorServiceSpecs() == null || chor.getChorServiceSpecs().isEmpty()) {
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
	 * Retrieve the choreography information
	 * 
	 * @param chorId the choreography id provided in the URI
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 200 (OK). 
	 * 			Location header: the choreography URI.  
	 *			Body response: the Choreography representation
	 *			HTTP code 400 (BAD_REQUEST) if chorId is not properly provided 
	 *			HTTP code 404 (NOT_FOUND) if choreography does not exist
	 */
	@GET
	@Path("{chorID}")
	@Produces(MediaType.APPLICATION_XML)
	public Response get(@PathParam("chorID") String chorId, @Context UriInfo uriInfo) {
		
		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		Choreography chor;
		try {
			chor = ee.getChoreography(chorId);
		} catch (ChoreographyNotFoundException e) {
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
			return Response.serverError().build(); 
		} catch (ChoreographyNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(chor).location(location).build();
	}
	
	@PUT
	@Path("{chorID}/update")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_XML)
	public Response update(@PathParam("chorID") String chorId, ChorSpec spec, @Context UriInfo uriInfo) {
		
		if (chorId == null || chorId.isEmpty()) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		
		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
		URI location = uriBuilder.build();
		
		Choreography chor;
		try {
			ee.update(chorId, spec);
			chor = ee.getChoreography(chorId);
		} catch (EnactmentException e) {
			return Response.serverError().build();
		} catch (ChoreographyNotFoundException e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		
		return Response.ok(chor).location(location).build();
	}
	
}
