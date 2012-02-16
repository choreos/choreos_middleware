package br.usp.ime.choreos.nodepoolmanager;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import br.usp.ime.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;

@Path("/nodes")
public class NodesResource {

    
    private Controller controller = new Controller(new AWSCloudProvider());
    
    @GET
    public List<NodeRestRepresentation> getNodes() {

    	List<NodeRestRepresentation> restNodeList = new ArrayList<NodeRestRepresentation>();
    	for (Node node: controller.getNodes()){
    		restNodeList.add(node.getRestRepresentation());
    	}
        return restNodeList;
    }

    @POST
    public Response createNode(NodeRestRepresentation node, @Context UriInfo uriInfo) throws URISyntaxException {
        Response response;

    	String nodeId = controller.createNode(new Node(node));
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        uriBuilder.path(NodeResource.class);
        response = Response.created(uriBuilder.build(nodeId)).build();

        return response;
    }
    
    @POST
    public Response createNode(NodeRestRepresentation node, Cookbook coobook, @Context UriInfo uriInfo) throws URISyntaxException {
        Response response;

    	String nodeId = controller.createNode(new Node(node),coobook);
        //TODO
    	
        return null;
    }
}