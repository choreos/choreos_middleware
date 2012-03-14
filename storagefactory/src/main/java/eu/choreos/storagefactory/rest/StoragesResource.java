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
import eu.choreos.storagefactory.utils.NodePoolManager;
import eu.choreos.storagefactory.utils.SimpleNodePoolManagerHandler;

/**
 * Storage factory REST API resource: storages
 * 
 * @author leonardo, alfonso
 * 
 */
@Path("/storagefactory")
public class StoragesResource {

	//StorageNodeManager storageManager = new StorageNodeManager(new SimpleNodePoolManagerHandler());
	StorageNodeManager storageManager = new StorageNodeManager(new NodePoolManager());

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
		
		StorageNode node=null;
		try{
			node = storageManager.createNewStorageNode(spec);
			if(node ==null)
				return Response.status(Status.NOT_FOUND).build();
			
		}catch(Exception e){
			System.out.println("** A Exception when invoking StorageFactory REST API ... ");
			System.out.println(e.getMessage());
			return Response.serverError().build();
		}
		
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

		StorageNode node = null;
		try {
			node = storageManager.registry.getNode(uuid);
			return node;
		} catch (Exception e) {
			// TODO se não tiver storage, retorna erro
			return null;
		}
	}

	/**
	 * Deletes a storage node
	 * 
	 * @param uuid
	 *            the user identifier
	 * @param id
	 *            the storage node identifier
	 */
	@DELETE
	@Path("/storages/{uuid}")
	public void deleteStorage(@PathParam("uuid") String uuid) {

		System.out.println("deleting " + uuid);
		// TODO se storage node não existir, retornar erro
	}
}
