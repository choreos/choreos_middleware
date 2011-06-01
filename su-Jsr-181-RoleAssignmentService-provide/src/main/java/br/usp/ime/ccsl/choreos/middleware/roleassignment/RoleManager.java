package br.usp.ime.ccsl.choreos.middleware.roleassignment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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

    public void assignRole(RoleAssignment roleAssigment) {
        List<String> uriList = uriRole.get(roleAssigment.getRole());
        if (uriList == null) {
            uriList = new ArrayList<String>();
            List<String> threadSafeUriList = Collections.synchronizedList(uriList);
            uriRole.put(roleAssigment.getRole(), threadSafeUriList);
        }
        uriList.add(roleAssigment.getUri());
    }

    public List<String> getUriList(String roleName) {
        List<String> uriList = uriRole.get(roleName);
        if (uriList == null)
            uriList = new ArrayList<String>();
        return uriList;
    }
}
