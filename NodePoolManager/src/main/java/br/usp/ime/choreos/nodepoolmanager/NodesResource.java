package br.usp.ime.choreos.nodepoolmanager;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

@Path("/nodes")
public class NodesResource {

    public static List<Node> nodes = new ArrayList<Node>();

    @GET
    public List<Node> getNodes() {
        return nodes;
    }

    @POST
    public Node createNode(Node node) {
        node.setIp("fakeIp");
        nodes.add(node);
        return node;
    }
}
