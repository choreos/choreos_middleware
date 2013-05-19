package org.ow2.choreos.chors.rest;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Encapsulates the reading of the file owners.properties 
 * @author leonardo
 *
 */
public class Owners {

	public static final String DEFAULT = "DEFAULT";
	
	private static String PROPERTIES_FILE = "owners.properties";
	
	private static Logger logger = Logger.getLogger(Owners.class);

	private static Owners INSTANCE = new Owners();

	private final Properties properties = new Properties();

    private Properties getProperties() {
        return properties;
    }

    /**
     * If the value is not found, the DEFAULT value is returned.
     * If both the value of key and DEFAULT are not found, an IllegalArgumentException is thrown.
     * @param key
     * @return
     * @throws IllegalArgumentException
     */
    public static String get(String key) {

    	if (key == null) {
    		key = DEFAULT;
    	}
    	String value = INSTANCE.getProperties().getProperty(key);
    	if (value == null || value.trim().isEmpty()) {
    		value = INSTANCE.getProperties().getProperty(DEFAULT);
    	}
    	
    	if (value == null || value.trim().isEmpty()) {
    		logger.error("Could not retrieve the DeploymentManager for owner " + key 
    				+ ". Please, check the file " + PROPERTIES_FILE);
    		throw new IllegalArgumentException();
    	}
    	
        return value.trim();
    }

    public static void set(String key, String value) {
    	if (key != null) {
    		INSTANCE.getProperties().setProperty(key, value);
    	}
    }

    private Owners() {
    	
        try {
            final ClassLoader loader = this.getClass().getClassLoader();
            final InputStream propFile = loader.getResourceAsStream(PROPERTIES_FILE);
            if (propFile != null) {
            	properties.load(propFile);
            	propFile.close();
            } 
        } catch (IOException e) {
           System.out.println("Could not load the file " + PROPERTIES_FILE);
        }
    }
    

}
