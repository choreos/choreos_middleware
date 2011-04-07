package br.usp.ime.ccsl;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import br.usp.ime.ccsl.RoleManager;


public class RoleManagerTest {

	private static RoleManager roleManager;

	@Test
	public void testGetInstanceNotNull() {
		roleManager = RoleManager.getInstance();
		assertNotNull(roleManager);
	}
	
	@Test
	public void testGetInstanceSingleton() {
		assertEquals(roleManager, RoleManager.getInstance());
	}
	
	@Test
	public void testGetUriListIsEmptyAtFirst() {
		assertEquals(0, roleManager.getUriList("rolename0").size());
	}
	
	@Test
	public void testGetAssignedUriList() {
		final String uri = "uri1";
		final String roleName = "rolename1";
		roleManager.assignRole(uri, roleName);
		List<String> result = roleManager.getUriList(roleName);
		assertEquals(uri, result.get(0));
	}
	
	@Test
	public void testAssignRole() {
		roleManager.assignRole("uri2", "rolename2");
	}
}
