package br.usp.ime.ccsl.choreos;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;


@Path("/hadoop/")
public class HadoopWS {


	@GET
	public Response getIntegradeApplication(@PathParam("application_id") String id,
			@Context UriInfo uriInfo) {

		Response response = null;

		try {
		}
		catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

}