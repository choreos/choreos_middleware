package br.usp.ime.choreos.nodepoolmanager;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jclouds.compute.RunNodesException;

import br.usp.ime.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;

@Path("/nodes")
public class NodesResource {

    
    private Controller controller = new Controller();
    
    @GET
    public List<Node> getNodes() {
        return controller.getNodes();
    }

    @POST
    public Response createNode(Node node, @Context UriInfo uriInfo) throws URISyntaxException {
        Response response;

    	String nodeId = controller.createNode(node);
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        uriBuilder.path(NodeResource.class);
        response = Response.created(uriBuilder.build(nodeId)).build();

        return response;
    }
}