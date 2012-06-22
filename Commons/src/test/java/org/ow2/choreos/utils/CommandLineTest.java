package org.ow2.choreos.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandLineTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue(CommandLine.run("pwd").length() > 0);
	}

}
