package br.usp.ime.choreos.nodepoolmanager;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

@Path("/nodes/{node_id}")
public class NodeResource {

    @GET
    public Node getNode(@PathParam("node_id") Long id) {
        for (Node n : NodesResource.nodes) {
            if (n.getId() == id) {
                return n;
            }
        }
        return null;
    }

    @DELETE
    public void deleteNode(@PathParam("node_id") Long id) {
        List<Node> toDelete = new ArrayList<Node>();

        for (Node n : NodesResource.nodes) {
            if (n.getId() == id) {
                toDelete.add(n);
            }
        }

        NodesResource.nodes.removeAll(toDelete);
    }

}
