package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyDeployerImpl;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.chors.datamodel.ChoreographyServiceSpec;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.deployment.services.datamodel.DeployedServiceSpec;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.ServiceSpec;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test enacts a choreography of a single service.
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer.
 * 
 * @author leonardo
 * 
 */
@Category(IntegrationTest.class)
public class SingleServiceEnactmentTest {

	private static final String WEATHER_FORECAST_SERVICE = "WeatherForecastService";
	private static final int WEATHER_FORECAST_PORT = 8192;

	private ChoreographySpec chorSpec;

	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}

	@Before
	public void setUp() {

		chorSpec = new ChoreographySpec();

		ServiceSpec serviceSpec = new DeployedServiceSpec();
		((DeployedServiceSpec) serviceSpec)
				.setEndpointName(WEATHER_FORECAST_SERVICE.toLowerCase());
		((DeployedServiceSpec) serviceSpec).setPort(WEATHER_FORECAST_PORT);
		serviceSpec.setPackageType(PackageType.COMMAND_LINE);

		List<String> roles = new ArrayList<String>();
		roles.add(WEATHER_FORECAST_SERVICE);
		ChoreographyServiceSpec chorServiceSpec = new ChoreographyServiceSpec(
				serviceSpec, "owner1", "group1", roles,
				WEATHER_FORECAST_SERVICE);

		chorSpec.addChoreographyServiceSpec(chorServiceSpec);
	}

	@Test
	public void shouldEnactChoreography() throws Exception {

		ChoreographyDeployer ee = new ChoreographyDeployerImpl();
		String chorId = ee.createChoreography(chorSpec);
		Choreography chor = ee.enactChoreography(chorId);

		ChoreographyService weather = chor
				.getDeployedChoreographyServiceByChoreographyServiceUID(WEATHER_FORECAST_SERVICE);
		WSClient client = new WSClient(weather.getService().getUris().get(0)
				+ "?wsdl");

		Item request = new ItemImpl("getWeatherAt");
		request.addChild("where").setContent("Paris");
		request.addChild("date").setContent("Today");
		Item response = client.request("getWeatherAt", request);
		String humidity = response.getChild("return").getChild("humidityPerc")
				.getContent();
		String temperature = response.getChild("return")
				.getChild("temperatureC").getContent();

		assertEquals("50", humidity);
		assertEquals("35", temperature);
	}

}
