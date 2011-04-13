package br.usp.ime.ccsl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class ChoreosEntityManager {

    private static EntityManagerFactory entityManagerFactory = Persistence
	    .createEntityManagerFactory("br.usp.ime.ccsl");

    public static EntityManager getInstance() {
	return entityManagerFactory.createEntityManager();
    }

}
