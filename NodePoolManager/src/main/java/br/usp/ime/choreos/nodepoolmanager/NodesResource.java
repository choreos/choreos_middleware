package br.usp.ime.choreos.nodepoolmanager;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

@Path("/nodes")
public class NodesResource {

    public static List<Node> nodes = new ArrayList<Node>();

    @GET
    public List<Node> getNodes() {
        return nodes;
    }

    @POST
    public Response createNode(Node node, @Context UriInfo uriInfo) throws URISyntaxException {
        node.setIp("fakeIp");
        node.setId((long) (Math.random() * 100000));
        nodes.add(node);
        UriBuilder uriBuilder = uriInfo.getBaseUriBuilder();
        uriBuilder.path(NodeResource.class);
        return Response.created(uriBuilder.build(node.getId())).build();
    }

}
