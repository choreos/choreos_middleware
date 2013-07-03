package org.ow2.choreos.deployment.nodes.cm;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

public class BootstrapperCheckerTest {

    private SshWaiter waiterForGoodNode;
    private SshWaiter waiterForBadNode;
    private CloudNode node;

    @Before
    public void setUp() throws Exception {

        SshUtil goodSsh = mock(SshUtil.class);
        when(goodSsh.runCommand("ls $HOME/chef-solo")).thenReturn(
                "cookbooks  node.json  prepare_deployment.sh  solo.rb add_recipe_to_node.sh");
        waiterForGoodNode = mock(SshWaiter.class);
        when(waiterForGoodNode.waitSsh(anyString(), anyString(), anyString(), anyInt())).thenReturn(goodSsh);

        SshUtil badSsh = mock(SshUtil.class);
        when(badSsh.runCommand("ls $HOME/chef-solo")).thenReturn(
                "ls: impossível acessar nao: Arquivo ou diretório não encontrado");
        waiterForBadNode = mock(SshWaiter.class);
        when(waiterForBadNode.waitSsh(anyString(), anyString(), anyString(), anyInt())).thenReturn(badSsh);

        node = new CloudNode();
        node.setIp("192.168.56.101");
        node.setUser("ubuntu");
        node.setPrivateKey("ubuntu.pem");
    }

    @Test
    public void shouldBeBootstrapped() {
        BootstrapChecker bootstrapChecker = new BootstrapChecker();
        bootstrapChecker.sshWaiter = waiterForGoodNode;
        boolean ok = bootstrapChecker.isBootstrapped(node);
        assertTrue(ok);
    }

    @Test
    public void shouldNotBeBootstrapped() {
        BootstrapChecker bootstrapChecker = new BootstrapChecker();
        bootstrapChecker.sshWaiter = waiterForBadNode;
        boolean ok = bootstrapChecker.isBootstrapped(node);
        assertFalse(ok);
    }

}
