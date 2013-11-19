/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.Configuration;

/**
 * Encapsulates the reading of the file owners.properties
 * 
 * @author leonardo
 * 
 */
public class CloudConfiguration {

    private static Logger logger = Logger.getLogger(CloudConfiguration.class);

    public static final String DEFAULT = DeploymentManagerConfiguration.get("DEFAULT_CLOUD_ACCOUNT");

    private final String owner;

    private static String PROPERTIES_FILE = "clouds.properties";

    private static Map<String, CloudConfiguration> INSTANCES = new HashMap<String, CloudConfiguration>();

    private final Configuration properties = new Configuration(PROPERTIES_FILE);

    private Configuration getProperties() {
	return properties;
    }

    public static CloudConfiguration getCloudConfigurationInstance(String owner) {
	if (!INSTANCES.containsKey(owner))
	    INSTANCES.put(owner, new CloudConfiguration(owner));

	return INSTANCES.get(owner);
    }

    public static CloudConfiguration getCloudConfigurationInstance() {
	return getCloudConfigurationInstance(DEFAULT);
    }

    private CloudConfiguration(String owner) {
	this.owner = owner;
    }

    /**
     * If the value is not found, the DEFAULT value is returned. If both the
     * value of key and DEFAULT are not found, an IllegalArgumentException is
     * thrown.
     * 
     * @param key
     * @return
     * @throws IllegalArgumentException
     */
    public String get(String key) {

	key = keyForOwner(key);

	String value = getProperties().get(key);

	if (value == null || value.trim().isEmpty()) {
	    logger.error("Could not retrieve the CloudConfiguration property " + key + " for owner " + owner
		    + ". Please, check the file " + PROPERTIES_FILE);
	    throw new IllegalArgumentException();
	}

	return value.trim();
    }

    public String[] getMultiple(String key) {
	return getProperties().getMultiple(keyForOwner(key));
    }

    public void set(String key, String value) {
	if (key != null) {
	    key = keyForOwner(key);
	    getProperties().set(key, value);
	}
    }

    private String keyForOwner(String key) {
	return owner + "." + key;
    }

    public String getOwner() {
	return owner;
    }
}
