package org.ow2.choreos.enactment.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.ow2.choreos.enactment.EnactEngImpl;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

@Path("choreographies")
public class ChorResource {
	
	private EnactmentEngine ee = new EnactEngImpl();
	private AtomicInteger counter = new AtomicInteger();

	@GET
	public Response operation() {
		
		return Response.ok().build();
	}
	
//	@POST
//	@Consumes(MediaType.APPLICATION_XML)
//	@Produces(MediaType.APPLICATION_XML)
//	public Response enact(Choreography chor, @Context UriInfo uriInfo) {
//		
//		if (chor == null) {
//			return Response.status(Status.BAD_REQUEST).build();
//		}
//		
//		// Map<String, Service> deployed = ee.enact(chor);
//		
//		Map<String, Service> deployed = new HashMap<String, Service>();
//		Service s = new Service();
//		s.setUri("localhost:1234/airline");
//		s.setName("airline");
//		deployed.put("airline", s);
//		s = new Service();
//		s.setUri("localhost:1235/travel");
//		s.setName("travel-agency");
//		deployed.put("travel", s);
//		
//		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
//		String chorId = Integer.toString(counter.incrementAndGet());
//		uriBuilder = uriBuilder.path(ChorResource.class).path(chorId);
//		URI location = uriBuilder.build();
//
//		return Response.created(location).entity(deployed.values()).build();
//	}
}
