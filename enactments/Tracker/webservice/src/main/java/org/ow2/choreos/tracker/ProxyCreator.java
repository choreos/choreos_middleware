package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

public class ProxyCreator {

    private static final String NAMESPACE = "http://tracker.choreos.ow2.org/";

    public Tracker getProxy(final String wsdl, final TrackerType type) throws MalformedURLException {
        final TrackerProperties trackerProps = new TrackerProperties();
        final String localPart = trackerProps.getLocalPart(type);

        final QName qname = new QName(NAMESPACE, localPart);
        final URL url = new URL(wsdl);
        final Service service = Service.create(url, qname);
        return service.getPort(Tracker.class);
    }
}
