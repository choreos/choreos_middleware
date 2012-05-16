package eu.choreos.platform.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.choreos.platform.utils.ShellHandler;

public class ShellHandlerTest {

	
	private ShellHandler shellHandler = new ShellHandler();

	@Test
	public void testRunLocalCommand() throws CommandRuntimeException {
		assertTrue("Could not run pwd command",
				shellHandler .runLocalCommand("pwd").length() > 0);
	}

	@Test
	public void shouldExecuteLsCommandOnCommandLineInterface() throws CommandRuntimeException {

		String command = "ls /";
		assertTrue("Cannot use Command Line Interface!", shellHandler
				.runLocalCommand(command).contains("usr"));

	}

	@Test
	public void shouldExecuteLsCommandOnCommandLineInterfaceAtSpecifiedFolder() throws CommandRuntimeException {

		String command = "ls";
		assertTrue("Cannot use Command Line Interface!", shellHandler
				.runLocalCommand(command, "/").contains("usr"));

	}

}