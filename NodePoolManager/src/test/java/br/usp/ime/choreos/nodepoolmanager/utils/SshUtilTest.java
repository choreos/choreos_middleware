package br.usp.ime.choreos.nodepoolmanager.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.jcraft.jsch.JSchException;

public class SshUtilTest {

    @Test
    public void runCommand() throws Exception {
        String host = "ec2-50-16-158-242.compute-1.amazonaws.com";
        double rand = Math.random();
        String command = "mkdir tmp1\ncd tmp1\ntouch a" + rand + "\nls\ncd ..\nrm -rf tmp1\n";
        String runReturn =  new SshUtil(host).runCommand(command);
        assertEquals("a" + rand + "\n", runReturn);
    }

    @Test(expected = JSchException.class)
    public void runCommandInvalidNode() throws Exception {
        new SshUtil("bla.ble.bli.blo").runCommand("ls");
    }

    @Test(expected = JSchException.class)
    public void runCommandNodeDoesNotAcceptSSH() throws Exception {
        new SshUtil("www.google.com").runCommand("ls");
    }

}
