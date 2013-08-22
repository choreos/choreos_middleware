package eu.choreos.monitoring.platform.daemon.datatypes;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;

import org.junit.After;
import org.junit.Before;

public class HostTest {

	private HashMap<String, Metric> metricsMap1;
	private HashMap<String, Metric> metricsMap2;
	private HashMap<String, Metric> metricsMap3;
	private HashMap<String, Metric> metricsMap4;
	//private Host host1;
	private Host host2;
	private Host host3;
	private Host host4;

	@Before
	public void setUp() throws Exception {
		metricsMap1 = new HashMap<String, Metric>(); // small
		metricsMap2 = new HashMap<String, Metric>(); // medium
		metricsMap3 = new HashMap<String, Metric>(); // large
		metricsMap4 = new HashMap<String, Metric>(); // extralarge

		metricsMap1.put("load_one", new Metric("load_one", "1.0", 10, 30, 0));
		metricsMap1.put("mem_total", new Metric("mem_total", "1700000", 10, 30, 0));
		
		metricsMap2.put("load_one", new Metric("load_one", "2.0", 10, 30, 0));
		metricsMap2.put("mem_total", new Metric("mem_total", "3500000", 10, 30, 0));
		
		metricsMap3.put("load_one", new Metric("load_one", "2.0", 10, 30, 0));
		metricsMap3.put("mem_total", new Metric("mem_total", "7500000", 10, 30, 0));
		
		metricsMap4.put("load_one", new Metric("load_one", "2.0", 10, 30, 0));
		metricsMap4.put("mem_total", new Metric("mem_total", "15000000", 10, 30, 0));

		// host1 = new Host("test1", "hostname1", "ip1", metricsMap1, 20,20);
		host2 = new Host("test1", "hostname1", "ip1", metricsMap2, 20,20);
		host3 = new Host("test1", "hostname1", "ip1", metricsMap3, 20,20);
		host4 = new Host("test1", "hostname1", "ip1", metricsMap4, 20,20);
	}

	@After
	public void tearDown() throws Exception {
	}

	//@Test
	public void InstanceTypedHosts() {
		
		//assertEquals("small", host1.getInstanceType());
		assertEquals("medium", host2.getInstanceType());
		assertEquals("large", host3.getInstanceType());
		assertEquals("extralarge", host4.getInstanceType());
		
	}

}
