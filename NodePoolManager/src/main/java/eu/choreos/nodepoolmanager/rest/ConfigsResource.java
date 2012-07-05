package eu.choreos.nodepoolmanager.rest;

import java.net.URI;
import java.net.URISyntaxException;

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

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.Controller;
import eu.choreos.nodepoolmanager.cloudprovider.CloudProviderFactory;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.datamodel.NodeRestRepresentation;

@Path("nodes/configs")
public class ConfigsResource {

	private Logger logger = Logger.getLogger(ConfigsResource.class);
	private String cloudProviderType = Configuration.get("CLOUD_PROVIDER");
	private Controller controller = new Controller(CloudProviderFactory.getInstance(cloudProviderType));
	
	/**
	 * Updates node configuration. 
	 * To apply the configuration, post to nodes/upgrade.
	 * 
	 * @param config <code>config.name</code> corresponds to the chef recipe to be deployed
	 * @param uriInfo
	 * @return Response with location = id of the node where the config is going to be applied, 
	 * 			and the body contains the representation of such node
	 * @throws URISyntaxException
	 */
    @POST
    @Consumes(MediaType.APPLICATION_XML)
    public Response applyConfig(Config config, @Context UriInfo uriInfo) throws URISyntaxException {
        
    	logger.debug("Request to apply " + config.getName());
    	
		if (config == null || config.getName() == null || config.getName().isEmpty())
			return Response.status(Status.BAD_REQUEST).build();
		
    	Node node = controller.applyConfig(config);
    	
    	if (node == null)  // config not applied!
    		return Response.status(Status.INTERNAL_SERVER_ERROR).build();

		logger.info(config.getName() + " applied on " + node.getIp());

		UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
		uriBuilder = uriBuilder.path(NodesResource.class).path(node.getId());
		URI uri = uriBuilder.build();
		NodeRestRepresentation nodeRest = new NodeRestRepresentation(node);
    	return Response.created(uri).entity(nodeRest).build();
    }
}
