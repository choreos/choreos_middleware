/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import java.io.File;
import java.net.URL;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.log4j.Logger;

/**
 * Load configuration from a property file.
 * 
 * @author leonardo, thiago, alessio
 * 
 */
public class Configuration {

    private final PropertiesConfiguration properties;

    private static Logger logger = Logger.getLogger(Configuration.class);

    /**
     * The file must be on the resources folder.
     * 
     * @param filePath
     *            relative to resources folder.
     */
    public Configuration(String resourceName) {

        PropertiesConfiguration tmp = null;
        try {
            URL fileUrl = this.getClass().getClassLoader().getResource(resourceName);
            if (fileUrl != null) {
                File propertiesFile = new File(fileUrl.getFile());
                tmp = new PropertiesConfiguration(propertiesFile);
            } else {
                logger.error("Could not load properties from " + resourceName);
                tmp = new PropertiesConfiguration();
            }
        } catch (ConfigurationException e) {
            logger.error("Could not load properties from " + resourceName);
            tmp = new PropertiesConfiguration();
        }
        this.properties = tmp;
    }

    public String get(String key) {
        if (key == null)
            throw new IllegalArgumentException();
        String value = properties.getString(key);
        return process(key, value);
    }

    private String process(String key, String value) {
        if (value != null) {
            if (value.contains("#")) {
                logger.warn("The value for " + key
                        + " property contains '#'. Beware that inline comments are not allowed in property files!");
            }
            return value.trim();
        } else {
            return value;
        }
    }

    public String[] getMultiple(String key) {
        if (key == null)
            throw new IllegalArgumentException();
        String[] values = properties.getStringArray(key);
        for (int i = 0; i < values.length; i++) {
            values[i] = process(key, values[i]);
        }
        return values;
    }

    public void set(String key, String value) {
        if (key != null)
            properties.setProperty(key, value);
    }

    public void set(String key, String[] values) {
        if (key != null)
            properties.setProperty(key, values);
    }

    public void save() {
        if (properties != null) {
            try {
                properties.save();
            } catch (ConfigurationException e) {
                logger.error("Coud not save properties on " + properties.getFileName());
            }
        }
    }

}
