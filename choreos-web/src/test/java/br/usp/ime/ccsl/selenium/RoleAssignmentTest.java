package br.usp.ime.ccsl.selenium;

import static br.usp.ime.ccsl.selenium.SeleniumUtil.assertTextPresent;

import org.junit.Test;

import com.thoughtworks.selenium.DefaultSelenium;

public class RoleAssignmentTest {
    
    private static DefaultSelenium client = SeleniumUtil.startSelenium();
    
    @Test
    public void testFormPresence() {
	client.open("http://localhost:8080/choreos-web/roleAssignments/form");
	assertTextPresent("Role:");
	assertTextPresent("URI:");
    }
}
