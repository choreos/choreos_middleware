package org.ow2.choreos.tracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;

public class TrackerInfoTest {

	private transient TrackerInfo trackerInfo;

	@Before
	public void setUp() {
		trackerInfo = new TrackerInfo();
	}

	@Test
	public void testAnswerPrediction() {
		assertEquals("0 1 2", trackerInfo.getExpectedPathIds(3));
	}

	@Test
	public void testTrackerName() {
		assertEquals("tracker42", trackerInfo.getName(42));
	}

	@Test(expected = IllegalStateException.class)
	public void shouldThrowIllegalStateExceptionIfgettingWsdlWithoutChorSet() {
		trackerInfo.getWsdl(0);
		fail();
	}

	@Test(expected = NoSuchElementException.class)
	public void shouldThrowNoSuchElementExceptionIfNoServiceIsFound() {
		trackerInfo.setChoreography(new Choreography());
		trackerInfo.getWsdl(0);
	}

}
