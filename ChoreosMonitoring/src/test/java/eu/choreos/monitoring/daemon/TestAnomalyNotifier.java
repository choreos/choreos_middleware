package eu.choreos.monitoring.daemon;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.Gmetric;
import eu.choreos.monitoring.GmondDataReader;

public class TestAnomalyNotifier {

	private AnomalyNotifier notifier;
	private GmondDataReader dataReader;
	private HashMap<String, Gmetric> returnedMap;

	@Before
	public void setUp() throws Exception {
		returnedMap = new HashMap<String, Gmetric>();
		returnedMap.put("load_one", new Gmetric("load_one", "0.8"));
		returnedMap.put("mem_total", new Gmetric("mem_total", "9876543"));

		dataReader = mock(GmondDataReader.class);
		when(dataReader.getAllMetrics()).thenReturn(returnedMap);

		notifier = new AnomalyNotifier(dataReader);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldIdentifyLoadAverageGreaterThanThreeInTheLastFiveMinutes() {

		returnedMap.put("load_five", new Gmetric("load_five", "3.8"));

		boolean evaluation = notifier.identifySurpassedUpperThreshold(
				"load_five", 3);

		assertTrue(evaluation);

	}

	@Test
	public void shouldNotIdentifyLoadAverageGreaterThanThreeInTheLastFiveMinutes() {

		returnedMap.put("load_five", new Gmetric("load_five", "1.8"));

		boolean evaluation = notifier.identifySurpassedUpperThreshold(
				"load_five", 3);

		assertFalse("Evaluated threshold should be false", evaluation);

	}

	@Test
	public void shouldIdentifyFreeMemoryLessThan64Mb() {
		returnedMap.put("mem_free", new Gmetric("mem_free", "16000"));

		boolean evaluation = notifier.identifySurpassedLowerThreshold(
				"mem_free", 64000);

		assertTrue(
				"This host has only 16MB of free memory. Should not have been triggered.",
				evaluation);

	}

	@Test
	public void shouldNotIdentifyFreeMemoryLessThan64Mb() {
		returnedMap.put("mem_free", new Gmetric("mem_free", "176000"));

		boolean evaluation = notifier.identifySurpassedLowerThreshold(
				"mem_free", 64000);

		assertFalse(
				"This host has 176MB of free memory. Should not have been triggered.",
				evaluation);

	}

}
