package org.ow2.choreos.npm.utils;

import static org.junit.Assert.assertEquals;

import org.jclouds.compute.RunNodesException;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.npm.Configuration;
import org.ow2.choreos.npm.cloudprovider.AWSCloudProvider;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.utils.SshUtil;


import com.jcraft.jsch.JSchException;


public class SshUtilTest {
    private final static AWSCloudProvider infra = new AWSCloudProvider();

    private static Node node = new Node();

    @BeforeClass
    public static void createNode() throws RunNodesException {
        node.setImage("us-east-1/ami-ccf405a5");
        Configuration.set("DEFAULT_PROVIDER", "");
        node = infra.createOrUseExistingNode(node);
    }

    @Test
    public void runCommand() throws Exception {
        // Waiting ssh to start
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (!ssh.isAccessible())
            ;
        double rand = Math.random();
        String command = "mkdir tmp1\ncd tmp1\ntouch a" + rand + "\nls\ncd ..\nrm -rf tmp1\n";
        String runReturn = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile()).runCommand(command);

        assertEquals("a" + rand + "\n", runReturn);
    }



    @Test(expected = JSchException.class)
    public void runCommandInvalidNode() throws Exception {
        new SshUtil("bla.ble.bli.blo", "asdas", "lalalala").runCommand("ls");
    }

    @Test(expected = JSchException.class)
    public void runCommandNodeDoesNotAcceptSSH() throws Exception {
        new SshUtil("www.google.com", "haha", "popopo").runCommand("ls");
    }

}
