package br.usp.ime.ccsl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;

public class RoleManagerTest {

    private static RoleManager roleManager;
    private String uri;
    private String uri1;
    private String uri2;
    private String roleName;
    private String roleName1;
    private String roleName2;

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
	assertEquals(0, roleManager.getUriList("rolename").size());
    }

    @Test
    public void testGetAssignedUriList() {
	uri = "uri";
	final String roleName = "rolename";

	roleManager.assignRole(uri, roleName);
	List<String> result = roleManager.getUriList(roleName);
	assertEquals(uri, result.get(0));
    }

    @Test
    public void testAssignRole() {
	roleManager.assignRole("uri2", "rolename2");
    }

    @Test
    public void testAssignTwoURIsSameRole() {
	uri1 = "uri1";
	uri2 = "uri2";
	roleName = "foo";

	roleManager.assignRole(uri1, roleName);
	roleManager.assignRole(uri2, roleName);

	List<String> uris = roleManager.getUriList(roleName);
	assertEquals(uri1, uris.get(0));
	assertEquals(uri2, uris.get(1));
    }

    @Test
    public void testTwoDifferentRoles() {
	uri1 = "uri1";
	uri2 = "uri2";
	roleName1 = "foo1";
	roleName2 = "foo2";

	roleManager.assignRole(uri1, roleName1);
	roleManager.assignRole(uri2, roleName2);

	List<String> uris1 = roleManager.getUriList(roleName1);
	List<String> uris2 = roleManager.getUriList(roleName2);

	assertEquals(uri1, uris1.get(0));
	assertEquals(uri2, uris2.get(0));
    }

}
