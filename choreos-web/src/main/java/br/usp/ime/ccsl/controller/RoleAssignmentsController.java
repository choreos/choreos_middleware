package br.usp.ime.ccsl.controller;

import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Validator;
import br.com.caelum.vraptor.validator.ValidationMessage;
import br.usp.ime.ccsl.dao.RoleAssignmentDAO;
import br.usp.ime.ccsl.model.RoleAssignment;

@Resource
public class RoleAssignmentsController {

    private static List<RoleAssignment> list = new ArrayList<RoleAssignment>();

    private Validator validator;

    private RoleAssignmentDAO roleAssignmentDAO;

    public RoleAssignmentsController(Validator validator) {
        this.validator = validator;
    }

    public void form() {
        // code that loads data for checkboxes, selects, etc
    }

    public RoleAssignment add(RoleAssignment roleAssignment) {
        validate(roleAssignment);
        roleAssignmentDAO.add(roleAssignment);
        return roleAssignment;
    }

    private void validate(RoleAssignment roleAssignment) {
        if (roleAssignment.getUri().isEmpty()) {
            validator.add(new ValidationMessage("you must provide an URI.", "Empty field"));
        }
        if (roleAssignment.getRole().isEmpty()) {
            validator.add(new ValidationMessage("you must provide a role.", "Empty field"));
        }
        validator.onErrorUsePageOf(RoleAssignmentsController.class).form();
    }

    // TODO extract paths to RoutesConfiguration:
    // http://vraptor.caelum.com.br/documentacao/resources-rest/
    @Path( { "/roleAssignments/", "/roleAssignments/list" })
    public List<RoleAssignment> list() {
        return roleAssignmentDAO.list();
    }

    public void setRoleAssignmentDAO(RoleAssignmentDAO roleAssignmentDAO) {
        this.roleAssignmentDAO = roleAssignmentDAO;

    }
}