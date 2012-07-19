package org.ow2.choreos.enactment;

import java.io.IOException;
import java.util.Properties;

public class AirportProperties {

	public static final String[] SERVICES_NAMES = { "Airline",
		"AirlineGroundStaffMID", "Airport", "GroundTransportationCompany",
		"Hotel", "StandAndGateManagement", "Travelagency",
		"WeatherForecastService" };
	
	private static String PROPERTIES_FILE = "airport.properties";
	
	private static AirportProperties INSTANCE = new AirportProperties();

	private final Properties properties = new Properties();

    private Properties getProperties() {
        return properties;
    }

    public static String get(String key) {
        return INSTANCE.getProperties().getProperty(key);
    }

    public static void set(String key, String value) {
        INSTANCE.getProperties().setProperty(key, value);
    }

    private AirportProperties() {
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(PROPERTIES_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
