package org.ow2.choreos.chors;

import org.junit.Test;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;

/**
 * To run this test, the DeploymentManager must be off
 * 
 * @author leonardo
 * 
 */
public class ChorDeployerTest {

    /**
     * Sometimes we run the ChoreographyDeployer missing to start before the
     * DeploymentManager. This test tests if the ChoreographyDeployer will fail
     * fast in this situation.
     * 
     * @throws ChoreographyNotFoundException
     * @throws EnactmentException
     */
    @Test(expected = EnactmentException.class)
    public void shouldThrowExceptionWhenDeploymentManagerIsNotAvailable() throws EnactmentException,
	    ChoreographyNotFoundException {

	ChoreographyDeployer chorDeployer = new ChoreographyDeployerImpl();
	ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
	ChoreographySpec chorSpec = models.getChorSpec();
	String id = chorDeployer.createChoreography(chorSpec);
	chorDeployer.enactChoreography(id);
    }

}
