package org.ow2.choreos.deployment.services.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.RunList;

@Path("nodes/{node_id:.+}/runlist")
public class RunListResource {
    
    private String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
    private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));

    private Logger logger = Logger.getLogger(RunListResource.class);

    @GET
    @Produces("application/json")
    public Response getNodeRunlist(@PathParam("node_id") String nodeId) {

	logger.debug("Request to get run list from node " + nodeId);
	
	Response response;
	try {
	    CloudNode node = npm.getNode(nodeId);
	    RunList runList = node.getRunList();
	    String json = runList.toJson();
	    response = Response.ok(json).build();
	} catch (NodeNotFoundException e) {
	    logger.error("Node " + nodeId + " not found");
	    response = Response.status(Status.NOT_FOUND).build();
	}
	return response;
    }

}
