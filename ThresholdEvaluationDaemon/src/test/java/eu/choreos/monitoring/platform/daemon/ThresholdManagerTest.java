package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.datatypes.Metric;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class ThresholdManagerTest {

	private ThresholdManager notifier;
	private GmondDataReader dataReader;
	private Map<String, Metric> returnedMap;
	private Threshold threshold;

	@Before
	public void setUp() throws Exception {
		returnedMap = new HashMap<String, Metric>();
		returnedMap.put("load_one", new Metric("load_one", "0.8"));
		returnedMap.put("mem_total", new Metric("mem_total", "9876543"));
		returnedMap.put("proc_run", new Metric("proc_run", "1"));
		returnedMap.put("load_five", new Metric("load_five", "0.04"));
		returnedMap.put("disk_free", new Metric("disk_free", "224.231"));
		returnedMap.put("mem_cached", new Metric("mem_cached", "412908"));
		returnedMap.put("pkts_in", new Metric("pkts_in", "124.93"));
		
		dataReader = mock(GmondDataReader.class);
		
		for(String metric:returnedMap.keySet()) {
		//when(dataReader.getMetricValue(metric)).thenReturn(returnedMap.get(metric).getValue());
			
		}

		notifier = new ThresholdManager(dataReader);

		threshold = new Threshold("mem_free", Threshold.MIN, 64000);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void shouldAddOneThreshold() {
		Threshold threshold = new Threshold("load_one", Threshold.MAX, 3);
		notifier.addThreshold(threshold);
		assertEquals(1, notifier.getThresholdSize());
	}
	
	@Test
	public void shouldNotAddEqualThresholds() {
		assertEquals(0, notifier.getThresholdSize());
		Threshold threshold1 = new Threshold("load_one", Threshold.MAX, 3);
		Threshold threshold2 = new Threshold("load_one", Threshold.MAX, 3);
		notifier.addThreshold(threshold1);
		notifier.addThreshold(threshold2);
		assertEquals(1, notifier.getThresholdSize());
	}

	@Test
	public void shouldNotNotifySingleThreshold() throws GangliaException {
		Threshold threshold = new Threshold("pkts_in", Threshold.MAX, 1000);

		notifier.addThreshold(threshold);

		List<Threshold> list = notifier.getAllSurpassedThresholds();

		assertTrue(list.isEmpty());
	}

//	@Test
	public void shouldNotifySingleThreshold() throws GangliaException {
		Threshold threshold = new Threshold("pkts_in", Threshold.MAX, 100);

		notifier.addThreshold(threshold);

		List<Threshold> list = notifier.getAllSurpassedThresholds();

		assertTrue(list.contains(threshold));
	}

//	@Test
	public void shouldEvaluateMultipleThresholds() throws GangliaException {

		Threshold threshold1 = new Threshold("pkts_in", Threshold.MIN, 1000);
		Threshold threshold4 = new Threshold("disk_free", Threshold.MAX, 100);
		Threshold threshold2 = new Threshold("mem_cached", Threshold.MIN, 50000);
		Threshold threshold3 = new Threshold("mem_cached", Threshold.MAX,
				1000000);

		notifier.addThreshold(threshold1);
		notifier.addThreshold(threshold2);
		notifier.addThreshold(threshold3);
		notifier.addThreshold(threshold4);

		List<Threshold> list = notifier.getAllSurpassedThresholds();

		assertTrue(list.contains(threshold1));
		assertFalse(list.contains(threshold2));
		assertFalse(list.contains(threshold3));
		assertTrue(list.contains(threshold4));

	}

}
