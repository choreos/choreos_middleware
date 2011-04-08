package br.usp.ime.ccsl;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

/**
 * Here is a sample JAX-WS implementation.
 * <p>
 * For more information about JAX-WS, please visit
 * <b>https://jax-ws.dev.java.net/jax-ws-ea3/docs/annotations.html</b>.
 * </p>
 */
@WebService(serviceName = "RoleAssignment", targetNamespace = "http://ccsl.ime.usp.br", portName = "RoleAssignmentPort")
public class RoleAssignment {

    @WebMethod(operationName = "assignRole")
    public void assignRole(@WebParam(name = "uri") String uri, @WebParam(name = "roleName") String roleName) {
	RoleManager roleManager = RoleManager.getInstance();
	roleManager.assignRole(uri, roleName);
    }

    @WebMethod(operationName = "get")
    public List<String> get(@WebParam(name = "roleName") String roleName) {
	RoleManager roleManager = RoleManager.getInstance();
	return roleManager.getUriList(roleName);
    }
    
    public static void main(String[] args){
        // create and publish an endpoint
        RoleAssignment calculator = new RoleAssignment();
        Endpoint endpoint = Endpoint.publish("http://localhost:8080/roleAssignment", calculator);        
    }

}
