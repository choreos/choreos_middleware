package org.ow2.choreos.tracker;

import static org.junit.Assert.assertFalse;

import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class IntegrationTest {

	private static final Publisher PUBLISHER = new Publisher();
	private transient String wsdl;

	@Before
	public void setUp() {
		wsdl = PUBLISHER.publishTracker(0);
	}

	@After
	public void tearDown() {
		PUBLISHER.unpublishAll();
	}

	@Ignore
	// Unfortunately, it fails.
	public void shouldCreateProxy() throws MalformedURLException {
		final ProxyCreator creator = new ProxyCreator();
		final Tracker tracker = creator.getProxy(wsdl, TrackerType.WHITE);
		final String pathIds = tracker.getPathIds();
		assertFalse(pathIds.isEmpty());
	}

	@Test
	// This is a workaround. Giving up jaxws and using cxf.
	public void shouldCreateApacheCXFProxy() throws MalformedURLException {
		final Tracker tracker = CxfProxyCreator.getTracker(wsdl);
		final String reply = tracker.getPathIds();
		assertFalse(reply.isEmpty());
	}
}
