package org.ow2.choreos.chors;

import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * To run this test, the DeploymentManager must be off
 * @author leonardo
 *
 */
public class ChorDeployerTest {

	@BeforeClass
	public static void beforeClass() {
		LogConfigurator.configLog();
	}
	
	/**
	 * Sometimes we run the ChoreographyDeployer missing to start before the DeploymentManager.
	 * This test tests if the ChoreographyDeployer will fail fast in this situation.
	 * @throws ChoreographyNotFoundException 
	 * @throws EnactmentException 
	 */
	@Test(expected=EnactmentException.class)
	public void shouldThrowExceptionWhenDeploymentManagerIsNotAvailable() throws EnactmentException, ChoreographyNotFoundException {
		
		ChoreographyDeployer chorDeployer = new ChorDeployerImpl();
		ModelsForTest models = new ModelsForTest(ArtifactType.COMMAND_LINE);
		ChorSpec chorSpec = models.getChorSpec();
		String id = chorDeployer.createChoreography(chorSpec);
		chorDeployer.enact(id);
	}

}
