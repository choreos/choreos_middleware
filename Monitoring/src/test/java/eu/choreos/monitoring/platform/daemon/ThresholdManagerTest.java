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

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.platform.daemon.datatypes.Host;
import eu.choreos.monitoring.platform.daemon.datatypes.Metric;
import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdManagerTest {

	private ThresholdManager notifier;
	private HostManager hostManager;
	private Map<String, Metric> metricsMapForMedium;
	private Map<String, Metric> metricsMapForLarge;
	private Map<String, Metric> metricsMapForExtraLarge;
	private ArrayList<Host> hostList;
	private Host extraLargeHost;
	private Host mediumHost;
	private Host largeHostDown;

	@Before
	public void setUp() throws Exception {
		metricsMapForMedium = new HashMap<String, Metric>();
		metricsMapForLarge = new HashMap<String, Metric>();
		metricsMapForExtraLarge = new HashMap<String, Metric>();
		hostManager = mock(HostManager.class);
		
		metricsMapForExtraLarge.put("load_one", new Metric("load_one", "1.1", 10, 30, 0));
		metricsMapForExtraLarge.put("mem_total", new Metric("mem_total", "9876543", 10, 30, 0));
		
		metricsMapForMedium.put("load_one", new Metric("load_one", "2.1", 10, 30, 0));
		metricsMapForMedium.put("mem_total", new Metric("mem_total", "3500000", 10, 30, 0));

		metricsMapForLarge.put("mem_total", new Metric("mem_total", "7550000", 10, 30, 0));
		metricsMapForLarge.put("load_one"	, new Metric("load_one", "0.4", 90, 20, 0));
		
		extraLargeHost = new Host("test1", "hostname1", "ip1", metricsMapForExtraLarge, 20,20);
		mediumHost = new Host("test1", "hostname2", "ip2", metricsMapForMedium, 20,20);		
		largeHostDown = new Host("test1", "hostname3", "ip3", metricsMapForLarge, 40,20); 

		hostList = new ArrayList<Host>();
		hostList.add(extraLargeHost);
		hostList.add(mediumHost);
		//hostList.add(largeHostDown);

		when(hostManager.getHosts()).thenReturn(hostList);

		notifier = new ThresholdManager(hostManager);


	}

	@Test
	public void shouldAddOneSingleThreshold() {
		SingleThreshold threshold = new SingleThreshold("load_one", SingleThreshold.MAX, 3);
		notifier.addThreshold("default", threshold);
		assertEquals(1, notifier.getThresholdSize());
	}
	
	@Test
	public void shouldAddOneDoubleThreshold() {
		DoubleThreshold threshold = new DoubleThreshold("load_five", DoubleThreshold.BETWEEN, 1,1.1);
		notifier.addThreshold("default", threshold);
		assertEquals(1, notifier.getThresholdSize());
	}

	@Test
	public void shouldNotAddEqualThresholds() {
		assertEquals(0, notifier.getThresholdSize());
		SingleThreshold threshold1 = new SingleThreshold("load_one", SingleThreshold.MAX, 3);
		SingleThreshold threshold2 = new SingleThreshold("load_one", SingleThreshold.MAX, 3);
		notifier.addThreshold("default", threshold1);
		notifier.addThreshold("default", threshold2);
		assertEquals(1, notifier.getThresholdSize());
	}


	@Test
	public void shouldEvaluateMultipleThresholdsInSingleHost()
			throws GangliaException {

		SingleThreshold threshold1 = new SingleThreshold("load_one", SingleThreshold.MIN, 1.5);
		SingleThreshold threshold2 = new SingleThreshold("mem_total", SingleThreshold.MAX, 50000);

		notifier.addThreshold("default", threshold1);
		notifier.addThreshold("small", threshold2);
		
		notifier.updateThresholdsInfo();

		Map<String, List<AbstractThreshold>> list1 = notifier.getSurpassedThresholds();
				
		assertEquals("surpassed thresholds", 1, list1.size());

		List<AbstractThreshold> list = list1.get(extraLargeHost.getHostName());
		
		assertEquals("hosts with surpassed thresholds", 1, list.size());
		
	}

	@Test
	public void shouldNotifySingleThresholdInMultipleHosts()
			throws GangliaException {
		SingleThreshold threshold = new SingleThreshold("load_one", SingleThreshold.MAX, 0.8);

		notifier.addThreshold("default", threshold);
		notifier.updateThresholdsInfo();

		Map<String, List<AbstractThreshold>> list1 = notifier.getSurpassedThresholds();
		System.out.println(list1);
		
		assertEquals(2, list1.size());
		
		List<AbstractThreshold> list;

		list = list1.get(extraLargeHost.getHostName());
		assertTrue(list.contains(threshold));

		list = list1.get(mediumHost.getHostName());
		assertTrue(list.contains(threshold));

	}
	
	@Test
	public void thereAreNotSurpassedThreshold() throws GangliaException {
		SingleThreshold t = new SingleThreshold("load_one", SingleThreshold.MAX, 3);
		
		notifier.addThreshold("default", t);
		notifier.updateThresholdsInfo();
		
		assertFalse(notifier.thereAreSurpassedThresholds());
	}
	
	@Test
	public void thereAreSurpassedThreshold() throws GangliaException {
		SingleThreshold t = new SingleThreshold("load_one", SingleThreshold.MAX, 0);
		
		notifier.addThreshold("default", t);
		notifier.updateThresholdsInfo();
		
		assertTrue(notifier.thereAreSurpassedThresholds());
	}

	@Test
	public void thereIsAHostDown() throws GangliaException {
		hostList.add(largeHostDown);
		notifier.updateThresholdsInfo();
		Map<String, List<AbstractThreshold>> t = notifier.getSurpassedThresholds();
		assertTrue(t.containsKey(largeHostDown.getHostName()));
		assertEquals("host_down", t.get(largeHostDown.getHostName()).get(0).getName());
	}

}
