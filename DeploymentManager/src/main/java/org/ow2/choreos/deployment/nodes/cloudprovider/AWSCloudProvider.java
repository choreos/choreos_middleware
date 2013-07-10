/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.Iterator;
import java.util.Properties;

import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.ec2.domain.InstanceType;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class AWSCloudProvider extends JCloudsCloudProvider {

    private static final String IDENTITY = DeploymentManagerConfiguration.get("AMAZON_ACCESS_KEY_ID");
    private static final String CREDENTIAL = DeploymentManagerConfiguration.get("AMAZON_SECRET_KEY");
    private static final Properties PROPERTIES = new Properties();
    private static final String DEFAULT_USER = "ubuntu";
    private static final String DEFAULT_PRIVATE_KEY = DeploymentManagerConfiguration.get("AMAZON_PRIVATE_SSH_KEY");
    private static final String PROVIDER = "aws-ec2";
    private static final String DEFAULT_IMAGE = "us-east-1/ami-3b4ff252"; // Ubuntu 12.04

    private static final OneRequestPerSecondEnforcer oneRequestPerSecondEnforcer = new OneRequestPerSecondEnforcer();

    public AWSCloudProvider() {
        super(IDENTITY, CREDENTIAL, PROVIDER, PROPERTIES);
    }

    public String getCloudProviderName() {
        return PROVIDER;
    }

    @Override
    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
        oneRequestPerSecondEnforcer.enforceRule();
        return super.createNode(nodeSpec);
    }

    @Override
    protected String getDefaultImageId() {
        String imageId = DeploymentManagerConfiguration.get("AMAZON_IMAGE_ID");;
        if (imageId == null || imageId.trim().isEmpty())
            imageId = DEFAULT_IMAGE; 
        return imageId;
    }

    @Override
    protected String getHardwareId() {
        return InstanceType.M1_SMALL;
    }

    @Override
    protected String getUserName() {
        return DEFAULT_USER;
    }

    @Override
    protected String getUserPrivateKey() {
        return DEFAULT_PRIVATE_KEY;
    }

    @Override
    protected String getNodeIp(NodeMetadata nodeMetadata) {
        Iterator<String> publicAddresses = nodeMetadata.getPublicAddresses().iterator();
        if (publicAddresses != null && publicAddresses.hasNext()) {
            return publicAddresses.next();
        } else {
            return null;
        }
    }

    @Override
    protected void configureTemplateOptions(TemplateOptions templateOptions) {
        AWSEC2TemplateOptions options = templateOptions.as(AWSEC2TemplateOptions.class);
        options.securityGroups("default");
        options.keyPair(DeploymentManagerConfiguration.get("AMAZON_KEY_PAIR"));
    }
    
    public static void main(String[] args) throws NodeNotCreatedException {
        AWSCloudProvider cp = new AWSCloudProvider();
        CloudNode node = cp.createNode(new NodeSpec());
        System.out.println("Created: " + node);
    }

}