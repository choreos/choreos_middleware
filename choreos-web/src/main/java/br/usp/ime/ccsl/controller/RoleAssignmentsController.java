package br.usp.ime.ccsl.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.usp.ime.ccsl.model.RoleAssignment;

@Resource
public class RoleAssignmentsController {

    public void form() {
        // code that loads data for checkboxes, selects, etc
    }

    
    public void add(RoleAssignment roleAssignment){
	System.out.println("adicionou " + roleAssignment.getUri() + " no role " + roleAssignment.getRole());
    }
    
    public List<RoleAssignment> list() {
        return new ArrayList<RoleAssignment>();
    }

}
