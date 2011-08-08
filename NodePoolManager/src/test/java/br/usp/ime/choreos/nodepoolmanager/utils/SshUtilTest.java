package br.usp.ime.choreos.nodepoolmanager.utils;

import static org.junit.Assert.assertEquals;

import org.jclouds.compute.RunNodesException;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.InfrastructureService;
import br.usp.ime.choreos.nodepoolmanager.Node;

import com.jcraft.jsch.JSchException;

public class SshUtilTest {
    private final InfrastructureService infra = new InfrastructureService();

    @Test
    public void runCommand() throws Exception {
        Node node = createNode();

        // Waiting sshd to start
        Thread.sleep(30000);

        double rand = Math.random();
        String command = "mkdir tmp1\ncd tmp1\ntouch a" + rand + "\nls\ncd ..\nrm -rf tmp1\n";
        String runReturn = new SshUtil(node.getIp()).runCommand(command);

        infra.destroyNode(node.getId());

        assertEquals("a" + rand + "\n", runReturn);

    }

    private Node createNode() throws RunNodesException {
        Node node = new Node();
        node.setImage("us-east-1/ami-ccf405a5");

        infra.createNode(node);

        return node;
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
