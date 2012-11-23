package org.ow2.choreos;

import java.io.IOException;
import java.util.Properties;

public class Locations {
	
	private final String FILE_NAME = "locations_test.properties";

    private final Properties properties = new Properties();

    private static Locations INSTANCE = new Locations();

    private Properties getProperties() {
        return properties;
    }

    public static String get(String key) {
        return INSTANCE.getProperties().getProperty(key);
    }

    public static void set(String key, String value) {
        INSTANCE.getProperties().setProperty(key, value);
    }

    private Locations() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            properties.load(loader.getResourceAsStream(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
