package br.usp.ime.ccsl;

import static org.junit.Assert.assertNotNull;

import javax.persistence.EntityManager;

import org.junit.Test;

public class ChoreosEntityManagerTest {

    @Test
    public void getEntityManager() throws Exception {
	EntityManager em = ChoreosEntityManager.getInstance();
	assertNotNull(em);
    }

    @Test
    public void persistAnObject() throws Exception {
	EntityManager em = ChoreosEntityManager.getInstance();
	em.getTransaction().begin();
	// RoleAssignment ra = new RoleAssignment("uri", "role");
	// em.persist(ra);

	Object result = em.createQuery("from RoleAssignment").getSingleResult();
	assertNotNull(result);
	em.close();

    }
}
