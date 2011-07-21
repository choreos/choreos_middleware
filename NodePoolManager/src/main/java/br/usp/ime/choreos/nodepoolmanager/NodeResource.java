package br.usp.ime.choreos.nodepoolmanager;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/nodes/{node_id}")
public class NodeResource {

    @GET
    public Response getNode(@PathParam("node_id") Long id) {
        for (Node n : NodesResource.nodes) {
            if (n.getId() == id) {
                return Response.ok(n).build();
            }
        }
        return Response.status(Status.NOT_FOUND).build();
    }

    @DELETE
    public Response deleteNode(@PathParam("node_id") Long id) {
        List<Node> toDelete = new ArrayList<Node>();

        for (Node n : NodesResource.nodes) {
            if (n.getId() == id) {
                toDelete.add(n);
            }
        }

        NodesResource.nodes.removeAll(toDelete);
        return Response.status(Status.OK).build();
    }

}
