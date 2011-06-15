package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.hibernate.Query;
import org.hibernate.Session;

public class RoleManager {

    private static RoleManager instance;

    ConcurrentMap<String, List<String>> uriRole;

    private RoleManager() {
        uriRole = new ConcurrentHashMap<String, List<String>>();
    }

    public static RoleManager getInstance() {
        if (instance == null) {
            instance = new RoleManager();
        }
        return instance;
    }

    public void assignRole(RoleAssignment roleAssignment) {
        Session session = ChoreosSessionFactory.getSession();
        session.save(roleAssignment);
        session.close();
    }

    @SuppressWarnings("unchecked")
    public List<String> getUriList(String roleName) {
        Session session = ChoreosSessionFactory.getSession();
        Query query = session.createQuery("from RoleAssignment ra where ra.role like :roleName").setString("roleName", roleName);
        List<RoleAssignment> roleAssigments = query.list();
        
        List<String> uriList = new ArrayList<String>();
        
        for (RoleAssignment roleAssignment : roleAssigments) {
            uriList.add(roleAssignment.getUri());
        }
        session.close();
        
        return uriList;
    }
}
