/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.Iterator;
import java.util.Properties;
import java.util.Random;

import org.apache.log4j.Logger;
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
    private static final String DEFAULT_IMAGE = "us-east-1/ami-3b4ff252"; // Ubuntu
									  // 12.04

    // only threads with the creationToken can create new instances
    // we use this token to implement the 1 req/sec rule
    private static boolean creationToken = true;
    private static Random random = new Random();

    private Logger logger = Logger.getLogger(AWSCloudProvider.class);

    public AWSCloudProvider() {
	super(IDENTITY, CREDENTIAL, PROVIDER, PROPERTIES);
    }

    public String getCloudProviderName() {
	return PROVIDER;
    }

    @Override
    public CloudNode createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {
	oneRequestPerSecondRule();
	return super.createNode(nodeSpec);
    }

    private void oneRequestPerSecondRule() {

	while (!getToken()) {
	    final int DELAY = 10;
	    final int DELTA = random.nextInt(10);
	    try {
		Thread.sleep(DELAY + DELTA);
	    } catch (InterruptedException e) {
		logger.error("Exception at sleeping =/");
	    }
	}

	final int TWO_SECONDS = 2000;
	try {
	    Thread.sleep(TWO_SECONDS);
	} catch (InterruptedException e) {
	    logger.error("Exception at sleeping =/");
	}

	creationToken = true; // releases the token
    }

    private boolean getToken() {
	boolean ok = false;
	synchronized (AWSCloudProvider.class) {
	    ok = creationToken;
	    if (ok) {
		creationToken = false;
	    }
	}
	return ok;
    }

    @Override
    protected String getDefaultImageId() {
	return DEFAULT_IMAGE;
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
	    throw new IllegalStateException("Could not retrieve IP from node");
	}
    }

    @Override
    protected void configureTemplateOptions(TemplateOptions templateOptions) {
	AWSEC2TemplateOptions options = templateOptions.as(AWSEC2TemplateOptions.class);
	options.securityGroups("default");
	options.keyPair(DeploymentManagerConfiguration.get("AMAZON_KEY_PAIR"));
    }

}