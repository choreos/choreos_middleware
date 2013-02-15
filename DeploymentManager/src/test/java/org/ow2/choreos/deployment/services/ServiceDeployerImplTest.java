package org.ow2.choreos.deployment.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeCookbook;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.deployment.nodes.ConfigNotAppliedException;
import org.ow2.choreos.deployment.nodes.NodePoolManager;
import org.ow2.choreos.deployment.nodes.datamodel.Config;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.deployment.services.datamodel.ArtifactType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.utils.LogConfigurator;

public class ServiceDeployerImplTest {

	private NodePoolManager npm; 
	private ServiceDeployer serviceDeployer;
	
	private Node selectedNode;
	private ServiceSpec serviceSpec;
	
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
		
		npm = mock(NodePoolManager.class);
		when(npm.applyConfig(any(Config.class))).thenReturn(selectedNode);
	}
	
	private void setUpServiceDeployer() throws KnifeException {
		
		serviceSpec = new ServiceSpec();
		serviceSpec.setName("Airline");
		serviceSpec.setCodeUri("http://choreos.eu/services/airline.jar");
		serviceSpec.setArtifactType(ArtifactType.COMMAND_LINE);
		serviceSpec.setEndpointName("airline");
		serviceSpec.setPort(8042);
		
		Knife knife = mock(Knife.class);
		KnifeCookbook knifeCookbbok = mock(KnifeCookbook.class);
		when(knife.cookbook()).thenReturn(knifeCookbbok);
		String cookbookUploadResult = "Cookbook 'uploaded' by mock";
		when(knifeCookbbok.upload(any(String.class), any(String.class))).thenReturn(cookbookUploadResult);
		
		serviceDeployer = new ServiceDeployerImpl(npm, knife);
	}
	
	@Test
	public void shouldReturnAValidService() throws ConfigNotAppliedException, ServiceNotDeployedException {

		final String EXPECTED_URI = "http://" + selectedNode.getIp() + ":"
				+ serviceSpec.getPort() + "/" + serviceSpec.getEndpointName()
				+ "/";
		
		Service service = serviceDeployer.deploy(serviceSpec);
		
		assertEquals(selectedNode.getHostname(), service.getHost());
		assertEquals(selectedNode.getIp(), service.getIp());
		assertEquals(selectedNode.getId(), service.getNodeId());
		assertEquals(EXPECTED_URI, service.getNativeUri());
		
		verify(npm).applyConfig(any(Config.class));
	}
}
