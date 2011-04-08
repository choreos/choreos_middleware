package br.usp.ime.ccsl.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Resource;
import br.usp.ime.ccsl.model.RoleAssignment;

@Resource
public class RoleAssignmentsController {

    private static List<RoleAssignment> list = new ArrayList<RoleAssignment>();

    public void form() {
	// code that loads data for checkboxes, selects, etc
    }

    public RoleAssignment add(RoleAssignment roleAssignment) {
	list.add(roleAssignment);
	System.out.println("adicionou " + roleAssignment.getUri() + " no role " + roleAssignment.getRole());
	return roleAssignment;
    }
    
    public List<RoleAssignment> list() {
	return list;
    }

}
