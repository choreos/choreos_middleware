package org.ow2.choreos.tracker;

import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

public class Publisher {

	private final static int PORT = 8081;

	private final transient List<Endpoint> endpoints = new ArrayList<Endpoint>();
	private final transient List<String> wsdls = new ArrayList<String>();
	private static transient int published = 0;

	public List<String> publishTrackers(final int instances) {
		for (int i = 0; i < instances; i++) {
			publishTracker(published++);
		}
		return wsdls;
	}

	public void unpublishAll() {
		for (Endpoint endpoint : endpoints) {
			endpoint.stop();
		}
		endpoints.clear();
		wsdls.clear();
		published = 0;
	}

	public String publishTracker(final int instance) {
		final Tracker tracker = new TrackerWhite();
		final String address = "http://127.0.0.1:" + PORT + "/tracker"
				+ instance;
		final Endpoint endpoint = Endpoint.publish(address, tracker);
		endpoints.add(endpoint);

		final String wsdl = address + "?wsdl";
		wsdls.add(wsdl);
		return wsdl;
	}
}
