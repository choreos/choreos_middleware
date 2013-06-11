/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jclouds.ContextBuilder;
import org.jclouds.aws.ec2.compute.AWSEC2ComputeService;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.aws.ec2.reference.AWSEC2Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.ec2.domain.InstanceType;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;

import com.google.common.collect.Iterables;

public class AWSCloudProvider implements CloudProvider {

    private Logger logger = Logger.getLogger(AWSCloudProvider.class);

    private static String DEFAULT_USER = "ubuntu";
    private static String PROVIDER = "aws-ec2";
    private static String DEFAULT_IMAGE = "us-east-1/ami-3b4ff252";
    // private static String DEFAULT_IMAGE= "us-east-1/ami-ccf405a5";

    // only threads with the creationToken can create new instances
    // we use this token to implement the 1 req/sec rule
    private static boolean creationToken = true;
    private static Random random = new Random();

    public String getProviderName() {
	return PROVIDER;
    }

    ComputeService getClient(String image) {

	Properties overrides = new Properties();
	overrides.setProperty(AWSEC2Constants.PROPERTY_EC2_AMI_QUERY, "image-id=" + image);

	ComputeServiceContext context = ContextBuilder.newBuilder(PROVIDER)
		.credentials(DeploymentManagerConfiguration.get("AMAZON_ACCESS_KEY_ID"), DeploymentManagerConfiguration.get("AMAZON_SECRET_KEY"))
		.overrides(overrides).buildView(ComputeServiceContext.class);

	return context.getComputeService();
    }

    public Node createNode(NodeSpec nodeSpec) throws NodeNotCreatedException {

	long t0 = System.currentTimeMillis();

	Node node = new Node();
	
	oneRequestPerSecondRule();
	logger.debug("Creating node...");

	String imageId = nodeSpec.getImage();
	if (imageId == null || imageId.isEmpty()) {
	    imageId = DEFAULT_IMAGE;
	}
	String image = imageId.substring(imageId.indexOf('/') + 1);

	ComputeService client = getClient(image);
	try {
	    Set<? extends NodeMetadata> createdNodes = client.createNodesInGroup("default", 1,
		    getTemplate(client, imageId, nodeSpec.getResourceImpact()));
	    NodeMetadata cloudNode = Iterables.get(createdNodes, 0);

	    setNodeProperties(node, cloudNode);
	    client.getContext().close();
	} catch (RunNodesException e) {
	    logger.error("Node creation failed: " + e.getMessage());
	    throw new NodeNotCreatedException(node.getId());
	}

	long tf = System.currentTimeMillis();
	long duration = tf - t0;
	logger.debug(node + " created in " + duration + " miliseconds");

	return node;
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

    public Node getNode(String nodeId) throws NodeNotFoundException {

	ComputeService client = getClient("");
	Node node = null;
	try {
	    ComputeMetadata computeMetadata = client.getNodeMetadata(nodeId);
	    NodeMetadata cloudNode = client.getNodeMetadata(computeMetadata.getId());
	    node = new Node();
	    setNodeProperties(node, cloudNode);
	    client.getContext().close();
	} catch (Exception e) {
	    throw new NodeNotFoundException(nodeId);
	}

	return node;
    }

    public List<Node> getNodes() {

	List<Node> nodeList = new ArrayList<Node>();
	Node node;

	ComputeService client = getClient("");
	Set<? extends ComputeMetadata> cloudNodes = client.listNodes();
	for (ComputeMetadata computeMetadata : cloudNodes) {
	    NodeMetadata cloudNode = client.getNodeMetadata(computeMetadata.getId());
	    node = new Node();

	    setNodeProperties(node, cloudNode);
	    if (node.getState() != 1) {
		nodeList.add(node);
	    }
	}

	client.getContext().close();

	return nodeList;
    }

    public void destroyNode(String id) {

	ComputeService client = getClient("");
	client.destroyNode(id);
	client.getContext().close();
    }

    private void setNodeProperties(Node node, NodeMetadata cloudNode) {
	setNodeIp(node, cloudNode);
	node.setHostname(cloudNode.getName());
	node.setSo(cloudNode.getOperatingSystem().getName());
	node.setId(cloudNode.getId());
	node.setImage(cloudNode.getImageId());
	node.setState(cloudNode.getStatus().ordinal());
	node.setUser(DEFAULT_USER);
	node.setPrivateKey(DeploymentManagerConfiguration.get("AMAZON_PRIVATE_SSH_KEY"));
    }

    private void setNodeIp(Node node, NodeMetadata cloudNode) {
	Iterator<String> publicAddresses = cloudNode.getPublicAddresses().iterator();

	if (publicAddresses != null && publicAddresses.hasNext()) {
	    node.setIp(publicAddresses.next());
	}
    }

    private Template getTemplate(ComputeService client, String imageId, ResourceImpact resourceImpact) {

	String AWSInstanceType = getInstanceTypeFromResourceImpact(resourceImpact);

	TemplateBuilder builder = client.templateBuilder().imageId(imageId);
	if (client instanceof AWSEC2ComputeService) {
	    builder.hardwareId(AWSInstanceType);
	    Template template = builder.build();
	    AWSEC2TemplateOptions options = template.getOptions().as(AWSEC2TemplateOptions.class);
	    options.securityGroups("default");
	    options.keyPair(DeploymentManagerConfiguration.get("AMAZON_KEY_PAIR"));
	    return template;
	}

	return builder.build();
    }

    private String getInstanceTypeFromResourceImpact(ResourceImpact resourceImpact) {

	String defaultImage = InstanceType.M1_SMALL;

	if (resourceImpact != null && resourceImpact.getMemory() != null) {
	    switch (resourceImpact.getMemory()) {
	    case SMALL:
		return InstanceType.M1_SMALL;
	    case MEDIUM:
		return InstanceType.M1_MEDIUM;
	    case LARGE:
		return InstanceType.M1_LARGE;
	    default:
		return defaultImage;
	    }
	}
	return defaultImage;
    }

    public Node createOrUseExistingNode(NodeSpec nodeSpec) throws NodeNotCreatedException {

	List<Node> nodes = this.getNodes();
	if (nodes.size() > 0)
	    return nodes.get(0);
	else
	    return createNode(nodeSpec);
    }

}