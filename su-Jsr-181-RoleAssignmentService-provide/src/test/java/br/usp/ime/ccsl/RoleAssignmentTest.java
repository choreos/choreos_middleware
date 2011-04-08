package br.usp.ime.ccsl;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.xml.ws.Endpoint;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class RoleAssignmentTest {
    private static RoleAssignment roleAssignment;
    private static Endpoint endpoint;
    
    @BeforeClass
    public static void initWebService() {
	roleAssignment = new RoleAssignment();
	 endpoint =
	     Endpoint.publish("http://localhost:8080/roleAssignment", roleAssignment);     
    }
    
    @Test
    public void testAssign(){
	final String uri = "uri";
	final String roleName = "rolename";
	
	roleAssignment.assignRole(uri, roleName);
	List<String> uris = roleAssignment.get(roleName);
	assertEquals(uri,uris.get(0));
    }
    
    @AfterClass
    public static void shutDownWebService() {
	try {
	    Thread.sleep(30000);
	} catch (InterruptedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	endpoint.stop();
    }
}
