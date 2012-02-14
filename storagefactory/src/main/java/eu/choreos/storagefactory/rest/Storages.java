package eu.choreos.storagefactory.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.JAXBElement;

import eu.choreos.storagefactory.datamodel.Database;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.datamodel.StorageNodes;

/**
 * Storage factory REST API
 * resource: storages
 * 
 * @author leonardo, alfonso
 *
 */
@Path("/storagefactory")
public class Storages {

	/**
	 * Client requests a storage
	 * 
	 * @param specXml
	 * @return
	 */
	@POST
	@Path("/storages")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public Response requestStorage(JAXBElement<StorageNodeSpec> specXml) {
		
		StorageNodeSpec spec = specXml.getValue();
		
		// condition
		if (spec.getType() == null && spec.getUserID() == null)
			return Response.status(Status.BAD_REQUEST).build();
		
		// TODO trocar bloco abaixo para o que precisamos fazer
			StorageNode node = new StorageNode();
			node.setId(spec.getUserID() + "StorageNode");
			node.setStorageNodeSpec(spec);
			Database db = new Database();
			db.setUri("localhost");
			db.setUser(spec.getUserID());
			db.setPassword("123mudar");
			node.setDatabase(db);
		
		return Response.ok(node).build();
	}
	
	/**
	 * 
	 * @return the storages used by an user
	 */
	@GET
	@Path("/storages/{userID}")
	@Produces(MediaType.APPLICATION_XML)
	public StorageNodes getUserNodes(@PathParam("userID") String userID) {
		
		// TODO trocar bloco abaixo para o que precisamos fazer
			List<StorageNode> list = new ArrayList<StorageNode>();
			StorageNode node = new StorageNode();
			node.setId(userID + "StorageNode");
			Database db = new Database();
			db.setUri("localhost");
			db.setPassword("123mudar");
			node.setDatabase(db);		
			list.add(node);
			StorageNodes nodes= new StorageNodes();
			nodes.setNodes(list);
		
		return nodes;
	}
	
	/**
	 * 
	 * @return the storage node of a group defined by a correlation ID
	 */
	@GET
	@Path("/correlations/{correlationID}/storage")
	@Produces(MediaType.APPLICATION_XML)
	public StorageNode getCorrelationNode(@PathParam("correlationID") String correlationID) {
		
		// TODO trocar bloco abaixo para o que precisamos fazer
			StorageNode node = new StorageNode();
			node.setId(correlationID + "StorageNode");
			Database db = new Database();
			db.setUri("localhost");
			db.setPassword("123mudar");
			node.setDatabase(db);
		
		// TODO se não tiver storage, retorna erro
		return node;
	}
	
	/**
	 * Deletes a storage node
	 * 
	 * @param userID the user identifier
	 * @param id the storage node identifier
	 */
	@DELETE
	@Path("/storages/{userID}/{id}")
	public void deleteStorage(@PathParam("userID") String userID, @PathParam("id") String id) {
		
		System.out.println("userID=" + userID + "; id=" + id);
		// TODO se storage node não existir, retornar erro
	}
	
	
	
}
