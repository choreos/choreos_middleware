package org.ow2.choreos.tracker;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;

/**
 * This proxy creator uses apache cxf. Written because jaxws is having
 * comptibility problems with cxf/xerces.
 * 
 * @author cadu
 * 
 */
public final class CxfProxyCreator {

	private CxfProxyCreator() {
	}

	public static Tracker getTracker(final String wsdl) {
		final JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
		factory.setServiceClass(Tracker.class);
		factory.setAddress(wsdl);
		return (Tracker) factory.create();
	}
}
