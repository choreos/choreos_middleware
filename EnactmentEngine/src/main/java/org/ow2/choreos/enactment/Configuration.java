package org.ow2.choreos.enactment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private static String PROPERTIES_FILE = "enactmentengine.properties";
	
	public enum Option {
		ENACTMENT_ENGINE_PORT, 
		NODE_POOL_MANAGER_URI, 
		SERVICE_DEPLOYER_URI
	};
	
	private static Configuration INSTANCE = new Configuration();

	private final Properties properties = new Properties();

    private Properties getProperties() {
        return properties;
    }

    public static String get(Option key) {
    	if (key == null)
    		throw new IllegalArgumentException();
        return INSTANCE.getProperties().getProperty(key.toString());
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
