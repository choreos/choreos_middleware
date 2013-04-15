package org.ow2.choreos.deployment.services.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.deployment.services.ServiceNotDeployedException;
import org.ow2.choreos.deployment.services.ServicesManager;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

public class ServicesResourceTest {
	
	private ServicesResource servicesResources;
	
	@Before
	public void setUp() throws ServiceNotDeployedException {
		
		ServicesManager servicesManagerMock = mock(ServicesManager.class);
		when(servicesManagerMock.createService(getSpec())).thenReturn(getService());
		
		this.servicesResources = new ServicesResource(servicesManagerMock, null);
	}
	
	private ServiceSpec getSpec() {
		
		ServiceSpec airlineSpec = new ServiceSpec();
		airlineSpec.setName("airline");
		airlineSpec.setPackageUri("http://choreos.eu/airilne.jar");
		airlineSpec.setType(ServiceType.SOAP);
		airlineSpec.setEndpointName("airline");
		airlineSpec.setPort(1234);
		airlineSpec.setPackageType(PackageType.COMMAND_LINE);
		return airlineSpec;
	}
	
	private Service getService() {

		Service airline = new Service();
		airline.setName("airline");
		airline.setSpec(getSpec());
		
		ServiceInstance instance = new ServiceInstance();
		instance.setInstanceId("1");
		instance.setNativeUri("http://hostname:1234/airline");

		airline.addInstance(instance);
		
		return airline;
	}
	
	@Test
	public void shouldCreateService() throws IllegalArgumentException, UriBuilderException, URISyntaxException, ServiceInstanceNotFoundException {
		
		String uri = "/services/airline";
		UriBuilder uriBuilder = mock(UriBuilder.class);
		UriInfo uriInfo = mock(UriInfo.class);
		when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
		when(uriBuilder.path(any(Class.class))).thenReturn(uriBuilder);
		when(uriBuilder.path(any(String.class))).thenReturn(uriBuilder);
		when(uriBuilder.build()).thenReturn(new URI(uri));
		
		Response response = this.servicesResources.createService(getSpec(), uriInfo);
		
		assertEquals(201, response.getStatus());
		Service entity = (Service) response.getEntity();
		assertEquals("airline", entity.getName());
		assertEquals(getSpec(), entity.getSpec());
		assertEquals(1, entity.getInstances().size());
		assertEquals(getService().getInstance("1"), entity.getInstance("1"));
		assertEquals(uri, response.getMetadata().get("location").get(0));
	}

}
