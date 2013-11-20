/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.config;

import org.ow2.choreos.utils.Configuration;

/**
 * 
 * @author tfmend
 * 
 */
public class QoSManagementConfiguration {

    public static final String ENACTMENT_ENGINE_URL = "ENACTMENT_ENGINE_URL";
    public static final String QOS_MGMT = "QOS_MGMT";
    public static final String NODE_TYPES = "NODE_TYPES";
    public static final String MAPPER_POLICY = "MAPPER_POLICY";
    public static final String RESOURCE_METRIC_AGGREGATOR = "RESOURCE_METRIC_AGGREGATOR";

    private static final String FILE_PATH = "qos_mgmt.properties";

    private final Configuration configuration;

    private static QoSManagementConfiguration INSTANCE = new QoSManagementConfiguration();

    public static String get(String key) {
	return INSTANCE.configuration.get(key);
    }

    public static String[] getMultiple(String key) {
	return INSTANCE.configuration.getMultiple(key);
    }

    public static void set(String key, String value) {
	INSTANCE.configuration.set(key, value);
    }

    private QoSManagementConfiguration() {
	this.configuration = new Configuration(FILE_PATH);
    }
}
