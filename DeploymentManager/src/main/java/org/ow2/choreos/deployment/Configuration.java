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
        return INSTANCE.getProperties().getProperty(key).trim();
    }

    public static void set(String key, String value) {
        INSTANCE.getProperties().setProperty(key, value);
    }

    private Configuration() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            properties.load(loader.getResourceAsStream("deployment.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
