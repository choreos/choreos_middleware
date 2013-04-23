package org.ow2.choreos.deployment.services.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import org.ow2.choreos.deployment.services.datamodel.DeployedService;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
import org.ow2.choreos.deployment.services.datamodel.ServiceInstance;
import org.ow2.choreos.deployment.services.datamodel.ServiceType;

public class ServicesResourceTest {

	private ServicesResourceForTest servicesResources;
	private String expectedServiceUUID;

	@Before
	public void setUp() throws ServiceNotDeployedException {

		ServicesManager servicesManagerMock = mock(ServicesManager.class);
		when(servicesManagerMock.createService(getSpec())).thenReturn(
				getService());

		this.servicesResources = new ServicesResourceForTest(
				servicesManagerMock, null);
	}

	private DeployedServiceSpec getSpec() {
		return new DeployedServiceSpec(ServiceType.SOAP,
				PackageType.COMMAND_LINE, null, null,
				"http://choreos.eu/airilne.jar", 1234, "airline", 1);
	}

	private DeployedService getService() {

		DeployedService airline = new DeployedService(getSpec());
		expectedServiceUUID = airline.getSpec().getUUID();
		ServiceInstance instance = new ServiceInstance();
		instance.setInstanceId("1");
		instance.setNativeUri("http://hostname:1234/airline");
		airline.addInstance(instance);
		return airline;
	}

	@Test
	public void shouldCreateService() throws IllegalArgumentException,
			UriBuilderException, URISyntaxException,
			ServiceInstanceNotFoundException {

		String uri = "/services/airline";
		UriBuilder uriBuilder = mock(UriBuilder.class);
		UriInfo uriInfo = mock(UriInfo.class);
		when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
		when(uriBuilder.path(any(Class.class))).thenReturn(uriBuilder);
		when(uriBuilder.path(any(String.class))).thenReturn(uriBuilder);
		when(uriBuilder.build()).thenReturn(new URI(uri));

		Response response = this.servicesResources.deployService(getSpec(),
				uriInfo);

		assertEquals(201, response.getStatus());
		Service entity = (Service) response.getEntity();
		assertEquals(expectedServiceUUID, entity.getSpec().getUUID());
		assertEquals(getSpec(), entity.getSpec());
		assertEquals(1, ((DeployedService) entity).getInstances().size());
		assertEquals(getService().getInstance("1"), ((DeployedService) entity).getInstance("1"));
		assertEquals(uri, response.getMetadata().get("location").get(0));
	}

}
