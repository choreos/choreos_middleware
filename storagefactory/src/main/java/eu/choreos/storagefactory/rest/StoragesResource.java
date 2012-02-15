package eu.choreos.storagefactory.rest;

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

import eu.choreos.storagefactory.StorageNodeManager;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;

/**
 * Storage factory REST API
 * resource: storages
 * 
 * @author leonardo, alfonso
 *
 */
@Path("/storagefactory")
public class StoragesResource {

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
		if (spec.getType() == null && spec.getUuid() == null)
			return Response.status(Status.BAD_REQUEST).build();
		
			StorageNodeManager nodeManager = new StorageNodeManager();
			StorageNode node = nodeManager.createNewStorageNode(spec);
			
		return Response.ok(node).build();
	}
	
	/**
	 * 
	 * @return the storage node of a group defined by a correlation ID
	 */
	@GET
	@Path("/storages/{uuid}")
	@Produces(MediaType.APPLICATION_XML)
	public StorageNode getCorrelationNode(@PathParam("uuid") String uuid) {
		
		// TODO trocar bloco abaixo para o que precisamos fazer
			StorageNode node = new StorageNode();
			node.setUuid(uuid);
			node.setPassword("123mudar");
			node.setSchema(uuid);
			node.setType("MySQL");
			node.setUri("localhost");
			node.setUser("uuid");
		
		// TODO se não tiver storage, retorna erro
		return node;
	}
	
	/**
	 * Deletes a storage node
	 * 
	 * @param uuid the user identifier
	 * @param id the storage node identifier
	 */
	@DELETE
	@Path("/storages/{uuid}")
	public void deleteStorage(@PathParam("uuid") String uuid) {
		
		System.out.println("deleting " + uuid);
		// TODO se storage node não existir, retornar erro
	}
	
	
	
}
