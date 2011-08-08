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

    @GET
    public List<Node> getNodes() {
        InfrastructureService infrastructure = new InfrastructureService();
        return infrastructure.getNodes();
    }

    @POST
    public Response createNode(Node node, @Context UriInfo uriInfo) throws URISyntaxException {
        Response response;
        InfrastructureService infrastructure = new InfrastructureService();
        System.out.println("NodesResource createNode: " + node.getImage());

        try {
            infrastructure.create(node);
            nodes.add(node);
            UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
            uriBuilder.path(NodeResource.class);
            response = Response.created(uriBuilder.build(node.getId())).build();
            System.out.println("NodesResource.createNode created " + node.getId());
        } catch (RunNodesException e) {
            response = Response.status(Status.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }
}