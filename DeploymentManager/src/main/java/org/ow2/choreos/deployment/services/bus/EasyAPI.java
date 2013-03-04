package org.ow2.choreos.deployment.services.bus;

import java.net.URISyntaxException;

import com.ebmwebsourcing.easycommons.research.util.SOAException;
import com.ebmwebsourcing.easycommons.research.util.jaxb.SOAJAXBContext;
import com.ebmwebsourcing.esstar.management.UserManagementClientSOAP;

import esstar.petalslink.com.service.management._1_0.ManagementException;

/**
 * Arquivo temporário pra fazer uns testes mais ligeiros
 * @author leonardo
 *
 */
public class EasyAPI {
	
	public static final String ADMIN_ENDPOINT = "http://192.168.56.101:8180/services/adminExternalEndpoint";
	public static final String SVC_ENDPOINT = "http://192.168.56.101:1234/airline";
	public static final String SVC_WSDL = "http://192.168.56.101:1234/airline?wsdl";
	
	 static {
        try {
            // ADD JAXB FACTORY
            SOAJAXBContext.getInstance().addOtherObjectFactory(esstar.petalslink.com.data.management.user._1.ObjectFactory.class);
        } catch (SOAException e) {
            e.printStackTrace();
        }
	}
	 
	public static void main(String[] args) throws URISyntaxException {

		proxify();
	}
	
	public static void proxify() {
		
		UserManagementClientSOAP cli = new UserManagementClientSOAP(ADMIN_ENDPOINT);
		try {
			System.out.println("-c " + ADMIN_ENDPOINT + " -pr " + SVC_ENDPOINT + " " + SVC_WSDL);
			String result = cli.proxify(SVC_ENDPOINT, SVC_WSDL);
			System.out.println("Proxify result: " + result);
		} catch (ManagementException e) {
			System.out.println("Exception at proxify!");
			e.printStackTrace();
		}
	}
	
}
