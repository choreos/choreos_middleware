/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.rest;

import java.net.URI;
import java.net.URISyntaxException;

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
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

@Path("nodes")
public class NodesResource {

    private String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
    private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));

    private Logger logger = Logger.getLogger(NodesResource.class);

    /**
     * POST /nodes
     * 
     * Create an active node.
     * 
     * Body: node spec
     * 
     * @param nodeSpec
     *            passed in the body
     * @param uriInfo
     *            provided by the REST framework
     * @return HTTP code 201 (CREATED) Body: representation of the just created
     *         node; Location header: the just created node URI, containing the
     *         node ID. HTTP code 500 (BAD_REQUEST) if could not create node
     * @throws URISyntaxException
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response createNode(NodeSpec nodeSpec, @Context UriInfo uriInfo) throws URISyntaxException {

	logger.debug("Request to create node");

	CloudNode node = null;
	try {
	    node = npm.createNode(nodeSpec);
	} catch (NodeNotCreatedException e) {
	    logger.warn("Node not created", e);
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	logger.info(node + " created");

	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
	uriBuilder = uriBuilder.path(NodesResource.class).path(node.getId());
	URI uri = uriBuilder.build();
	return Response.created(uri).entity(node).build();
    }

    /**
     * GET /nodes/{nodeId}
     * 
     * Retrieve information of a node according to a given node id.
     * 
     * @param nodeId
     *            the node id provided in the URI
     * @return HTTP code 200 (OK) Body: representation of the retrieved node
     *         HTTP code 404 (NOT_FOUND) if nodeId is not properly provided.
     */
    @GET
    @Path("{node_id:.+}")
    public Response getNode(@PathParam("node_id") String nodeId) {

	logger.debug("Request to get node " + nodeId);

	Response response;
	try {
	    CloudNode node = npm.getNode(nodeId);
	    response = Response.ok(node).build();
	} catch (NodeNotFoundException e) {
	    logger.error("Node " + nodeId + " not found");
	    response = Response.status(Status.NOT_FOUND).build();
	}
	return response;
    }

    /**
     * POST /nodes/{nodeId}/update
     * 
     * Updates and installs new software installed in the selected node.
     * 
     * @param nodeId
     *            the node id, provided in the URI
     * @return HTTP code 200 (OK). HTTP code 404 (NOT_FOUND) if nodeId is not
     *         properly provided. HTTP code 500 (INTERNAL_SERVER_ERROR) if node
     *         is not destroyed.
     */
    @POST
    @Path("{node_id:.+}/update")
    public Response updateNode(@PathParam("node_id") String nodeId) {

	logger.debug("Request to update node " + nodeId);

	Response response;
	try {
	    npm.updateNode(nodeId);
	    logger.info("Node " + nodeId + " updated");
	    response = Response.status(Status.OK).build();
	} catch (NodeNotUpdatedException e) {
	    logger.error("Node " + nodeId + " not updated", e);
	    response = Response.status(Status.NOT_FOUND).build();
	} catch (NodeNotFoundException e) {
	    logger.error("Node " + nodeId + " not updated", e);
	    response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	return response;
    }

    @DELETE
    @Path("{node_id:.+}")
    public Response deleteNode(@PathParam("node_id") String nodeId) {

	logger.debug("Request to delete node");

	Response response;
	try {
	    this.npm.destroyNode(nodeId);
	    response = Response.status(Status.OK).build();
	    logger.info("Node " + nodeId + " deleted");
	} catch (NodeNotDestroyed e) {
	    logger.error("Nodenot destroyed", e);
	    response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
	} catch (NodeNotFoundException e) {
	    logger.error("Node not destroyed", e);
	    response = Response.status(Status.NOT_FOUND).build();
	}

	return response;
    }

}
