package eu.choreos.servicedeployer;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.servicedeployer.datamodel.ResourceImpact;
import eu.choreos.servicedeployer.datamodel.Service;
import eu.choreos.servicedeployer.datamodel.ServiceSpec;
import eu.choreos.servicedeployer.utils.CommandLineInterfaceHelper;

public class ServiceDeployerTest {

	private WebClient client;
	protected ServiceDeployer deployer;
	protected static ServiceSpec specWar = new ServiceSpec();
	protected static ServiceSpec specWild = new ServiceSpec();
	protected static ServiceSpec specBpel = new ServiceSpec();
	private static ResourceImpact resourceImpact = new ResourceImpact();
	protected Service service1;
	protected Service service2;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		resourceImpact.setCpu("low");
		resourceImpact.setIo("low");
		resourceImpact.setMemory("low");
		resourceImpact.setRegion("BR");

		specWar.setCodeUri("http://content.hccfl.edu/pollock/AJava/WAR/myServletWAR.war");
		specWar.setType("WAR");
		specWar.setResourceImpact(resourceImpact);

		specBpel.setCodeUri("http://content.hccfl.edu/pollock/AJava/WAR/myServletWAR.war");
		specBpel.setType("BPEL");
		specBpel.setResourceImpact(resourceImpact);

		specWild.setCodeUri("http://content.hccfl.edu/pollock/AJava/WAR/myServletWAR.war");
		specWild.setType("WILD");
		specWild.setResourceImpact(resourceImpact);
	}

	@Before
	public void setUp() throws Exception {
		deployer = new ServiceDeployer();
		(new CommandLineInterfaceHelper())
				.runLocalCommand("knife node run_list remove choreos-node recipe[servicemyServletWAR] -c " + Configuration.get("CHEF_CONFIG_FILE"));
		(new CommandLineInterfaceHelper())
				.runLocalCommand("ssh root@choreos-node chef-client");
	}

	@Test
	public void shouldStoreWarFileInWebAppsFolder() throws Exception {
		(new CommandLineInterfaceHelper())
				.runLocalCommand("ssh root@choreos-node rm -rf "
						+ "/var/lib/tomcat6/webapps/myServletWAR*");
		service1 = new Service(specWar);

		service1.setUri("http://this.should.not.work/");
		service1.setId("myServletWAR");

		String url = deployer.deploy(service1);
		assertEquals("http://choreos-node:8080/servicemyServletWARDeploy/", url);

		assertTrue((new CommandLineInterfaceHelper())
		.runLocalCommand("ssh root@choreos-node ls "
				+ "/var/lib/tomcat6/webapps/").contains("servicemyServletWARDeploy"));

	}

	@Test
	public void shouldDeployAWarServiceInANode() throws Exception {
		(new CommandLineInterfaceHelper())
				.runLocalCommand("ssh root@choreos-node rm -rf "
						+ "/var/lib/tomcat6/webapps/myServletWAR*");
		service1 = new Service(specWar);

		service1.setUri("http://this.should.not.work/");
		service1.setId("myServletWAR");

		String url = deployer.deploy(service1);
		assertEquals("http://choreos-node:8080/servicemyServletWARDeploy/", url);

		Thread.sleep(15000);
		client = WebClient.create(url);
		assertEquals(200, client.get().getStatus());
	}

	@Test
	public void shouldDeployABPELServiceInANode() throws MalformedURLException {
		// service1 = new Service(specBpel);
		//
		// service1.setUri("http://this.should.not.work/");
		// service1.setId("servicemyServletWAR");
		//
		// deployer.deploy(service1);
		//
		// client = WebClient.create(service1.getUri());
		// assertEquals(200, client.get().getStatus());
	}

	@Test
	public void shouldDeployAWildServiceInANode() throws MalformedURLException {
		// service1 = new Service(specWild);
		//
		// service1.setUri("http://this.should.not.work/");
		// service1.setId("servicemyServletWAR");
		//
		// deployer.deploy(service1);
		//
		// client = WebClient.create(service1.getUri());
		// assertEquals(200, client.get().getStatus());
	}

	@Test
	public void shouldGetServiceFromServiceID() throws MalformedURLException {
		// service1 = new Service(specWar);
		// service1.setId("servicemyServletWAR");
		// deployer.deploy(service1);
		//
		// assertEquals(service1, deployer.getService("servicemyServletWAR"));
	}

	@Test
	public void shouldGetSpecificServiceFromAServiceGroupGivenTheServiceID()
			throws MalformedURLException {
		// service1 = new Service(specWar);
		// service1.setId("servicemyServletWAR");
		// deployer.deploy(service1);
		//
		// service2 = new Service(specWar);
		// service2.setId("servicemyServletWAR");
		// deployer.deploy(service1);
		//
		// assertEquals(service1, deployer.getService("servicemyServletWAR"));
	}

	@Test
	public void shouldUpdateAService() {
		// fail("Not yet implemented");
	}

	@Test
	public void shouldUpdateTheCorrectServiceGivenItsID() {
		// fail("Not yet implemented");
	}

	@Test
	public void shouldDeleteAService() throws MalformedURLException {
		// service1 = new Service(specWar);
		// service1.setId("servicemyServletWAR");
		// service1.setUri("http://this.should.not.work/");
		//
		// deployer.deploy(service1);
		//
		// service2 = new Service(specWar);
		// service2.setId("servicemyServletWAR");
		// service2.setUri("http://this.should.not.work/");
		//
		// deployer.deploy(service1);
		// deployer.deleteService("servicemyServletWAR");
		//
		// assertTrue(deployer.getService("servicemyServletWAR") == null);

		// client = WebClient.create(service1.getUri());
		// assertEquals(200, client.get().getStatus());
	}

	@Test
	public void ShouldOnlyDeleteTheCorrectService()
			throws MalformedURLException {
		// service1 = new Service(specWar);
		// service1.setId("servicemyServletWAR");
		// deployer.deploy(service1);
		//
		// service2 = new Service(specWar);
		// service2.setId("servicemyServletWAR");
		// deployer.deploy(service1);
		//
		// deployer.deleteService("servicemyServletWAR");
		// assertTrue("Did not delete the requested node",
		// deployer.getService("servicemyServletWAR") == null);
		// assertTrue("Deleted an unrequested node or all of them",
		// deployer.getService("servicemyServletWAR") != null);
	}

	private static void deleteDirectory() {
		URL fileLocation = ClassLoader
				.getSystemResource("chef/serviceservicemyServletWAR");
		if (fileLocation != null)
			FileUtils.deleteQuietly(new File(fileLocation.getFile()));
		fileLocation = ClassLoader
				.getSystemResource("chef/serviceservicemyServletWAR");
		if (fileLocation != null)
			FileUtils.deleteQuietly(new File(fileLocation.getFile()));
	}
}
