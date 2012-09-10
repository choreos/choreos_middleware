package org.ow2.choreos.npm.cloudprovider;

public class CloudProviderSelector {
    public CloudProvider newCloudProvider(String cloud) {
        if (cloud.endsWith("aws")) {
            return new AWSCloudProvider();
        } else if (cloud.endsWith("openstack")) {
            return new OpenStackKeystoneCloudProvider();
        }

        return new FixedCloudProvider();
    }
}
