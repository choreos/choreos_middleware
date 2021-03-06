/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.config;

import org.ow2.choreos.utils.Configuration;

/**
 * 
 * @author leonardo, thiago, alessio
 * 
 */
public class DeploymentManagerConfiguration {

    private static final String FILE_PATH = "deployment.properties";

    private final Configuration configuration;

    private static DeploymentManagerConfiguration INSTANCE = new DeploymentManagerConfiguration();

    private DeploymentManagerConfiguration() {
        this.configuration = new Configuration(FILE_PATH);
    }

    public static String get(String key) {
        return INSTANCE.configuration.get(key);
    }

    public static String[] getMultiple(String key) {
        return INSTANCE.configuration.getMultiple(key);
    }

    public static void set(String key, String value) {
        INSTANCE.configuration.set(key, value);
    }

    public static void set(String key, String[] values) {
        INSTANCE.configuration.set(key, values);
    }
    
    public static void save() {
        INSTANCE.configuration.save();
    }

}
