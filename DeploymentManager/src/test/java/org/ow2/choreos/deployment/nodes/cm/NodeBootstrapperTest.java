package org.ow2.choreos.deployment.nodes.cm;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URL;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Scp;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

public class NodeBootstrapperTest {

    private String initialNodeJsonPath;
    private String prepareDeploymentScriptPath;
    private CloudNode node;
    private SshUtil ssh;
    private SshWaiter waiter;
    private Scp scp;
    private NodeSetup nodeSetup;

    @BeforeClass
    public static void setConfiguration() {
	DeploymentManagerConfiguration.set("HARAKIRI", "false");
	DeploymentManagerConfiguration.set("MONITORING", "false");
    }

    @Before
    public void setUp() throws Exception {
	setFiles();
	setNode();
	setSsh();
	setScp();
	setNodeSetup();
    }

    private void setFiles() throws IOException {
	URL url = this.getClass().getClassLoader().getResource(NodeBootstrapper.BOOTSTRAP_SCRIPT);
	url = this.getClass().getClassLoader().getResource(NodeBootstrapper.INITIAL_NODE_JSON);
	initialNodeJsonPath = url.getFile();
	url = this.getClass().getClassLoader().getResource(NodeBootstrapper.PREPARE_DEPLOYMENT_SCRIPT);
	prepareDeploymentScriptPath = url.getFile();
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

    private void setScp() {
	scp = mock(Scp.class);
	Scp.scpForTest = scp;
	Scp.testing = true;
    }

    private void setNodeSetup() {
	nodeSetup = mock(NodeSetup.class);
	NodeSetup.setupForTest = nodeSetup;
	NodeSetup.testing = true;
    }

    @Test
    public void shouldRunBootstrapThroughSshAndSendFiles() throws Exception {
	NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
	bootstrapper.sshWaiter = waiter;
	bootstrapper.bootstrapNode();
	verify(nodeSetup).setup();
	verify(scp).sendFile(initialNodeJsonPath, NodeBootstrapper.CHEF_SOLO_FOLDER);
	verify(scp).sendFile(prepareDeploymentScriptPath, NodeBootstrapper.CHEF_SOLO_FOLDER);
    }

}
