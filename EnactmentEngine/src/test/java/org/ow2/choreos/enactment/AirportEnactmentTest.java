package org.ow2.choreos.enactment;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.enactment.datamodel.ChorServiceSpec;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.enactment.datamodel.ServiceDependence;
import org.ow2.choreos.servicedeployer.datamodel.Service;
import org.ow2.choreos.servicedeployer.datamodel.ServiceType;
import org.ow2.choreos.utils.LogConfigurator;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.clientgenerator.WSClient;

/**
 * This test will enact the services
 * WeatherForecast and AirlineGroundStaffMID.
 * 
 * Before the test, start the NPMServer and the ServiceDeployerServer
 * 
 * AirlineGroundStaffMID depends on WeatherForecast.
 * Whether these services will be deployed on the same node, or not,
 * depends on Node Pool Manager configurations.
 * Nodes must be already bootstrapped.
 * 
 * @author leonardo
 *
 */
public class AirportEnactmentTest {

	private static final String WEATHER_FORECAST_SERVICE = "WeatherForecastService";
	//private static final String AIRLINE_GROUND_STAFF_MID = "AirlineGroundStaffMID";
	
	private ChorSpec chor;
	
	@BeforeClass
	public static void startServers() {
		LogConfigurator.configLog();
	}
	
	@Before
	public void setUp() {
		
		chor = new ChorSpec();
		for (String serviceName: AirportProperties.SERVICES_NAMES) {
			
			ChorServiceSpec service = new ChorServiceSpec();
			service.setName(serviceName);
			service.setCodeUri(AirportProperties.get(serviceName + ".codeUri"));
			service.setEndpointName(serviceName.toLowerCase());
			int port = Integer.parseInt(AirportProperties.get(serviceName + ".port"));
			service.setPort(port);
			service.getRoles().add(serviceName);
			service.setType(ServiceType.JAR);
			
			List<ServiceDependence> deps = getDependences(serviceName);
			service.setDependences(deps);
			
			chor.addServiceSpec(service);
		}
	}
	
	private List<ServiceDependence> getDependences(String serviceName) {

		List<ServiceDependence> deps = new ArrayList<ServiceDependence>();
		
		String line = AirportProperties.get(serviceName + ".dependences");
		if (line == null)
			return deps;
		
		String[] names = line.split(",");
		for (String name: names) {
			ServiceDependence dep = new ServiceDependence(name, name);
			deps.add(dep);
		}
		
		return deps;
	}

	@Test
	public void shouldEnactChoreography() throws Exception {
		
		EnactmentEngine ee = new EnactEngImpl();
		String chorId = ee.createChoreography(chor);
		Choreography chor = ee.enact(chorId);
		
		Service weather = chor.getDeployedServiceByName(WEATHER_FORECAST_SERVICE);
		testWeather(weather);
		
		//Service groundStaff = deployedServices.get(AIRLINE_GROUND_STAFF_MID);
	}

	private void testWeather(Service weather) throws Exception {
		
		String endpoint = weather.getUri();
		WSClient client = new WSClient(endpoint + "?wsdl");
		
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
