package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;


@WebService(targetNamespace = "http://roleassignment.middleware.choreos.ccsl.ime.usp.br/RoleAssignmentService", name = "RoleAssignmentService")
public class RoleAssignmentServiceImpl {

    public void saveRoleAssignment(RoleAssignment roleAssignment) {
        RoleManager roleManager = RoleManager.getInstance();
        roleManager.assignRole(roleAssignment);
    }

    @WebResult(name = "responseMessage", targetNamespace = "", partName = "responseMessage")
    @WebMethod
    public java.lang.String assignRole(
            @WebParam(partName = "role", name = "role", targetNamespace = "") java.lang.String role,
            @WebParam(partName = "uri", name = "uri", targetNamespace = "") java.lang.String uri) {
        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setRole(role);
        roleAssignment.setUri(uri);
        saveRoleAssignment(roleAssignment);
        return "Created";
    }

    @WebResult(name = "uris", targetNamespace = "", partName = "uris")
    @WebMethod
    public ArrayOfStrings get(
            @WebParam(partName = "roleName", name = "roleName", targetNamespace = "") java.lang.String roleName) {
        RoleManager roleManager = RoleManager.getInstance();
        ArrayOfStrings uris = new ArrayOfStrings();
        uris.setUri(roleManager.getUriList(roleName));
        return uris;
    }

    public static void main(String[] args) {
        // create and publish an endpointx
        RoleAssignmentServiceImpl calculator = new RoleAssignmentServiceImpl();
        Endpoint.publish("http://localhost:18080/roleAssignmentService", calculator);

    }

}
