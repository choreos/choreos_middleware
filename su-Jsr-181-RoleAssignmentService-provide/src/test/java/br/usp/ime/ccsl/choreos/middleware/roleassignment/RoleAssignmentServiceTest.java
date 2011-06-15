package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import static org.junit.Assert.assertEquals;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


public class RoleAssignmentServiceTest {
    private static RoleAssignmentServiceImpl roleAssignmentService;
    private static Endpoint endpoint;

    @BeforeClass
    public static void initWebService() {
        roleAssignmentService = new RoleAssignmentServiceImpl();
        endpoint = Endpoint.publish("http://localhost:60080/roleAssignment", roleAssignmentService);
    }

    @Test
    public void testAssign() {
        final String uri = "uri";
        final String roleName = "rolename";

        roleAssignmentService.saveRoleAssignment(new RoleAssignment(uri, roleName));
        ArrayOfStrings uris = roleAssignmentService.get(roleName);
        assertEquals(uri, uris.getUri().get(0));
    }

    @Test
    public void testAssignStr() {
        final String uri = "uri";
        final String roleName = "rolename";

        roleAssignmentService.assignRole(roleName, uri);
        ArrayOfStrings uris = roleAssignmentService.get(roleName);
        assertEquals(uri, uris.getUri().get(0));
    }

    @AfterClass
    public static void shutDownWebService() {
        endpoint.stop();
    }
}
