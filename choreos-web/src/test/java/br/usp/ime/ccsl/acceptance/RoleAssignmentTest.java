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
    public void testFormPresence() {
	beginAt("/roleAssignments/form");
	assertTextPresent("Role:");
	assertTextPresent("URI:");
    }
}
