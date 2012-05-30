package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.platform.utils.CommandRuntimeException;
import eu.choreos.platform.utils.ShellHandler;

public class ThresholdTest {

	Threshold threshold;

	@Before
	public void setUp() throws CommandRuntimeException {
		threshold = new Threshold("Test", Threshold.MAX, 3);
	}

	@Test
	public void upperBoundarySurpassed() {
		assertTrue(threshold.wasSurpassed(4.0));
	}

	@Test
	public void upperBoundaryNotSurpassed() {
		assertFalse(threshold.wasSurpassed(2.0));
	}

	@Test
	public void lowerBoundarySurpassed() {
		threshold = new Threshold("Test", Threshold.MIN, 3);
		assertTrue(threshold.wasSurpassed(2.0));
	}

	@Test
	public void lowerBoundaryNotSurpassed() {
		threshold = new Threshold("Test", Threshold.MIN, 3);
		assertFalse(threshold.wasSurpassed(4.0));
	}

	@Test
	public void testToString() {
		threshold.wasSurpassed(4.0);
		assertEquals("Triggered: Test <= 3.0. Measured: 4.0",
				threshold.toString());
	}
}
