package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import static org.junit.Assert.assertEquals;

import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

public class PersistenceRoleAssignmentListTest {
    @Before
    public void cleanDB(){
        Session session = ChoreosSessionFactory.getSession();
        session.createSQLQuery("delete from RoleAssignment").executeUpdate();
        session.close();
    }
    
    @Test
    public void shouldPersistOneUriToARole() throws Exception {
        RoleManager roleManager = RoleManager.getInstance();
        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setRole("Role form persistence");
        roleAssignment.setUri("uri from persistence");
        roleManager.assignRole(roleAssignment);
        
        Session session = ChoreosSessionFactory.getSession();
        RoleAssignment roleAssignmentFromDB = (RoleAssignment) session.createQuery("from RoleAssignment").list().get(0);
        assertEquals(roleAssignment, roleAssignmentFromDB);
        session.close();
    }
}
