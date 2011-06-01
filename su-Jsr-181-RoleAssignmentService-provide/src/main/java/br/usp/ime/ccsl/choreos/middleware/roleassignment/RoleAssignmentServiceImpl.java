package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import javax.jws.WebService;
import javax.xml.ws.Endpoint;

import br.usp.ime.ccsl.choreos.middleware.roleassignmentservice.ArrayOfStrings;
import br.usp.ime.ccsl.choreos.middleware.roleassignmentservice.RoleAssignmentService;

@WebService(endpointInterface = "br.usp.ime.ccsl.choreos.middleware.roleassignmentservice.RoleAssignmentService")
public class RoleAssignmentServiceImpl implements RoleAssignmentService {

    public void assignRole(RoleAssignment roleAssignment) {
        RoleManager roleManager = RoleManager.getInstance();
        roleManager.assignRole(roleAssignment);
    }

    public String assignRole(String role, String uri) {
        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setRole(role);
        roleAssignment.setUri(uri);
        assignRole(roleAssignment);
        return "Created";
    }

    public ArrayOfStrings get(String roleName) {
        RoleManager roleManager = RoleManager.getInstance();
        ArrayOfStrings uris = new ArrayOfStrings();
        uris.setUri(roleManager.getUriList(roleName));

        return uris;
    }

    public static void main(String[] args) {
        // create and publish an endpoint
        RoleAssignmentServiceImpl calculator = new RoleAssignmentServiceImpl();
        Endpoint.publish("http://localhost:18080/roleAssignmentService", calculator);
    }

}
