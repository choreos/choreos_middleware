package br.usp.ime.ccsl.choreos.hadoop;

import java.io.File;
import java.io.PrintWriter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.apache.hadoop.conf.Configuration;


@Path("/hadoop/")
public class HadoopWS {


	@GET
	public Response getIntegradeApplication(@Context UriInfo uriInfo) {

		Response response = null;

		try {
			File hdConfPath = new File(System.getProperty("hadoop.home", System.getProperty("user.home")) + File.separator +
										"conf");
			Configuration conf = new Configuration(true);
			conf.writeXml(System.out);
		}
		catch (Exception e) {
			e.printStackTrace();
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}

		return response;
	}

}