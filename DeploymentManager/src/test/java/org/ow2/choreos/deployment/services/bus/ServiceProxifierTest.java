package org.ow2.choreos.deployment.services.bus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public class ServiceProxifierTest {

	private static final String AIRLINE_JAR = "http://valinhos.ime.usp.br:54080/chordeployer/samples/v1-2/airline-service.jar";
	private static final String PROXIFIED_ADDRESS = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";
	
	
	private ServiceInstance getServiceInstance() {
		
		ServiceSpec airlineSpec = new ServiceSpec();
		airlineSpec.setName("airline");
		airlineSpec.setPackageUri(AIRLINE_JAR);
		airlineSpec.setType(ServiceType.SOAP);
		airlineSpec.setEndpointName("airline");
		airlineSpec.setPort(1234);
		airlineSpec.setPackageType(PackageType.COMMAND_LINE);
		
		ServiceInstance service = new ServiceInstance();
		service.setNativeUri("http://localhost:1234/airline/");
		service.setMyParentServiceSpec(airlineSpec);
		
		return service;
	}
	
	private EasyESBNode getEsbNode() throws ManagementException {
		
		EasyESBNode esbNode = mock(EasyESBNodeImpl.class);
		when(esbNode.proxifyService(any(String.class), any(String.class)))
				.thenReturn(PROXIFIED_ADDRESS);
		return esbNode;
	}
	
	@Test
	public void test() throws ManagementException {
		
		ServiceInstance svc = this.getServiceInstance();
		EasyESBNode esbNode = this.getEsbNode();
		
		ServiceProxifier proxifier = new ServiceProxifier();
		String proxifiedAddress = proxifier.proxify(svc, esbNode);
		assertEquals(PROXIFIED_ADDRESS, proxifiedAddress);
	}

}
