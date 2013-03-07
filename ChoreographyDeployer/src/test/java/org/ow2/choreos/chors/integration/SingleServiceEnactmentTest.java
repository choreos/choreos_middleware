package org.ow2.choreos.chors.integration;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chors.ChorDeployerImpl;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.deployment.services.datamodel.PackageType;
import org.ow2.choreos.deployment.services.datamodel.Service;
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
	private static final String WEATHER_FORECAST_CODE_URI = "http://valinhos.ime.usp.br:54080/demo2/jars/choreos-usecase-1.0-SNAPSHOT-WeatherForecast.jar";
	private static final int WEATHER_FORECAST_PORT = 8192;
	
	private ChorSpec chor;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() {
		
		chor = new ChorSpec();
		ServiceSpec service = new ServiceSpec();
		service.setName(WEATHER_FORECAST_SERVICE);
	//	service.setCodeUri(AirportProperties.get(WEATHER_FORECAST_SERVICE + ".codeUri"));
		service.setEndpointName(WEATHER_FORECAST_SERVICE.toLowerCase());
		int port = WEATHER_FORECAST_PORT;
		service.setPort(port);
		service.getRoles().add(WEATHER_FORECAST_SERVICE);
		service.setPackageType(PackageType.COMMAND_LINE);
		chor.addServiceSpec(service);
	}
	
	@Test
	public void shouldEnactChoreography() throws Exception {
		
		ChoreographyDeployer ee = new ChorDeployerImpl();
		String chorId = ee.createChoreography(chor);
		Choreography chor = ee.enact(chorId);
		
		Service weather = chor.getDeployedServiceByName(WEATHER_FORECAST_SERVICE);
		WSClient client = new WSClient(""/*weather.getUri()*/ + "?wsdl");
		
		Item request = new ItemImpl("getWeatherAt");
		request.addChild("where").setContent("Paris");
		request.addChild("date").setContent("Today");
		Item response = client.request("getWeatherAt", request);
		String humidity = response.getChild("return").getChild("humidityPerc").getContent();
		String temperature = response.getChild("return").getChild("temperatureC").getContent();
		
		assertEquals("50", humidity);
		assertEquals("35", temperature);
	}

}
