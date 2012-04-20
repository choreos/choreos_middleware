package eu.choreos.monitoring.daemon;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.monitoring.utils.ShellHandler;

public class ThresholdTest {

	Threshold threshold;
	
	String pwd = ShellHandler
			.runLocalCommand("pwd").replace("\n", "");
	
	String hostname = ShellHandler
			.runLocalCommand("/bin/bash "+ pwd + "/target/classes/hostname.sh").replace("\n", "");

	@Before
	public void setUp() {
		threshold = new Threshold("Test", Threshold.MAX, 3);
	}

	@Test
	public void testToString() {
		threshold.wasSurpassed(4.0);
		assertEquals("Triggered: Test <= 3.0. Measured: 4.0 in " + hostname,
				threshold.toString());
	}

	@Test
	public void testGetScriptCommand() {
		assertEquals(
				"/bin/bash " + pwd + "/target/classes/hostname.sh",
				threshold.getScriptCommand());
	}

	@Test
	public void shouldGetHostname() throws Exception {
		assertEquals(hostname, threshold.getHostName());
	}
}
