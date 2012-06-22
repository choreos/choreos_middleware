package org.ow2.choreos.utils;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class LogConfigurator {

	private static final String LOG_CONFIG_FILE = "log.properties";
	
	public static void configLog() {
	    	
    	Properties logProperties = new Properties();
    	try {
			logProperties.load(ClassLoader.getSystemResourceAsStream(LOG_CONFIG_FILE));
			PropertyConfigurator.configure(logProperties); 
		} catch (IOException e) {
			System.out.println("Could not load log.properties. Let's use basic log.");
			BasicConfigurator.configure();
		} 
	}
}
