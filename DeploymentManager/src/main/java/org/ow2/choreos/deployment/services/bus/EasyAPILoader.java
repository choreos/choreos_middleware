package org.ow2.choreos.deployment.services.bus;

import com.ebmwebsourcing.easycommons.research.util.SOAException;
import com.ebmwebsourcing.easycommons.research.util.jaxb.SOAJAXBContext;

/**
 * Executes static code necessary to make the EasyESB API work
 * @author leonardo
 *
 */
public class EasyAPILoader {

	private static boolean loaded = false;
	
	static {
		loadEasyAPI();
	}
	
	public static void loadEasyAPI() {
		
		if (!loaded) {
	        try {
	            // ADD JAXB FACTORY
	            SOAJAXBContext.getInstance().addOtherObjectFactory(esstar.petalslink.com.data.management.user._1.ObjectFactory.class);
	        } catch (SOAException e) {
	            e.printStackTrace();
	        }
	        loaded = true;
		}
	}

}
