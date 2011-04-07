package br.usp.ime.ccsl;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Here is a sample JAX-WS implementation.
 * <p>
 * For more information about JAX-WS, please visit 
 * <b>https://jax-ws.dev.java.net/jax-ws-ea3/docs/annotations.html</b>.
 * </p>
 */
@WebService( serviceName="RoleAssignment", targetNamespace="http://ccsl.ime.usp.br", portName="RoleAssignmentPort" )
public class RoleAssignment {

	@WebMethod(operationName="assignRole")
	@WebResult(name="result")
	public int assignRole(@WebParam(name="uri") String uri, @WebParam(name="roleName") String roleName) {
		RoleManager roleManager = RoleManager.getInstance();
		roleManager.assignRole(uri, roleName);
		return 0;
	}
}
