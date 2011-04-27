package br.usp.ime.ccsl.roleAssignment;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RoleAssignment implements Serializable {

    private static final long serialVersionUID = 485321587419877511L;

    @Id
    @GeneratedValue
    private Long id;

    private String uri;
    private String role;

    public RoleAssignment() {

    }

    public RoleAssignment(String uri, String role) {
	super();
	this.uri = uri;
	this.role = role;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
    }

    public String getUri() {
	return uri;
    }

    public void setUri(String uri) {
	this.uri = uri;
    }

    public String getRole() {
	return role;
    }

    public void setRole(String role) {
	this.role = role;
    }
}
