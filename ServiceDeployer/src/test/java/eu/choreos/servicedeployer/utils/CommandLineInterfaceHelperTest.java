package eu.choreos.servicedeployer.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.choreos.servicedeployer.Configuration;

public class CommandLineInterfaceHelperTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue("Could not run pwd command",
				(new CommandLineInterfaceHelper()).runLocalCommand("pwd")
						.length() > 0);
	}

	@Test
	public void testKnifeCommand() {
		String runCommand = "knife node show choreos-other-node";
		runCommand+= Configuration.get("CHEF_CONFIG_FILE")!=null?" -c "+Configuration.get("CHEF_CONFIG_FILE"):"";
		
		assertTrue(
				"Could not connect to chef server or a choreos-other-node is not present",
				(new CommandLineInterfaceHelper()).runLocalCommand(runCommand).contains("FQDN:   "));
	}

}
