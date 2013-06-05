package org.ow2.choreos.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class LogConfigurator {

    private static final String LOG_CONFIG_FILE = "log.properties";

    public static void configLog() {

	final ClassLoader loader = Thread.currentThread().getContextClassLoader();
	Properties logProperties = new Properties();
	try {
	    InputStream is = loader.getResourceAsStream(LOG_CONFIG_FILE);
	    if (is != null) {
		logProperties.load(is);
		PropertyConfigurator.configure(logProperties);
	    } else {
		basicConfiguration();
	    }
	} catch (IOException e) {
	    basicConfiguration();
	}
    }

    public static void basicConfiguration() {
	System.out.println("Let's use basic log.");
	BasicConfigurator.configure();
    }
}
