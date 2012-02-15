package eu.choreos.storagefactory.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandLineInterfaceHelperTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue("Could not run pwd command", CommandLineInterfaceHelper.runLocalCommand("pwd").length() > 0);
	}

}
