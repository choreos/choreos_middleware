
package br.usp.ime.ccsl.choreos.middleware.roleassignmentservice;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the br.usp.ime.ccsl.choreos.middleware.roleassignmentservice package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: br.usp.ime.ccsl.choreos.middleware.roleassignmentservice
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AssignRole }
     * 
     */
    public AssignRole createAssignRole() {
        return new AssignRole();
    }

    /**
     * Create an instance of {@link AssignRoleResponse }
     * 
     */
    public AssignRoleResponse createAssignRoleResponse() {
        return new AssignRoleResponse();
    }

    /**
     * Create an instance of {@link ArrayOfStrings }
     * 
     */
    public ArrayOfStrings createArrayOfStrings() {
        return new ArrayOfStrings();
    }

}
