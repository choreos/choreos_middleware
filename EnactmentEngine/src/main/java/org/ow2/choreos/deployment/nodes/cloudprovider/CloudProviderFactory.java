/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.CloudConfiguration;
import org.ow2.choreos.utils.Configuration;
import org.ow2.choreos.utils.SingletonsFactory;

public class CloudProviderFactory extends SingletonsFactory<CloudProvider> {

    private static final String CLASS_MAP_FILE_PATH = "cloud_providers.properties";

    private static final Logger logger = Logger.getLogger(CloudProviderFactory.class);

    private static CloudProviderFactory INSTANCE;

    public static CloudProvider cloudProviderForTesting;
    public static boolean testing;

    public static CloudProviderFactory getFactoryInstance() {
	if (INSTANCE == null) {
	    synchronized (CloudProviderFactory.class) {
		if (INSTANCE == null)
		    createNewInstance();
	    }
	}
	return INSTANCE;
    }

    private static void createNewInstance() {
	Configuration conf = new Configuration(CLASS_MAP_FILE_PATH);
	INSTANCE = new CloudProviderFactory(conf);
    }

    public CloudProviderFactory(Configuration classMap) {
	super(classMap);
    }

    public CloudProvider getCloudProviderInstance(CloudConfiguration cloudConfiguration) {
	if (testing) {
	    return cloudProviderForTesting;
	} else {
	    String type = cloudConfiguration.get("CLOUD_PROVIDER");
	    CloudProvider c = getInstance(type);
	    c.setCloudConfiguration(cloudConfiguration);
	    return c;
	}
    }

}
