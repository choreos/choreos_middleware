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

import eu.choreos.nodepoolmanager.Controller;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;

@Path("nodes/configs")
public class ConfigsResource {

	private Logger logger = Logger.getLogger(ConfigsResource.class);
	private Controller controller = new Controller(new AWSCloudProvider());
	
	/**
	 * Updates node cookbook list. To apply, post to nodes/upgrade.
	 * @param config
	 * @param uriInfo
	 * @return
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
    	return Response.created(uri).build();
    }
}
