/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.cloudprovider;

import java.util.Iterator;
import java.util.Properties;

import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.options.TemplateOptions;
import org.jclouds.ec2.domain.InstanceType;
import org.ow2.choreos.ee.config.CloudConfiguration;
import org.ow2.choreos.ee.config.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotDestroyed;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class AWSCloudProvider extends JCloudsCloudProvider {

    private static final Properties PROPERTIES = new Properties();
    private static final String DEFAULT_USER = "ubuntu";
    private static final String PROVIDER = "aws-ec2";
    private static final String DEFAULT_IMAGE = "us-east-1/ami-3b4ff252"; // Ubuntu
									  // 12.04
    private static final String DEFAULT_INSTANCE_TYPE = InstanceType.M1_SMALL;

    private static final int TIME_BETWEEN_REQUESTS_MILLIS = 2000;
    private static final DelayedRequestEnforcer delayedRequestsEnforcer = new DelayedRequestEnforcer(
	    TIME_BETWEEN_REQUESTS_MILLIS);
    private String defaultPrivateKey;
    private CloudConfiguration cloudConfiguration;

    public String getCloudProviderName() {
	return PROVIDER;
    }

    @Override
    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
	delayedRequestsEnforcer.enforceRule();
	return super.createNode(nodeSpec);
    }

    @Override
    public void destroyNode(String nodeId) throws NodeNotDestroyed {
	delayedRequestsEnforcer.enforceRule();
	super.destroyNode(nodeId);
    }

    @Override
    protected String getDefaultImageId() {
	String imageId = DeploymentManagerConfiguration.get("AMAZON_IMAGE_ID");
	;
	if (imageId == null || imageId.trim().isEmpty())
	    imageId = DEFAULT_IMAGE;
	return imageId;
    }

    @Override
    protected String getHardwareId() {
	String instanceType = DeploymentManagerConfiguration.get("AMAZON_INSTANCE_TYPE");
	if (instanceType == null || instanceType.isEmpty())
	    instanceType = DEFAULT_INSTANCE_TYPE;
	return instanceType;
    }

    @Override
    protected String getUserName() {
	return DEFAULT_USER;
    }

    @Override
    protected String getUserPrivateKey() {
	return defaultPrivateKey;
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
	options.keyPair(cloudConfiguration.get("AMAZON_KEY_PAIR"));
    }

    @Override
    public void setCloudConfiguration(CloudConfiguration cloudConfiguration) {
	this.cloudConfiguration = cloudConfiguration;
	defaultPrivateKey = cloudConfiguration.get("AMAZON_PRIVATE_SSH_KEY");
	super.identity = cloudConfiguration.get("AMAZON_ACCESS_KEY_ID");
	super.credential = cloudConfiguration.get("AMAZON_SECRET_KEY");
	super.provider = PROVIDER;
	super.properties = PROPERTIES;

    }
}