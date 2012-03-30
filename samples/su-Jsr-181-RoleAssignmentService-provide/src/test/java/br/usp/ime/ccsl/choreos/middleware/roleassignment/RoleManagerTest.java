package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class RoleManagerTest {

    private RoleManager roleManager;

    @Before
    public void setUp() {
        roleManager = RoleManager.getInstance();
    }

    @Before
    public void cleanDB(){
        ChoreosSessionFactory.getSession().createSQLQuery("delete from RoleAssignment").executeUpdate();
    }

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
    public void testGetAssignedUriList() {
        roleManager.assignRole(new RoleAssignment("uri", "rolename"));
        List<String> result = roleManager.getUriList("rolename");
        assertEquals("uri", result.get(0));
    }

    @Test
    public void testAssignTwoURIsSameRole() {
        roleManager.assignRole(new RoleAssignment("uri1", "roleName"));
        roleManager.assignRole(new RoleAssignment("uri2", "roleName"));

        List<String> uris = roleManager.getUriList("roleName");
        assertEquals("uri1", uris.get(0));
        assertEquals("uri2", uris.get(1));
    }

    @Test
    public void testTwoDifferentRoles() {
        roleManager.assignRole(new RoleAssignment("uri1", "roleName1"));
        roleManager.assignRole(new RoleAssignment("uri2", "roleName2"));

        List<String> uris1 = roleManager.getUriList("roleName1");
        List<String> uris2 = roleManager.getUriList("roleName2");

        assertEquals("uri1", uris1.get(0));
        assertEquals("uri2", uris2.get(0));
    }

}
