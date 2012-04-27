package eu.choreos.nodepoolmanager.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import eu.choreos.nodepoolmanager.Controller;
import eu.choreos.nodepoolmanager.NodeNotFoundException;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;


@Path("nodes")
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

    	Node newNode = new Node();
    	controller.createNode(newNode);
		
    	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
    	// TODO should be getId instead nodeIp
    	// and the node should be in the body
		uriBuilder = uriBuilder.path(NodesResource.class).path(newNode.getIp());
		URI uri = uriBuilder.build();
    	return Response.created(uri).build();
    }
    
    @GET
    @Path("{node_id:.+}")
    public Response getNode(@PathParam("node_id") String id) {
        
    	Response response;
        CloudProvider infrastructure = new AWSCloudProvider();

        try {
            NodeRestRepresentation node = infrastructure.getNode(id).getRestRepresentation();
            response = Response.ok(node).build();
        } catch (NodeNotFoundException e) {
            response = Response.status(Status.NOT_FOUND).build();
        }

        return response;
    }

    @DELETE
    @Path("{node_id:.+}")
    public Response deleteNode(@PathParam("node_id") String id) {
    	
    	CloudProvider infrastructure = new AWSCloudProvider();
        infrastructure.destroyNode(id);

        return Response.status(Status.OK).build();
    }
    
   
}