package eu.choreos.ServiceDeployer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBElement;

import eu.choreos.ServiceDeployer.datamodel.ServiceNode;
import eu.choreos.ServiceDeployer.datamodel.ServiceNodes;

/**
 * Service Deployer REST API
 * resource: services
 * 
 * @author alfonso
 *
 */
@Path("/serviceDeployer")
public class Services {

	/**
	 * Client requests a serviceNode (new or already created) 
	 * @param serviceNodeXML Service request XML
	 * @return A new service (or service already created) according to specifications.  
	 */
	@POST
	@Path("/services")
	@Consumes(MediaType.APPLICATION_XML)
	@Produces(MediaType.APPLICATION_XML)
	public ServiceNode requestService(JAXBElement<ServiceNode> serviceNodeXML) {
		ServiceNode serviceNode = serviceNodeXML.getValue();
		
		// TODO trocar bloco abaixo para o que precisamos fazer
		serviceNode.setId("12345");
		serviceNode.setCorrelationId("7654321");
		return serviceNode;
	}
	
	/**
	 * Client requests a serviceNode by ID
	 * @param serviceID of required service node
	 * @return a service node found
	 */
	@GET
	@Path("/services/{serviceID}")
	@Produces(MediaType.APPLICATION_XML)
	public ServiceNode getServiceNodes(@PathParam("serviceID") String serviceID){
		
		// TODO trocar bloco abaixo para o que precisamos fazer
		ServiceNode serviceNode = new ServiceNode();
		serviceNode.setId(serviceID+"007");
		serviceNode.setType("bpel");
		serviceNode.setCodeLocation("URI");
		
//		List<ServiceNode> nodes = new ArrayList<ServiceNode>();
//		nodes.add(serviceNode);
//		ServiceNodes serviceNodes = new ServiceNodes();
//		serviceNodes.setNodes(nodes);
		
		return serviceNode;
	}

	/**
	 * Client requests update specifications of a service node
	 * @param serviceNodeXML: new specifications in order to update 
	 */
	@PUT
	@Path("/services/")
	@Consumes(MediaType.APPLICATION_XML)
	public void updateService(JAXBElement<ServiceNode> serviceNodeXML) {
		//TODO
	}
	
	
	/**
	 * Client requests remove a service node by ID	
	 * @param serviceID
	 */
	@DELETE
	@Path("/services/{serviceID}")
	public void deleteServiceNode(@PathParam("serviceID") String serviceID) {
		
		System.out.println("serviceID=" + serviceID);
		// TODO se storage node não existir, retornar erro
	}

}
