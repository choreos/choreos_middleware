package org.ow2.choreos.deployment;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

public class Configuration {

	private final PropertiesConfiguration properties;

	private static Configuration INSTANCE = new Configuration();

	private PropertiesConfiguration getProperties() {
		return properties;
	}

	public static String get(String key) {
		if (key == null)
			throw new IllegalArgumentException();
		String value = INSTANCE.getProperties().getString(key);
		if (value != null)
			value.trim();
		return value;
	}

	public static String[] getMultiple(String key) {
		if(key == null)
			throw new IllegalArgumentException();
		String[] values = INSTANCE.getProperties().getStringArray(key);
		for(int i =0; i < values.length; i++)
			values[i] = values[i].trim();
		return values;
	}

	public static void set(String key, String value) {
		INSTANCE.getProperties().setProperty(key, value);
	}
	
	public static void set(String key, String[] values) {
		INSTANCE.getProperties().setProperty(key, values);
	}

	private Configuration() {
		//final ClassLoader loader = Thread.currentThread().getContextClassLoader();
		
		PropertiesConfiguration tmp = null;
		try {
			tmp = new PropertiesConfiguration(new File(ClassLoader.getSystemResource("deployment.properties").getFile()));
		} catch (ConfigurationException e) {
			tmp = null;
			e.printStackTrace();
		} finally {
			properties = tmp;
		}
	}

}
