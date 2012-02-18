package eu.choreos.servicedeployer.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandLineInterfaceHelperTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue("Could not run pwd command",
				(new CommandLineInterfaceHelper()).runLocalCommand("pwd")
						.length() > 0);
	}

	@Test
	public void testKnifeCommand() {
		assertTrue((new CommandLineInterfaceHelper()).runLocalCommand(
				"knife node show choreos-node").contains("FQDN:   "));
	}

}
