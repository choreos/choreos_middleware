package eu.choreos.monitoring.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.choreos.monitoring.utils.ShellHandler;

public class ShellHandlerTest {

	@Test
	public void testRunLocalCommand() {
		assertTrue("Could not run pwd command",
				ShellHandler.runLocalCommand("pwd").length() > 0);
	}

	@Test
	public void shouldExecuteLsCommandOnCommandLineInterface() {

		String command = "ls /";
		assertTrue("Cannot use Command Line Interface!", ShellHandler
				.runLocalCommand(command).contains("usr"));

	}

	@Test
	public void shouldExecuteLsCommandOnCommandLineInterfaceAtSpecifiedFolder() {

		String command = "ls";
		assertTrue("Cannot use Command Line Interface!", ShellHandler
				.runLocalCommand(command, "/").contains("usr"));

	}

}