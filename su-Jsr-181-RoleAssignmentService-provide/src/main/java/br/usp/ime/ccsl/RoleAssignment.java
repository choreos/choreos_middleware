package br.usp.ime.ccsl;

public class RoleAssignment {
    private String uri;
    private String role;

    public RoleAssignment(String uri, String role) {
	super();
	this.uri = uri;
	this.role = role;
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
