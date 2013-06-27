/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

public class CloudProviderFactory {

    public enum CloudProviderType {
        FIXED, AWS, OPEN_STACK
    }

    public static CloudProvider getInstance(String cloudProviderType) {

        CloudProviderType type;
        try {
            type = CloudProviderType.valueOf(cloudProviderType);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Invalid CLOUD_PROVIDER in properties file: " + cloudProviderType);
        }

        return getInstance(type);
    }

    public static CloudProvider getInstance(CloudProviderType type) {

        switch (type) {

        case AWS:
            return new AWSCloudProvider();
        case FIXED:
            return new FixedCloudProvider();
        case OPEN_STACK:
            return new OpenStackKeystoneCloudProvider();
        default:
            throw new IllegalArgumentException("Invalid cloud provider type");
        }
    }
}
