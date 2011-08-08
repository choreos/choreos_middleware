package br.usp.ime.choreos.nodepoolmanager;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

@Path("/nodes/{node_id:.+}")
public class NodeResource {

    @GET
    public Response getNode(@PathParam("node_id") String id) {
        System.out.println("NodeResource getNode " + id);
        InfrastructureService infrastructure = new InfrastructureService();
        Node node;
        Response response;

        try {
            node = infrastructure.getNode(id);
            response = Response.ok(node).build();
        } catch (NodeNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            response = Response.status(Status.NOT_FOUND).build();
        }

        return response;
    }

    @DELETE
    public Response deleteNode(@PathParam("node_id") String id) {
        InfrastructureService infrastructure = new InfrastructureService();
        infrastructure.unDeploy(id);

        return Response.status(Status.OK).build();
    }
}