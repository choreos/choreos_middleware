package org.ow2.choreos.ee.services.update;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.ee.services.preparer.ServiceDeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceDeploymentPreparerFactory;
import org.ow2.choreos.ee.services.update.IncreaseNumberOfReplicas;
import org.ow2.choreos.ee.services.update.UpdateActionFailedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

import com.google.common.collect.Sets;

public class IncreaseNumberOfReplicasTest {

    private ServiceDeploymentPreparer deploymentPreparer;
    private CloudNode node;
    private DeployableService airlineService;

    @Before
    public void setUp() throws Exception {
        setNode();
        setPreparer();
        setModels();
    }

    private void setNode() {
        node = new CloudNode();
        node.setId("unik");
        node.setIp("192.168.56.101");
        node.setUser("ubuntu");
        node.setPrivateKey("ubuntu.pem");
    }

    private void setPreparer() throws PrepareDeploymentFailedException {
        deploymentPreparer = mock(ServiceDeploymentPreparer.class);
        when(deploymentPreparer.prepareDeployment()).thenReturn(Sets.newHashSet(node));
        ServiceDeploymentPreparerFactory.preparerForTest = deploymentPreparer;
        ServiceDeploymentPreparerFactory.testing = true;
    }

    private void setModels() {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        airlineService = models.getAirlineService();
    }

    @After
    public void tearDown() {
        ServiceDeploymentPreparerFactory.testing = false;
    }

    @Test
    public void test() throws UpdateActionFailedException {

        DeployableServiceSpec newSpec = airlineService.getSpec().clone();
        newSpec.setNumberOfInstances(2);

        assertEquals(1, airlineService.getSelectedNodes().size());
        CloudNode originalNode = airlineService.getSelectedNodes().iterator().next();

        IncreaseNumberOfReplicas action = new IncreaseNumberOfReplicas(airlineService, newSpec);
        action.applyUpdate();

        assertEquals(newSpec, airlineService.getSpec());
        assertEquals(2, airlineService.getSelectedNodes().size());
        boolean foundOriginal = false;
        boolean foundNewNode = false;
        for (CloudNode node : airlineService.getSelectedNodes()) {
            if (node.equals(originalNode))
                foundOriginal = true;
            if (node.equals(node))
                foundNewNode = true;
        }
        assertTrue(foundOriginal);
        assertTrue(foundNewNode);

    }

}
