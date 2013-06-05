package org.ow2.choreos.utils;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class CommandLineTest {

    @Test
    public void testRunLocalCommand() throws CommandLineException {
	assertTrue(CommandLine.run("pwd").length() > 0);
    }

    @Test(expected = CommandLineException.class)
    public void shouldThrowExceptionBecauseExistStatus() throws CommandLineException {
	CommandLine.run("this command does not exit");
    }

    @Test(expected = CommandLineException.class)
    public void shouldThrowExceptionBecauseExistStatus2() throws CommandLineException {
	CommandLine.run("cat arquivo_que_nao_existe");
    }

}
