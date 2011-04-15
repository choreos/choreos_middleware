package br.usp.ime.ccsl;

import static org.junit.Assert.assertNotNull;

import org.hibernate.Session;
import org.junit.Test;

public class ChoreosSessionFactoryTest {

    @Test
    public void getEntityManager() throws Exception {
	Session s = ChoreosSessionFactory.getSession();
	assertNotNull(s);
    }

    @Test
    public void persistAnObject() throws Exception {
	Session s = ChoreosSessionFactory.getSession();
	RoleAssignment ra = new RoleAssignment("uri", "role");
	s.save(ra);
	RoleAssignment ra1 = (RoleAssignment) s.get(RoleAssignment.class, ra.getId());
	assertNotNull(ra1);
    }
}
