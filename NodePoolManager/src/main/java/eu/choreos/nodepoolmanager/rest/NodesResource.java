package eu.choreos.nodepoolmanager.rest;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;


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
    @Consumes(MediaType.APPLICATION_XML)
    public Response createNode(NodeRestRepresentation node, @Context UriInfo uriInfo) throws URISyntaxException {
        Response response;

    	String nodeId = controller.createNode(new Node(node));
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        uriBuilder.path(NodeResource.class);
        response = Response.created(uriBuilder.build(nodeId)).build();

        return response;
    }
    
    @POST
    @Path("/configs")
    @Consumes(MediaType.APPLICATION_XML)
    public Response applyConfig(Config config, @Context UriInfo uriInfo) throws URISyntaxException {
        
		if (config == null || config.getName() == null)
			return Response.status(Status.BAD_REQUEST).build();
		
    	Node node = controller.applyConfig(config);
    	
    	if (node == null)  // config not applied!
    		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	
    	// TODO responder código 201
    	Response response = null;
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        uriBuilder.path(NodeResource.class);
        response = Response.created(uriBuilder.build(node.getId())).build();
        
    	return response;
    }
}