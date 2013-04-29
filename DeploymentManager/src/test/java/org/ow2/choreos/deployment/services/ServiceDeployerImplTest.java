package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeCookbook;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.utils.LogConfigurator;

public class ServiceDeployerImplTest {

	private NodePoolManager npm; 
	private ServicesManager serviceDeployer;
	
	private Node selectedNode;
	private DeployableServiceSpec serviceSpec;
	
	@Before
	public void setUp() throws ConfigNotAppliedException, KnifeException {
	
		LogConfigurator.configLog();
		setUpNPM();
		setUpServiceDeployer();
	}
	
	private void setUpNPM() throws ConfigNotAppliedException {
		
		selectedNode = new Node();
		selectedNode.setId("1");
		selectedNode.setIp("192.168.56.102");
		selectedNode.setHostname("CHOREOS-NODE");
		
		List<Node> selectedNodes = new ArrayList<Node>();
		selectedNodes .add(selectedNode);
		
		npm = mock(NodePoolManager.class);
		when(npm.applyConfig(any(Config.class))).thenReturn(selectedNodes);
	}
	
	private void setUpServiceDeployer() throws KnifeException {
		
		serviceSpec = new DeployableServiceSpec();
		serviceSpec.setPackageUri("http://choreos.eu/services/airline.jar");
		serviceSpec.setPackageType(PackageType.COMMAND_LINE);
		serviceSpec.setEndpointName("airline");
		serviceSpec.setPort(8042);
		
		Knife knife = mock(Knife.class);
		KnifeCookbook knifeCookbbok = mock(KnifeCookbook.class);
		when(knife.cookbook()).thenReturn(knifeCookbbok);
		String cookbookUploadResult = "Cookbook 'uploaded' by mock";
		when(knifeCookbbok.upload(any(String.class), any(String.class))).thenReturn(cookbookUploadResult);
		
		serviceDeployer = new ServicesManagerImplForTests(npm, knife);
	}
	
	@Test
	public void shouldReturnAValidService() throws ConfigNotAppliedException, ServiceNotDeployedException {

		final String EXPECTED_URI = "http://" + selectedNode.getIp() + ":"
				+ serviceSpec.getPort() + "/" + serviceSpec.getEndpointName()
				+ "/";
		
		DeployableService service = serviceDeployer.createService(serviceSpec);
		
		ServiceInstance instance = service.getInstances().get(0);
		
		assertEquals(selectedNode.getHostname(), instance.getNode().getHostname());
		assertEquals(selectedNode.getIp(), instance.getNode().getIp());
		assertEquals(selectedNode.getId(), instance.getNode().getId());
		assertEquals(EXPECTED_URI, instance.getNativeUri());
		
		verify(npm).applyConfig(any(Config.class));
	}
	
	
	class ServicesManagerImplForTests extends ServicesManagerImpl {
		
		ServicesManagerImplForTests(NodePoolManager npm, Knife k) {
			super(npm);
			knife = k;
		}
	}
}
