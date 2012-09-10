package org.ow2.choreos.servicedeployer;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private final Properties properties = new Properties();

    private static Configuration INSTANCE = new Configuration();

    private Properties getProperties() {
        return properties;
    }

    public static String get(String key) {
        return INSTANCE.getProperties().getProperty(key);
    }

    public static void set(String key, String value) {
        INSTANCE.getProperties().setProperty(key, value);
    }

    private Configuration() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            properties.load(loader.getResourceAsStream("servicedeployer.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
