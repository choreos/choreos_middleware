package org.ow2.choreos.ee.nodes.cm;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.nodes.cm.NodeUpdater;
import org.ow2.choreos.ee.nodes.cm.UpdateHandler;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

public class NodeUpdaterTest {

    private CloudNode node;
    private SshUtil ssh;
    private SshWaiter waiter;
    
    @Before
    public void setUp() throws Exception {
        setNode();
        setSsh();
    }

    private void setNode() {
        node = new CloudNode();
        node.setIp("192.168.56.101");
        node.setUser("ubuntu");
        node.setPrivateKey("ubuntu.pem");
    }
    
    private void setSsh() throws SshNotConnected {
        ssh = mock(SshUtil.class);
        waiter = mock(SshWaiter.class);
        when(waiter.waitSsh(anyString(), anyString(), anyString(), anyInt())).thenReturn(ssh);
    }
    
    @Test
    public void shouldRunChefSolo() throws Exception {
        NodeUpdater updater = new NodeUpdater(node);
        updater.sshWaiter = waiter;
        updater.update();
        verify(ssh).runCommand(NodeUpdater.CHEF_SOLO_COMMAND);
    }
    
    @Test
    public void shouldInvokeHandlers() throws Exception {
        UpdateHandler h1 = mock(UpdateHandler.class);
        UpdateHandler h2 = mock(UpdateHandler.class);
        NodeUpdater updater = new NodeUpdater(node);
        updater.sshWaiter = waiter;
        updater.addHandler(h1);
        updater.addHandler(h2);
        updater.update();
        verify(ssh).runCommand(NodeUpdater.CHEF_SOLO_COMMAND);
        verify(h1).handle();
        verify(h2).handle();
    }

}
