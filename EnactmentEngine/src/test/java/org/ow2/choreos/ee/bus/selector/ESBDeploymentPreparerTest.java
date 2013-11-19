package org.ow2.choreos.ee.bus.selector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.anyInt;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.bus.selector.BusNotPreparedException;
import org.ow2.choreos.ee.bus.selector.ESBDeploymentPreparer;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

public class ESBDeploymentPreparerTest {

    private SshUtil ssh;
    private SshWaiter waiter;
    private SshWaiter badWaiter;
    private CloudNode node;
    
    @Before
    public void setUp() throws SshNotConnected {
        setUpSsh();
        setUpNode();
    }

    private void setUpSsh() throws SshNotConnected {
        ssh = mock(SshUtil.class);
        waiter = mock(SshWaiter.class);
        when(waiter.waitSsh(anyString(), anyString(), anyString(), anyInt())).thenReturn(ssh);
        badWaiter = mock(SshWaiter.class);
        when(badWaiter.waitSsh(anyString(), anyString(), anyString(), anyInt())).thenThrow(new SshNotConnected());
    }

    private void setUpNode() {
        node = new CloudNode();
        node.setId("1");
        node.setIp("192.168.56.101");
        node.setUser("user");
        node.setPrivateKey("key.pem");
    }
    
    @Test
    public void shouldExecuteCommandOnNode() throws JSchException, SshCommandFailed, BusNotPreparedException {
        ESBDeploymentPreparer preparer = new ESBDeploymentPreparer(node);
        preparer.sshWaiter = waiter;
        preparer.prepareESBDeployment();
        verify(ssh).runCommand(ESBDeploymentPreparer.COMMAND);
    }

    @Test(expected=BusNotPreparedException.class)
    public void shouldNotExecuteCommand() throws JSchException, SshCommandFailed, BusNotPreparedException {
        ESBDeploymentPreparer preparer = new ESBDeploymentPreparer(node);
        preparer.sshWaiter = badWaiter;
        preparer.prepareESBDeployment();
    }
    
}
