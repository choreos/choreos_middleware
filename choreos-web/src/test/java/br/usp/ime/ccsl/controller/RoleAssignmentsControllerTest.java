package br.usp.ime.ccsl.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import br.com.caelum.vraptor.util.test.MockValidator;
import br.usp.ime.ccsl.dao.RoleAssignmentDAO;
import br.usp.ime.ccsl.model.RoleAssignment;

public class RoleAssignmentsControllerTest {

    @Test
    public void list() throws Exception {
        RoleAssignmentsController roleAssignmentsController = new RoleAssignmentsController(null);
        RoleAssignmentDAO roleAssignmentDAO = mock(RoleAssignmentDAO.class);
        roleAssignmentsController.setRoleAssignmentDAO(roleAssignmentDAO);
        List<RoleAssignment> expected = Arrays.asList(new RoleAssignment());

        when(roleAssignmentDAO.list()).thenReturn(expected);

        assertEquals(expected, roleAssignmentsController.list());
    }

    @Test
    public void add() throws Exception {
        RoleAssignmentsController roleAssignmentsController = new RoleAssignmentsController(new MockValidator());
        RoleAssignmentDAO roleAssignmentDAO = mock(RoleAssignmentDAO.class);
        roleAssignmentsController.setRoleAssignmentDAO(roleAssignmentDAO);
        RoleAssignment roleAssignment = new RoleAssignment();
        roleAssignment.setUri("uri");
        roleAssignment.setRole("role");

        roleAssignmentsController.add(roleAssignment);

        verify(roleAssignmentDAO).add(roleAssignment);
    }
}
