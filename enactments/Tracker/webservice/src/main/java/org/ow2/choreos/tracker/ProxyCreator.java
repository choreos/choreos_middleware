package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class ProxyCreator {

	private static final String NAMESPACE = "http://tracker.choreos.ow2.org/";
	private static final String LOCAL_PART = "TrackerImplService";

	public Tracker getProxy(final String wsdl) throws MalformedURLException {
		final QName qname = new QName(NAMESPACE, LOCAL_PART);
		final URL url = new URL(wsdl);
		final Service service = Service.create(url, qname);
		return service.getPort(Tracker.class);
	}
}
