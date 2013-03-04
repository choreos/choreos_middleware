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
	        	Class[] classes = new Class[]{esstar.petalslink.com.data.management.user._1.ObjectFactory.class,
	        			easybox.org.oasis_open.docs.wsrf.bf_2.ObjectFactory.class,
	        			easybox.org.oasis_open.docs.wsn.b_2.ObjectFactory.class,
	        			easybox.org.oasis_open.docs.wsrf.r_2.ObjectFactory.class,
//	        			easybox.org.oasis_open.docs.wsrf.rl_2.ObjectFactory.class,
	        			easybox.org.oasis_open.docs.wsrf.rp_2.ObjectFactory.class,
	        			com.ebmwebsourcing.wsn.t_1_extension.ObjectFactory.class,
	        			easybox.org.oasis_open.docs.wsn.t_1.ObjectFactory.class
        			};
	            SOAJAXBContext.getInstance().addOtherObjectFactory(classes);
	        } catch (SOAException e) {
	            e.printStackTrace();
	        }
	        loaded = true;
		}
	}

}
