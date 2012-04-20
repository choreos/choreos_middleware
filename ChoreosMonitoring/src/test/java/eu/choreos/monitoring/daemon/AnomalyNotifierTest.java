package eu.choreos.monitoring.daemon;

import static org.junit.Assert.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.Gmetric;
import eu.choreos.monitoring.GmondDataReader;

public class AnomalyNotifierTest {

	private AnomalyNotifier notifier;
	private GmondDataReader dataReader;
	private Map<String, Gmetric> returnedMap;
	private Threshold threshold;

	@Before
	public void setUp() throws Exception {
		returnedMap = new HashMap<String, Gmetric>();
		returnedMap.put("load_one", new Gmetric("load_one", "0.8"));
		returnedMap.put("mem_total", new Gmetric("mem_total", "9876543"));
		returnedMap.put("proc_run", new Gmetric("proc_run", "1"));
		returnedMap.put("load_five", new Gmetric("load_five", "0.04"));
		returnedMap.put("disk_free", new Gmetric("disk_free", "224.231"));
		returnedMap.put("mem_cached", new Gmetric("mem_cached", "412908"));
		returnedMap.put("pkts_in", new Gmetric("pkts_in", "124.93"));

		dataReader = mock(GmondDataReader.class);
		when(dataReader.getAllMetrics()).thenReturn(returnedMap);

		notifier = new AnomalyNotifier(dataReader);

		threshold = new Threshold("mem_free", Threshold.MIN, 64000);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldIdentifyLoadAverageGreaterThanThreeInTheLastFiveMinutes() {

		returnedMap.put("load_five", new Gmetric("load_five", "3.8"));

		boolean evaluation = notifier.wasSurpassed(new Threshold("load_five",
				Threshold.MAX, 3));

		assertTrue(evaluation);

	}

	@Test
	public void shouldNotIdentifyLoadAverageGreaterThanThreeInTheLastFiveMinutes() {

		returnedMap.put("load_five", new Gmetric("load_five", "1.8"));

		boolean evaluation = notifier.wasSurpassed(new Threshold("load_five",
				Threshold.MAX, 3));

		assertFalse("Evaluated threshold should be false", evaluation);

	}

	@Test
	public void shouldIdentifyFreeMemoryLessThan64Mb() {
		returnedMap.put("mem_free", new Gmetric("mem_free", "16000"));

		boolean evaluation = notifier.wasSurpassed(threshold);

		assertTrue(
				"This host has only 16MB of free memory. Should not have been triggered.",
				evaluation);

	}

	@Test
	public void shouldNotIdentifyFreeMemoryLessThan64Mb() {
		returnedMap.put("mem_free", new Gmetric("mem_free", "176000"));

		boolean evaluation = notifier.wasSurpassed(threshold);

		assertFalse(
				"This host has 176MB of free memory. Should not have been triggered.",
				evaluation);

	}

	@Test
	public void shouldNotNotifySingleThreshold() {
		Threshold threshold = new Threshold("pkts_in", Threshold.MAX, 1000);

		notifier.getThresholds().add(threshold);

		List<Threshold> list = notifier.getAllSurpassedThresholds();

		assertTrue(list.isEmpty());
	}

	@Test
	public void shouldNotifySingleThreshold() {
		Threshold threshold = new Threshold("pkts_in", Threshold.MAX, 100);

		notifier.getThresholds().add(threshold);

		List<Threshold> list = notifier.getAllSurpassedThresholds();

		assertTrue(list.contains(threshold));
	}

	@Test
	public void shouldEvaluateMultipleThresholds() {

		Threshold threshold1 = new Threshold("pkts_in", Threshold.MIN, 1000);
		Threshold threshold4 = new Threshold("disk_free", Threshold.MAX, 100);
		Threshold threshold2 = new Threshold("mem_cached", Threshold.MIN, 50000);
		Threshold threshold3 = new Threshold("mem_cached", Threshold.MAX,
				1000000);

		notifier.getThresholds().add(threshold1);
		notifier.getThresholds().add(threshold2);
		notifier.getThresholds().add(threshold3);
		notifier.getThresholds().add(threshold4);

		List<Threshold> list = notifier.getAllSurpassedThresholds();

		assertTrue(list.contains(threshold1));
		assertFalse(list.contains(threshold2));
		assertFalse(list.contains(threshold3));
		assertTrue(list.contains(threshold4));

	}

}
