package eu.choreos.monitoring.daemon;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class ThresholdTest {
	
	Threshold threshold ;
	
	@Before
	public void setUp() {
		threshold = new Threshold("Test", Threshold.MAX, 3);
	}

	//TODO Make this not environment dependent
	@Test
	public void testToString() {
		threshold.wasSurpassed(4.0);
		assertEquals("Triggered: Test <= 3.0. Measured: 4.0 in hubble.eclipse.ime.usp.br", threshold.toString());
	}

	@Test
	public void testGetScriptCommand() {
		assertEquals("/bin/bash /home/users/pbmoura/desenvolvimento/CHOReOS-Monitoring-Service/ChoreosMonitoring/target/classes/hostname.sh", threshold.getScriptCommand());
	}

	@Test
	public void shouldGetHostname() throws Exception {
		assertEquals("hubble.eclipse.ime.usp.br", threshold.getHostName());
	}
}
