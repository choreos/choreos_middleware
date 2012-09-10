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
import org.ow2.choreos.npm.NodeNotCreatedException;
import org.ow2.choreos.npm.NodeNotDestroyed;
import org.ow2.choreos.npm.NodeNotFoundException;
import org.ow2.choreos.npm.NodeNotUpgradedException;
import org.ow2.choreos.npm.NodePoolManager;
import org.ow2.choreos.npm.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.datamodel.NodeRestRepresentation;
import org.ow2.choreos.servicedeployer.Configuration;



@Path("nodes")
public class NodesResource {

	private Logger logger = Logger.getLogger(NodesResource.class);
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));
    
	/**
	 * POST /nodes
	 * 
	 * Create a node on the cloud infrastructure.
	 * 
	 * Body: node representation used as specification
	 * 
	 * @param node the node representation provided in the Body
	 * @param uriInfo provided by the REST framework
	 * @return HTTP code 201 (CREATED)
	 *          Body: representation of the just created node
	 * 			Location header: the just created node URI, containing the node ID.
	 * 			HTTP code 500 (BAD_REQUEST) if could not create node
	 * @throws URISyntaxException
	 */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createNode(NodeRestRepresentation node, @Context UriInfo uriInfo) throws URISyntaxException {

    	logger.debug("Request to create node");
    	
    	Node newNode = new Node(node);
    	try {
			npm.createNode(newNode);
		} catch (NodeNotCreatedException e) {
			logger.warn("Node not created", e);
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
    	
    	logger.info(newNode + " created");
		
    	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(NodesResource.class).path(newNode.getId());
		URI uri = uriBuilder.build();
		NodeRestRepresentation nodeRest = new NodeRestRepresentation(newNode);
    	return Response.created(uri).entity(nodeRest).build();
    }

	/**
	 * GET /nodes
	 * 
	 * Retrieve information about all the nodes managed by this Node Pool Manager.
	 * 
	 * @return HTTP code 200 (OK)
	 *          Body: representation of the retrieved nodes
	 */
    @GET
    public List<NodeRestRepresentation> getNodes() {

    	logger.debug("Request to get nodes");
    	
    	List<NodeRestRepresentation> restNodeList = new ArrayList<NodeRestRepresentation>();
    	for (Node node: npm.getNodes()){
    		restNodeList.add(node.getRestRepresentation());
    	}
        return restNodeList;
    }
    
	/**
	 * GET /nodes/{nodeId}
	 * 
	 * Retrieve information of a node according to a given node id.
	 * 
	 * @param nodeId the node id provided in the URI
	 * @return HTTP code 200 (OK)
	 *          Body: representation of the retrieved node
	 *          HTTP code 404 (NOT_FOUND) if nodeId is not properly provided. 
	 */
    @GET
    @Path("{node_id:.+}")
    public Response getNode(@PathParam("node_id") String nodeId) {
        
    	logger.debug("Request to get node " + nodeId);
    	
        Response response;
		try {
			Node node = npm.getNode(nodeId);
			NodeRestRepresentation nodeRest = new NodeRestRepresentation(node);
			response = Response.ok(nodeRest).build();
		} catch (NodeNotFoundException e) {
			logger.error("Node not found", e);
			response = Response.status(Status.NOT_FOUND).build();
		}

        return response;
    }

	/**
	 * DELETE /nodes/{nodeId}
	 * 
	 * Destroys the Virtual Machine node.
	 * 
	 * @param nodeId the node id provided in the URI
	 * @return HTTP code 200 (OK).
	 *          HTTP code 404 (NOT_FOUND) if nodeId is not properly provided. 
	 *          HTTP code 500 (INTERNAL_SERVER_ERROR) if node is not destroyed.
	 */
    @DELETE
    @Path("{node_id:.+}")
    public Response deleteNode(@PathParam("node_id") String id) {
    	
    	logger.debug("Request to delete node");

    	Response response;
    	try {
			this.npm.destroyNode(id);
			response = Response.status(Status.OK).build();
			logger.info("Node " + id + " deleted");
		} catch (NodeNotDestroyed e) {
			logger.error("Nodes not destroyed", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		} catch (NodeNotFoundException e) {
			logger.error("Nodes not destroyed", e);
			response = Response.status(Status.NOT_FOUND).build();
		}
        
        return response;
    }
    
	/**
	 * POST /nodes/{nodeId}/upgrade
	 * 
	 * Apply configurations on selected node.
	 * Such configurations are the ones requested through the <code>applyConfig</code> operation.
	 * 
	 * @param nodeId the node id provided in the URI
	 * @return HTTP code 200 (OK).
	 *          HTTP code 404 (NOT_FOUND) if nodeId is not properly provided. 
	 *          HTTP code 500 (INTERNAL_SERVER_ERROR) if node is not destroyed.
	 */
    @POST
    @Path("{node_id:.+}/upgrade")
    public Response upgradeNode(@PathParam("node_id") String nodeId) {

    	logger.debug("Request to upgrade node " + nodeId);
    	
    	Response response;
    	try {
			npm.upgradeNode(nodeId);
			logger.info("Nodes upgraded");
			response = Response.status(Status.OK).build();
		} catch (NodeNotUpgradedException e) {
			logger.error("Nodes not upgraded", e);
			response = Response.status(Status.NOT_FOUND).build();
		} catch (NodeNotFoundException e) {
			logger.error("Nodes not upgraded", e);
			response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
		}
    	
        return response;
    }
   
}