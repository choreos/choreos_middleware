/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import org.ow2.choreos.utils.Configuration;

public class CloudProviderFactory {

    private static final String CLASS_MAP_FILE_PATH = "cloud_providers.properties";

    private static Configuration classMap;
    
    static {
        classMap = new Configuration(CLASS_MAP_FILE_PATH);
    }
    
    public static CloudProvider getInstance(String type) {
        String className = classMap.get(type);
        CloudProvider singleton = null;
        try {
            @SuppressWarnings("unchecked")
            // catches handle the problem
            Class<CloudProvider> clazz = (Class<CloudProvider>) Class.forName(className);
            singleton = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            creationFailed(type);
        } catch (InstantiationException e) {
            creationFailed(type);
        } catch (IllegalAccessException e) {
            creationFailed(type);
        } catch (ClassCastException e) {
            creationFailed(type);
        }
        return singleton;
    }
    
    private static void creationFailed(String type) {
        throw new IllegalStateException("Invalid CLOUD_PROVIDER: " + type);
    }
}
