/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
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
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.PrepareDeploymentFailedException;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.CloudNode;

@Path("nodes/configs")
public class ConfigsResource {

    private Logger logger = Logger.getLogger(ConfigsResource.class);
    private String cloudProviderType = DeploymentManagerConfiguration.get("CLOUD_PROVIDER");
    private NodePoolManager npm = new NPMImpl(CloudProviderFactory.getInstance(cloudProviderType));

    /**
     * Updates node configuration. To apply the configuration, use POST
     * node/{id}/upgrade, with the node id returned by this operation.
     * 
     * Body: Config representation. config.name corresponds to the chef recipe
     * to be deployed
     * 
     * @param config
     *            config representation provided in the Body
     * @param uriInfo
     * @return Response with location = id of the node where the config is going
     *         to be applied, and the body contains the representation of such
     *         node
     * @return HTTP code 200 (OK). Body: representation of the node where the
     *         config is going to be applied Location header: the URI of the
     *         node where the config is going be applied. The URI contains the
     *         node ID. HTTP code 404 (NOT_FOUND) if config representation is
     *         not properly provided. HTTP code 500 (INTERNAL_SERVER_ERROR) if
     *         config application was not possible.
     * @throws URISyntaxException
     */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response applyConfig(DeploymentRequest config, @Context UriInfo uriInfo) throws URISyntaxException {

	logger.debug("Request to apply " + config.getService().toString());

	if (config == null || config.getService() == null)
	    return Response.status(Status.BAD_REQUEST).build();

	List<CloudNode> nodes;
	try {
	    nodes = npm.prepareDeployment(config);
	} catch (PrepareDeploymentFailedException e) {
	    logger.error("Config not applied", e);
	    return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	// logger.info(config.getName() + " applied on " + node.getIp());

	// TODO: how to return all the nodes?

	CloudNode node = nodes.get(0);
	UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
	uriBuilder = uriBuilder.path(NodesResource.class).path(node.getId());
	URI uri = uriBuilder.build();
	return Response.created(uri).entity(node).build();
    }
}
