package eu.choreos.servicedeployer.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.choreos.servicedeployer.Configuration;

public class CommandLineTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue("Could not run pwd command",
				CommandLine.runLocalCommand("pwd").length() > 0);
	}

	@Test
	public void testKnifeCommand() {
		String chefRepoFolder = Configuration.get("CHEF_REPO");
		String command = "knife node";
		assertTrue(
				"Cannot use knife!",
				CommandLine.runLocalCommand(command, chefRepoFolder).contains(
						"** NODE COMMANDS **"));
	}

}
