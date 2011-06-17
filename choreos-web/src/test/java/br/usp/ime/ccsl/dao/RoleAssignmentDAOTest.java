package br.usp.ime.ccsl.dao;

import static org.junit.Assert.*;

import org.junit.Test;

import br.usp.ime.ccsl.model.RoleAssignment;


public class RoleAssignmentDAOTest {
@Test
public void Add() throws Exception {
    RoleAssignmentDAO roleAssignmentDAO = new RoleAssignmentDAO();
    RoleAssignment roleAssignment = new RoleAssignment();
    roleAssignmentDAO.add(roleAssignment);
}
    
}
