package br.usp.ime.ccsl.model;

import javax.persistence.Entity;

@Entity
public class RoleAssignment {
    private String uri;
    private String role;
    
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
