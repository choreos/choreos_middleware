/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

import org.ow2.choreos.utils.Configuration;

/**
 * 
 * @author leonardo
 * 
 */
public class Timeouts {

    private static final String FILE_PATH = "timeouts.properties";

    private final Configuration configuration;

    private static Timeouts INSTANCE = new Timeouts();

    private Timeouts() {
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

}
