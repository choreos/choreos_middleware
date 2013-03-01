package org.ow2.choreos.deployment.services.bus;

import com.ebmwebsourcing.esstar.management.UserManagementClientSOAP;

import esstar.petalslink.com.service.management._1_0.ManagementException;

/**
 * Access an EasyESB node. 
 * 
 * @author leonardo
 *
 */
public class EasyESBNodeImpl implements EasyESBNode {

	private final String adminEndpoint;
	
	static {
		EasyAPILoader.loadEasyAPI();
	}
	
	public EasyESBNodeImpl(String adminEndpoint) {
		this.adminEndpoint = adminEndpoint;
	}
	
	@Override
	public String getAdminEndpoint() {
		return this.adminEndpoint;
	}
	
	@Override
	public String proxifyService(String serviceUrl, String serviceWsdl) throws ManagementException {
		
		UserManagementClientSOAP cli = new UserManagementClientSOAP(this.adminEndpoint);
		return cli.proxify(serviceUrl, serviceWsdl);
	}

}
