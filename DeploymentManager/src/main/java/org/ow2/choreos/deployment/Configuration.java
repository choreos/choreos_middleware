package org.ow2.choreos.deployment;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private final Properties properties = new Properties();

    private static Configuration INSTANCE = new Configuration();

    private Properties getProperties() {
        return properties;
    }

    public static String get(String key) {
    	if (key == null)
    		throw new IllegalArgumentException();
    	String value = INSTANCE.getProperties().getProperty(key);
    	if (value != null)
    		value.trim();
        return value;
    }

    public static void set(String key, String value) {
        INSTANCE.getProperties().setProperty(key, value);
    }

    private Configuration() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            properties.load(loader.getResourceAsStream("deployment.properties"));
        } catch (IOException e) {
            System.out.println("Configuration class did not found deployment.properties resource.");
        }
    }

}
