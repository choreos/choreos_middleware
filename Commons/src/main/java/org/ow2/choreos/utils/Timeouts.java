/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;


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

    public static int get(String key) {
	try {
	    return Integer.parseInt(INSTANCE.configuration.get(key).trim());
	} catch (NumberFormatException e) {
	    throw new IllegalStateException(key + " not configured on " + FILE_PATH);
	}
    }

    public static void set(String key, int value) {
	INSTANCE.configuration.set(key, Integer.toString(value));
    }

}
