package br.usp.ime.choreos.nodepoolmanager;

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
}
