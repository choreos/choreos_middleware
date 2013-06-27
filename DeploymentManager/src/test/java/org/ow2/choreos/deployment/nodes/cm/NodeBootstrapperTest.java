package org.ow2.choreos.deployment.nodes.cm;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Scp;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

public class NodeBootstrapperTest {

    private String bootstrapScript;
    private String initialNodeJsonPath;
    private String prepareDeploymentScriptPath;
    private CloudNode node;
    private SshUtil ssh;
    private SshWaiter waiter;
    private Scp scp;

    @Before
    public void setUp() throws Exception {
	setFiles();
	setNode();
	setSsh();
	setScp();
    }
    
    private void setFiles() throws IOException {
	URL url = this.getClass().getClassLoader().getResource(NodeBootstrapper.BOOTSTRAP_SCRIPT);
	bootstrapScript = FileUtils.readFileToString(new File(url.getFile()));
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

    @Test
    public void shouldRunBootstrapThroughSshAndSendFiles() throws Exception {
	NodeBootstrapper bootstrapper = new NodeBootstrapper(new CloudNode());
	bootstrapper.sshWaiter = waiter;
	bootstrapper.bootstrapNode();
	verify(ssh).runCommand(bootstrapScript);
	verify(scp).sendFile(initialNodeJsonPath, NodeBootstrapper.CHEF_SOLO_FOLDER);
	verify(scp).sendFile(prepareDeploymentScriptPath, NodeBootstrapper.CHEF_SOLO_FOLDER);
    }

}
