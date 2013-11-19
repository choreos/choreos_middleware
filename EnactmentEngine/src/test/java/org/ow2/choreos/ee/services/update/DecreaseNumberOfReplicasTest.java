package org.ow2.choreos.ee.services.update;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceUndeploymentPreparerFactory;
import org.ow2.choreos.ee.services.update.DecreaseNumberOfReplicas;
import org.ow2.choreos.ee.services.update.UpdateActionFailedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class DecreaseNumberOfReplicasTest {

    private ServiceUndeploymentPreparer undeploymentPreparer;
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
	undeploymentPreparer = mock(ServiceUndeploymentPreparer.class);
	ServiceUndeploymentPreparerFactory.preparerForTest = undeploymentPreparer;
	ServiceUndeploymentPreparerFactory.testing = true;
    }

    private void setModels() {
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 3);
	airlineService = models.getAirlineService();
    }

    @Test
    public void test() throws UpdateActionFailedException {

	DeployableServiceSpec newSpec = airlineService.getSpec().clone();
	newSpec.setNumberOfInstances(2);

	DecreaseNumberOfReplicas action = new DecreaseNumberOfReplicas(airlineService, newSpec);
	action.applyUpdate();

	assertEquals(newSpec, airlineService.getSpec());

	ServiceUndeploymentPreparerFactory.testing = false;
    }

}
