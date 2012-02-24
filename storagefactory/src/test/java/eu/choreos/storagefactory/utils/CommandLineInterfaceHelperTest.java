package eu.choreos.storagefactory.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.choreos.storagefactory.Configuration;

public class CommandLineInterfaceHelperTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue("Could not run pwd command",
				(new CommandLineInterfaceHelper()).runLocalCommand("pwd")
						.length() > 0);
	}

	@Test
	public void testKnifeCommand() {
		String runCommand ="knife node show choreos-other-node ";
		runCommand+=Configuration.get("CHEF_CONFIG_FILE")!=null?("-c "+Configuration.get("CHEF_CONFIG_FILE")):"";
		System.out.println("command: "+runCommand);		
		assertTrue((new CommandLineInterfaceHelper()).runLocalCommand(runCommand).contains("FQDN:   "));
	}

}
