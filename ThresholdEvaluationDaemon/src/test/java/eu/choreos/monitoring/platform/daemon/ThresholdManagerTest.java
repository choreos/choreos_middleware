package eu.choreos.monitoring.platform.daemon;

import static org.junit.Assert.assertEquals;
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

import sun.awt.windows.ThemeReader;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.daemon.datatypes.Metric;
import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdManagerTest {

	private ThresholdManager notifier;
	private HostManager hostManager;
	private Map<String, Metric> metricsMap1;
	private Map<String, Metric> metricsMap2;
	private ArrayList<Host> hostList;
	private Host host1;
	private Host host2;
	private Host host3;

	@Before
	public void setUp() throws Exception {
		metricsMap1 = new HashMap<String, Metric>();
		metricsMap2 = new HashMap<String, Metric>();
		metricsMap1.put("load_one", new Metric("load_one", "1.0", 10, 30, 0));
		metricsMap1.put("mem_total", new Metric("mem_total", "9876543", 10, 30, 0));
		metricsMap2.put("load_one", new Metric("load_one", "2.0", 10, 30, 0));
		metricsMap2.put("mem_total", new Metric("mem_total", "4000", 10, 30, 0));

		hostManager = mock(HostManager.class);
		host1 = new Host("test1", "hostname1", "ip1", metricsMap1);
		host2 = new Host("test1", "hostname2", "ip2", metricsMap2);
		HashMap<String, Metric> m = new HashMap<String, Metric>();
		m.put("load_one"	, new Metric("load_one", "value", 90, 20, 0));
		host3 = new Host("test1", "hostname3", "ip3", m); 

		hostList = new ArrayList<Host>();
		hostList.add(host1);
		hostList.add(host2);

		when(hostManager.getHosts()).thenReturn(hostList);

		notifier = new ThresholdManager(hostManager);
		

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
	public void shouldEvaluateMultipleThresholdsInSingleHost()
			throws GangliaException {

		Threshold threshold1 = new Threshold("load_one", Threshold.MIN, 1.5);
		Threshold threshold2 = new Threshold("mem_total", Threshold.MAX, 50000);

		notifier.addThreshold(threshold1);
		notifier.addThreshold(threshold2);
		
		notifier.updateThresholdsInfo();

		Map<String, List<Threshold>> list1 = notifier.getSurpassedThresholds();
		
		assertEquals(1, list1.size());

		List<Threshold> list = list1.get(host1.getHostName());
		
		assertEquals(2, list.size());
		
	}

	@Test
	public void shouldNotifySingleThresholdInMultipleHosts()
			throws GangliaException {
		Threshold threshold = new Threshold("load_one", Threshold.MAX, 0.8);

		notifier.addThreshold(threshold);
		notifier.updateThresholdsInfo();

		Map<String, List<Threshold>> list1 = notifier.getSurpassedThresholds();
		
		assertEquals(2, list1.size());
		
		List<Threshold> list;

		list = list1.get(host1.getHostName());
		assertTrue(list.contains(threshold));

		list = list1.get(host2.getHostName());
		assertTrue(list.contains(threshold));

	}
	
	@Test
	public void thereAreNotSurpassedThreshold() throws GangliaException {
		Threshold t = new Threshold("load_one", Threshold.MAX, 3);
		
		notifier.addThreshold(t);
		notifier.updateThresholdsInfo();
		
		assertFalse(notifier.thereAreSurpassedThresholds());
	}
	
	@Test
	public void thereAreSurpassedThreshold() throws GangliaException {
		Threshold t = new Threshold("load_one", Threshold.MAX, 0);
		
		notifier.addThreshold(t);
		notifier.updateThresholdsInfo();
		
		assertTrue(notifier.thereAreSurpassedThresholds());
	}

	@Test
	public void thereIsAHostDown() throws GangliaException {
		hostList.add(host3);
		notifier.updateThresholdsInfo();
		Map<String, List<Threshold>> t = notifier.getSurpassedThresholds();
		assertTrue(t.containsKey(host3.getHostName()));
		assertEquals("host_down", t.get(host3.getHostName()).get(0).getName());
	}

}
