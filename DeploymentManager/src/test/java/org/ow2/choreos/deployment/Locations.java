/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment;

import java.io.IOException;
import java.util.Properties;

public class Locations {

    private final String FILE_NAME = "locations_test.properties";

    private final Properties properties = new Properties();

    private static Locations INSTANCE = new Locations();

    private Properties getProperties() {
        return properties;
    }

    public static String get(String key) {
        return INSTANCE.getProperties().getProperty(key);
    }

    public static void set(String key, String value) {
        INSTANCE.getProperties().setProperty(key, value);
    }

    private Locations() {
        final ClassLoader loader = Thread.currentThread().getContextClassLoader();

        try {
            properties.load(loader.getResourceAsStream(FILE_NAME));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
