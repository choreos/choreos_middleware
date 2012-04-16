package eu.choreos.enactment;

import java.io.IOException;
import java.util.HashSet;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.Set;

import eu.choreos.servicedeployer.datamodel.ServiceSpec;

public class POWSRetriever implements ServicesRetriever {

	private static final String SERVICE_TYPE = "JAR";
	private static final String PROPERTIES_FILE = "pows.properties";
	private static final String[] SERVICES_NAMES = { "Airline",
			"AirlineGroundStaffMID", "Airport", "GroundTransportationCompany",
			"Hotel", "StandAndGateManagement", "Travelagency",
			"WeatherForecastService" };
	
	@Override
	public Set<ServiceSpec> retrieve() {

		Properties properties = new Properties();
		try {
			properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
		} catch (IOException e) {
			System.out.println("Cannot load " + PROPERTIES_FILE);
			e.printStackTrace();
		}
		
		Set<ServiceSpec> specs = new HashSet<ServiceSpec>();
		
		for (String name: SERVICES_NAMES) {
			ServiceSpec spec = new ServiceSpec();
			spec.setType(SERVICE_TYPE);
			
			String key = name + ".codeUri";
			String codeUri = properties.getProperty(key); 
			checkNull(key, codeUri);
			
			key = name + ".endpointName";
			String endpointName = properties.getProperty(key);
			checkNull(key, endpointName);
			
			key = name + ".port";
			String port = properties.getProperty(key);
			checkNull(key, port);
			
			spec.setCodeUri(codeUri);
			spec.setEndpointName(endpointName);
			spec.setPort(port);
			
			specs.add(spec);
		}
		
		return specs;
	}

	private void checkNull(String key, String value) {
		if (value == null) {
			throw new MissingResourceException("Missing property " + key + " in "
					+ PROPERTIES_FILE, Properties.class.toString(), key);
		}
	}
}
