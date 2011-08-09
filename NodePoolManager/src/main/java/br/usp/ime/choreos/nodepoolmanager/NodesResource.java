package br.usp.ime.choreos.nodepoolmanager;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import org.jclouds.compute.RunNodesException;

@Path("/nodes")
public class NodesResource {

    public static List<Node> nodes = new ArrayList<Node>();

    private InfrastructureService infrastructure = new InfrastructureService();

    public void setInfrastructure(InfrastructureService infrastructure) {
        this.infrastructure = infrastructure;
    }

    @GET
    public List<Node> getNodes() {
        return infrastructure.getNodes();
    }

    @POST
    public Response createNode(Node node, @Context UriInfo uriInfo) throws URISyntaxException {
        Response response;

        try {
            infrastructure.createNode(node);
            nodes.add(node);
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
            uriBuilder.path(NodeResource.class);
            response = Response.created(uriBuilder.build(node.getId())).build();
        } catch (RunNodesException e) {
            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }
}