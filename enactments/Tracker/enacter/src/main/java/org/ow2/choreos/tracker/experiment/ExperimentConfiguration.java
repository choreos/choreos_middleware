package org.ow2.choreos.tracker.experiment;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * @author leonardo, alessio
 *
 */
public class ExperimentConfiguration {

	private static String PROPERTIES_FILE = "experiment.properties";
	
	private static ExperimentConfiguration INSTANCE = new ExperimentConfiguration();

	private final Properties properties = new Properties();

    private Properties getProperties() {
        return properties;
    }

    public static String get(String key) {
    	if (key == null)
    		throw new IllegalArgumentException();
    	String value = INSTANCE.getProperties().getProperty(key.toString());
    	if (value != null)
    		value.trim();
        return value;

    }

    public static void set(String key, String value) {
    	if (key == null)
    		throw new IllegalArgumentException();
        INSTANCE.getProperties().setProperty(key.toString(), value);
    }

    private ExperimentConfiguration() {
    	
        try {
            final ClassLoader loader = this.getClass().getClassLoader();
            final InputStream propFile = loader.getResourceAsStream(PROPERTIES_FILE);
            if (propFile != null) {
            	properties.load(propFile);
            	propFile.close();
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
