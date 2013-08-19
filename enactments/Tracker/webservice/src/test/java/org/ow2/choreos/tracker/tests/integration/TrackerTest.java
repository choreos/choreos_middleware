package org.ow2.choreos.tracker.tests.integration;

import static org.junit.Assert.assertEquals;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.tracker.ProxyCreator;
import org.ow2.choreos.tracker.Publisher;
import org.ow2.choreos.tracker.Tracker;
import org.ow2.choreos.tracker.TrackerType;

public class TrackerTest {

    private final static int THE_ANSWER = 42;

    private transient ProxyCreator proxyCreator;
    private static final transient Publisher PUBLISHER = new Publisher();

    @Before
    public void setUp() {
        proxyCreator = new ProxyCreator();
    }

    @After
    public void tearDown() {
        PUBLISHER.unpublishAll();
    }

    @Test
    public void pathIdsShouldBeIdIfOnlyOneTracker() throws MalformedURLException {
        final List<String> wsdls = PUBLISHER.publishTrackers(1);
        final Tracker tracker = proxyCreator.getProxy(wsdls.get(0), TrackerType.WHITE);
        tracker.setId(THE_ANSWER);
        assertEquals(Integer.toString(THE_ANSWER), tracker.getPathIds());
    }

    @Test
    public void testPathIdsWithTwoServices() throws MalformedURLException {
        final List<String> wsdls = PUBLISHER.publishTrackers(2);
        final int callerId = 0; // NOPMD
        final int calleeId = 1; // NOPMD

        final Tracker callee = proxyCreator.getProxy(wsdls.get(1), TrackerType.WHITE);
        callee.setId(calleeId);

        final Tracker caller = proxyCreator.getProxy(wsdls.get(0), TrackerType.WHITE);
        final List<String> calleeList = new ArrayList<String>(1);
        calleeList.add(wsdls.get(1));
        caller.setInvocationAddress(null, "tracker1", calleeList);

        assertEquals(callerId + " " + calleeId, caller.getPathIds());
    }
}
