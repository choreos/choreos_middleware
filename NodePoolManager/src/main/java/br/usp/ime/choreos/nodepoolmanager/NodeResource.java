package br.usp.ime.choreos.nodepoolmanager;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.usp.ime.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;

@Path("/nodes/{node_id:.+}")
public class NodeResource {

    @GET
    public Response getNode(@PathParam("node_id") String id) {
        Response response;
        CloudProvider infrastructure = new AWSCloudProvider();

        try {
            NodeRestRepresentation node = infrastructure.getNode(id).getRestRepresentation();
            response = Response.ok(node).build();
        } catch (NodeNotFoundException e) {
            response = Response.status(Status.NOT_FOUND).build();
        }

        return response;
    }

    @DELETE
    public Response deleteNode(@PathParam("node_id") String id) {
    	CloudProvider infrastructure = new AWSCloudProvider();
        infrastructure.destroyNode(id);

        return Response.status(Status.OK).build();
    }
}