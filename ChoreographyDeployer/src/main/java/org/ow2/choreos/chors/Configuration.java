package org.ow2.choreos.chors;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private static String PROPERTIES_FILE = "chordeployer.properties";
	
	public enum Option {
		CHOR_DEPLOYER_PORT, 
		DEPLOYMENT_MANAGER_URI
	};
	
	private static Configuration INSTANCE = new Configuration();

	private final Properties properties = new Properties();

    private Properties getProperties() {
        return properties;
    }

    public static String get(Option key) {
    	if (key == null)
    		throw new IllegalArgumentException();
    	String value = INSTANCE.getProperties().getProperty(key.toString());
    	if (value != null)
    		value.trim();
        return value;

    }

    public static void set(Option key, String value) {
    	if (key == null)
    		throw new IllegalArgumentException();
        INSTANCE.getProperties().setProperty(key.toString(), value);
    }

    private Configuration() {
        try {
            final ClassLoader loader = Thread.currentThread().getContextClassLoader();
            final InputStream propFile = loader.getResourceAsStream(PROPERTIES_FILE);
            properties.load(propFile);
            propFile.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
