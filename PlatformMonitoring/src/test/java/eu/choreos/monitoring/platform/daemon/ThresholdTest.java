package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.exception.CommandRuntimeException;

public class ThresholdTest {

	SingleThreshold threshold;
	DoubleThreshold dthreshold;

	@Before
	public void setUp() throws CommandRuntimeException {
		threshold = new SingleThreshold("Test", SingleThreshold.MAX, 3);
		dthreshold = new DoubleThreshold(
				"Test2", DoubleThreshold.BETWEEN, 1.0, 2.0);
	}

	@Test
	public void upperBoundarySurpassed() {
		assertTrue(threshold.wasSurpassed(4.0));
		assertTrue(dthreshold.wasSurpassed(3.0));
	}

	@Test
	public void upperBoundaryNotSurpassed() {
		assertFalse(threshold.wasSurpassed(2.0));
		assertFalse(dthreshold.wasSurpassed(1.5));
	}

	@Test
	public void lowerBoundarySurpassed() {
		threshold = new SingleThreshold("Test", SingleThreshold.MIN, 3);
		assertTrue(threshold.wasSurpassed(2.0));
		assertTrue(dthreshold.wasSurpassed(0.5));
	}

	@Test
	public void lowerBoundaryNotSurpassed() {
		threshold = new SingleThreshold("Test", SingleThreshold.MIN, 3);
		assertFalse(threshold.wasSurpassed(4.0));
	}

	@Test
	public void testToString() {
		threshold.wasSurpassed(4.0);
		assertEquals("Triggered: Test <= 3.0. Measured: 4.0",
				threshold.toString());
	}
}
