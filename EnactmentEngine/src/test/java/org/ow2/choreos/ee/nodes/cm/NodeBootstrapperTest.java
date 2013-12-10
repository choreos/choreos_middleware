package org.ow2.choreos.ee.nodes.cm;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.ee.config.DeploymentManagerConfiguration;
import org.ow2.choreos.ee.nodes.cm.NodeBootstrapper;
import org.ow2.choreos.ee.nodes.cm.NodeSetup;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.utils.Scp;

public class NodeBootstrapperTest {

    private String initialNodeJsonPath;
    private String prepareDeploymentScriptPath;
    private CloudNode node;
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
    
    @After
    public void tearDown() {
        NodeSetup.testing = false;
        Scp.testing = false;
    }

    @Test
    public void shouldRunBootstrapThroughSshAndSendFiles() throws Exception {
	NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
	bootstrapper.bootstrapNode();
	verify(nodeSetup).setup();
	verify(scp).sendFile(initialNodeJsonPath, NodeBootstrapper.CHEF_SOLO_FOLDER);
	verify(scp).sendFile(prepareDeploymentScriptPath, NodeBootstrapper.CHEF_SOLO_FOLDER);
    }

}
