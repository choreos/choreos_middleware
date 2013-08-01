package org.ow2.choreos.deployment.services.preparer;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.nodes.cm.NodePreparer;
import org.ow2.choreos.deployment.nodes.cm.NodePreparers;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdater;
import org.ow2.choreos.deployment.nodes.cm.NodeUpdaters;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class InstanceDeploymentPreparerTest {

    private static final String SERVICE_UUID = "1";
    
    private ModelsForTest models;
    private CloudNode node;
    private NodePreparer nodePreparer;
    private NodeUpdater nodeUpdater;

    @Before
    public void setUp() throws Exception {
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        setNode();
        setPreparer();
        setUpdater();
    }
    
    @After
    public void tearDown() {
        NodeUpdaters.testing = false;
        NodePreparers.testing = false;
    }
    
    private void setNode() {
        node = new CloudNode();
        node.setId("1");
        node.setIp("192.168.56.101");
        node.setUser("ubuntu");
        node.setPrivateKey("ubuntu.pem");
    }
    
    private void setPreparer() {
        nodePreparer = mock(NodePreparer.class);
        NodePreparers.preparerForTest = nodePreparer;
        NodePreparers.testing = true;
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
        instanceDeploymentPreparer.prepareDeployment();
        verify(nodePreparer).prepareNodeForDeployment(spec.getPackageUri(), "jar");
        verify(nodeUpdater).addHandler(any(InstanceCreatorUpdateHandler.class));
    }

}
