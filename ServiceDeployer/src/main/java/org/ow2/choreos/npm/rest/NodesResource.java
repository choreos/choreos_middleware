package org.ow2.choreos.npm.rest;

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

import org.apache.log4j.Logger;
import org.ow2.choreos.npm.NPMImpl;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.cloudprovider.AWSCloudProvider;
import org.ow2.choreos.npm.cloudprovider.CloudProvider;
import org.ow2.choreos.npm.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;
import org.ow2.choreos.servicedeployer.Configuration;



@Path("nodes")
public class NodesResource {

	private Logger logger = Logger.getLogger(NodesResource.class);
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
    
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createNode(NodeRestRepresentation node, @Context UriInfo uriInfo) throws URISyntaxException {

    	logger.debug("Request to create node");
    	
    	Node newNode = new Node();
    	npm.createNode(newNode);
    	
    	if (!newNode.hasIp()) {
    		logger.warn("Node not created");
    		return Response.status(Status.INTERNAL_SERVER_ERROR).build();
    	}
    	
    	logger.info(newNode + " created");
		
    	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(NodesResource.class).path(newNode.getId());
		URI uri = uriBuilder.build();
		NodeRestRepresentation nodeRest = new NodeRestRepresentation(newNode);
    	return Response.created(uri).entity(nodeRest).build();
    }
    
    @GET
    public List<NodeRestRepresentation> getNodes() {

    	logger.debug("Request to get nodes");
    	
    	List<NodeRestRepresentation> restNodeList = new ArrayList<NodeRestRepresentation>();
    	for (Node node: npm.getNodes()){
    		restNodeList.add(node.getRestRepresentation());
    	}
        return restNodeList;
    }
    
    @GET
    @Path("{node_id:.+}")
    public Response getNode(@PathParam("node_id") String id) {
        
    	logger.debug("Request to get node " + id);
    	
        Node node = npm.getNode(id);
        NodeRestRepresentation nodeRest = new NodeRestRepresentation(node);
        Response response;

        if (node != null)
        	response = Response.ok(nodeRest).build();
        else
        	response = Response.status(Status.NOT_FOUND).build();

        return response;
    }

    @DELETE
    @Path("{node_id:.+}")
    public Response deleteNode(@PathParam("node_id") String id) {
    	
    	logger.debug("Request to delete node");
    	
    	CloudProvider infrastructure = new AWSCloudProvider();
        infrastructure.destroyNode(id);

        logger.info("Node " + id + " deleted");
        
        return Response.status(Status.OK).build();
    }
    
    /**
     * Runs chef-client on nodes.
     * @return
     */
    @POST
    @Path("upgrade")
    public Response upgradeNodes() {

    	logger.debug("Request to upgrade");
    	boolean ok = npm.upgradeNodes();
    	
    	Response response;
        if (ok) {
        	logger.info("Nodes upgraded");
        	response = Response.status(Status.OK).build();
        } else {
        	logger.info("Nodes not upgraded");
        	response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }
    
    /**
     * Runs chef-client on a chosen node.
     * @return
     */
    @POST
    @Path("upgrade/{node_id}")
    public Response upgradeNode(@PathParam("node_id") String nodeId) {

    	logger.debug("Request to upgrade node " + nodeId);
    	boolean ok = npm.upgradeNode(nodeId);
    	
    	Response response;
        if (ok) {
        	logger.info("Nodes upgraded");
        	response = Response.status(Status.OK).build();
        } else {
        	logger.info("Nodes not upgraded");
        	response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }
   
}