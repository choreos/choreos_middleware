package eu.choreos.nodepoolmanager.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import eu.choreos.nodepoolmanager.Controller;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

@Path("nodes/configs")
public class ConfigResource {

	private Controller controller = new Controller(new AWSCloudProvider());
	
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response applyConfig(Config config, @Context UriInfo uriInfo) throws URISyntaxException {
        
		if (config == null || config.getName() == null || config.getName().isEmpty())
			return Response.status(Status.BAD_REQUEST).build();
		
    	Node node = controller.applyConfig(config);
		
    	if (node == null)  // config not applied!
    		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        URI uri = uriBuilder.path(NodesResource.class).build(node.getId());
        Response response = Response.created(uri).build();
        // FIXME response is wrong
        // it is http://localhost:8080/nodes
        // it should be http://localhost:8080/nodes/{nodeId}
        
    	return response;
    }
}
