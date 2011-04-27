package br.usp.ime.ccsl.roleAssignment;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class ChoreosSessionFactory {

    private static SessionFactory sessionFactory;

    static {
	sessionFactory = new AnnotationConfiguration().configure().buildSessionFactory();
    }

    public static Session getSession() {
	return sessionFactory.openSession();
    }

}
