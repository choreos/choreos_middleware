package eu.choreos.enactment;

import java.io.IOException;
import java.util.Properties;

public class Configuration {

    private final Properties properties = new Properties();
    private static String CONFIGURATION_FILE= "enactment.properties";

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
        try {
            properties.load(ClassLoader.getSystemResourceAsStream(CONFIGURATION_FILE));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
