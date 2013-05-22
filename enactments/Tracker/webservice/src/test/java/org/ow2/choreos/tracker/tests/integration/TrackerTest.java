package org.ow2.choreos.tracker.tests.integration;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.ws.Endpoint;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.tracker.ProxyCreator;
import org.ow2.choreos.tracker.Tracker;
import org.ow2.choreos.tracker.TrackerImpl;


public class TrackerTest {

	private final static int PORT = 8081;
	private final static int THE_ANSWER = 42;

	private final transient List<Endpoint> endpoints = new ArrayList<Endpoint>();
	private final transient List<String> wsdls = new ArrayList<String>();
	private transient ProxyCreator proxyCreator;

	@Before
	public void setUp() {
		proxyCreator = new ProxyCreator();
	}

	@After
	public void tearDown() {
		unpublishAll();
	}

	@Test
	public void pathIdsShouldBeIdIfOnlyOneTracker()
			throws MalformedURLException {
		publishTrackers(1);
		final Tracker tracker = proxyCreator.getProxy(wsdls.get(0));
		tracker.setId(THE_ANSWER);
		assertEquals(Integer.toString(THE_ANSWER), tracker.getPathIds());
	}

	@Test
	public void testPathIdsWithTwoServices() throws MalformedURLException {
		publishTrackers(2);
		final int callerId = 0; // NOPMD
		final int calleeId = 1; // NOPMD

		final Tracker callee = proxyCreator.getProxy(wsdls.get(1));
		callee.setId(calleeId);

		final Tracker caller = proxyCreator.getProxy(wsdls.get(0));
		final List<String> calleeList = new ArrayList<String>(1);
		calleeList.add(wsdls.get(1));
		caller.setInvocationAddress(null, "tracker1", calleeList);

		assertEquals(callerId + " " + calleeId, caller.getPathIds());
	}

	private void publishTrackers(final int instances) {
		for (int i = 0; i < instances; i++) {
			publishTracker(i);
		}
	}

	private void unpublishAll() {
		for (Endpoint endpoint : endpoints) {
			endpoint.stop();
		}
		endpoints.clear();
		wsdls.clear();
	}

	private void publishTracker(final int instance) {
		final Tracker tracker = new TrackerImpl();
		final String address = "http://127.0.0.1:" + PORT + "/tracker"
				+ instance;
		final Endpoint endpoint = Endpoint.publish(address, tracker);
		endpoints.add(endpoint);
		wsdls.add(address + "?wsdl");
	}
}
