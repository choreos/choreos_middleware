package org.ow2.choreos.deployment.services.preparer;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdater;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdaters;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

public class InstanceDeploymentPreparerTest {

    private static final String SERVICE_UUID = "1";
    private static final String RANDON_INSTANCE_ID = "2";
    private static final String COMMAND_EXCERPT = "prepare_deployment";
    
    private ModelsForTest models;
    private SshUtil ssh;
    private SshWaiter waiter;
    private CloudNode node;
    private NodeUpdater nodeUpdater;

    @Before
    public void setUp() throws Exception {
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        setSsh();
        setNode();
        setUpdater();
    }
    
    @After
    public void tearDown() {
        NodeUpdaters.testing = false;
    }
    
    private void setSsh() throws Exception {
        ssh = mock(SshUtil.class);
        when(ssh.runCommand(Mockito.contains(COMMAND_EXCERPT))).thenReturn(RANDON_INSTANCE_ID);
        waiter = mock(SshWaiter.class);
        when(waiter.waitSsh(anyString(), anyString(), anyString(), anyInt())).thenReturn(ssh);
    }

    private void setNode() {
        node = new CloudNode();
        node.setIp("192.168.56.101");
        node.setUser("ubuntu");
        node.setPrivateKey("ubuntu.pem");
    }
    
    private void setUpdater() {
        nodeUpdater = mock(NodeUpdater.class);
        NodeUpdaters.updaterForTest = nodeUpdater;
        NodeUpdaters.testing = true;
    }
    
    @Test
    public void shouldRunDeploymentPrepareOnNodeAndAddHandler() throws Exception {
        DeployableServiceSpec spec = models.getAirlineSpec();
        InstanceDeploymentPreparer instanceDeploymentPreparer = new InstanceDeploymentPreparer(spec, SERVICE_UUID, node);
        instanceDeploymentPreparer.sshWaiter = waiter;
        instanceDeploymentPreparer.prepareDeployment();
        verify(ssh).runCommand(Mockito.contains(COMMAND_EXCERPT));
        verify(nodeUpdater).addHandler(any(InstanceCreatorUpdateHandler.class));
    }

}
