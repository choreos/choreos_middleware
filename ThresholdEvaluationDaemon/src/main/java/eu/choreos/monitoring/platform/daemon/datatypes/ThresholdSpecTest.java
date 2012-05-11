package eu.choreos.monitoring.platform.daemon.datatypes;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.Threshold;

import sun.awt.windows.ThemeReader;

public class ThresholdSpecTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetComparisonConstant() {
		ThresholdSpec threshold = new ThresholdSpec();
		threshold.comparison = "MAX";
		assertEquals(Threshold.MAX, threshold.getComparisonConstant());

		threshold.comparison = "MIN";
		assertEquals(Threshold.MIN, threshold.getComparisonConstant());

		threshold.comparison = "EQUALS";
		assertEquals(Threshold.EQUALS, threshold.getComparisonConstant());
	}

	@Test
	public void testToThreshold() {
		Threshold threshold = new Threshold("load_one", Threshold.MAX, 1.0);
		ThresholdSpec thresholdSpec = new ThresholdSpec();
		thresholdSpec.attribute = "load_one";
		thresholdSpec.comparison = "MAX";
		thresholdSpec.limit_value = "1.0";

		assertEquals(threshold, thresholdSpec.toThreshold());
	}

}
