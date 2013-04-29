package org.ow2.choreos.chors.integration;


import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.chors.ModelsForTest;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * This test will enact a choreography with two services, with a service
 * depending on the other.
 * 
 * Before the test, start the DeploymentManager server
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class WARChorEnactmentTest extends SimpleChorEnactmentTest {

	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}

	@Before
	@Override
	public void setUp() {

		Configuration.set(Option.BUS, "false");
		ModelsForTest models = new ModelsForTest(ServiceType.SOAP,
				PackageType.TOMCAT);
		super.chorSpec = models.getChorSpec();
	}

}
