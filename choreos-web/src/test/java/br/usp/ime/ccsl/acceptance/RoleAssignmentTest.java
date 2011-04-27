package br.usp.ime.ccsl.acceptance;

import net.sourceforge.jwebunit.junit.WebTestCase;

import org.junit.Test;

public class RoleAssignmentTest extends WebTestCase {
    
    
    @Override
    public void setUp() throws Exception {
	super.setUp();
	setBaseUrl("http://localhost:8089/choreos-web");
    }

    @Test
    public void testCreateAndListRoleAssignments() {
	beginAt("/roleAssignments/form");

	clickLink("add_role_assignment");

	setTextField("roleAssignment.role", "myRole");
	setTextField("roleAssignment.uri", "myURI");
	submit();

	assertTextPresent("Service myURI registered with role myRole");

	clickLink("list_role_assignments");

	assertTextPresent("myRole");
	assertTextPresent("myURI");
    }
}
