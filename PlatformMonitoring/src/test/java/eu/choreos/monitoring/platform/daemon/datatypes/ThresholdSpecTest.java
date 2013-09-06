package eu.choreos.monitoring.platform.daemon.datatypes;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.SingleThreshold;

public class ThresholdSpecTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetComparisonConstant() {
		SingleThresholdSpec threshold = new SingleThresholdSpec();
		threshold.comparison = "MAX";
		assertEquals(SingleThreshold.MAX, threshold.getComparisonConstant());

		threshold.comparison = "MIN";
		assertEquals(SingleThreshold.MIN, threshold.getComparisonConstant());

		threshold.comparison = "EQUALS";
		assertEquals(SingleThreshold.EQUALS, threshold.getComparisonConstant());
	}

	@Test
	public void testToThreshold() {
		SingleThreshold threshold = new SingleThreshold("load_one", SingleThreshold.MAX, 1.0);
		SingleThresholdSpec thresholdSpec = new SingleThresholdSpec();
		thresholdSpec.attribute = "load_one";
		thresholdSpec.comparison = "MAX";
		thresholdSpec.limit_value = "1.0";

		assertEquals(threshold, thresholdSpec.toThreshold());
	}

}
