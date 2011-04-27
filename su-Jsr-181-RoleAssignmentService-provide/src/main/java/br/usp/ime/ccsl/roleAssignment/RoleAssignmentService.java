package br.usp.ime.ccsl.roleAssignment;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService(serviceName = "RoleAssignmentService", targetNamespace = "http://ccsl.ime.usp.br", portName = "RoleAssignmentServicePort")
public class RoleAssignmentService {

    @WebMethod(operationName = "assignRole")
    public void assignRole(@WebParam(name = "roleAssignment") RoleAssignment roleAssignment) {
	RoleManager roleManager = RoleManager.getInstance();
	roleManager.assignRole(roleAssignment);
    }

    @WebMethod(operationName = "get")
    public List<String> get(@WebParam(name = "roleName") String roleName) {
	RoleManager roleManager = RoleManager.getInstance();
	return roleManager.getUriList(roleName);
    }

    public static void main(String[] args) {
	// create and publish an endpoint
	RoleAssignmentService calculator = new RoleAssignmentService();
	Endpoint.publish("http://localhost:18080/roleAssignmentService", calculator);
    }

}
