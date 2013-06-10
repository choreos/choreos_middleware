package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jclouds.Constants;
import org.jclouds.ContextBuilder;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.logging.slf4j.config.SLF4JLoggingModule;
import org.jclouds.openstack.nova.v2_0.compute.options.NovaTemplateOptions;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;

public class OpenStackKeystoneCloudProvider implements CloudProvider {

    Logger logger = Logger.getLogger(OpenStackKeystoneCloudProvider.class);

    public String getProviderName() {
	return "OpenStack Keystone Provider";
    }

    private static String OP_AUTHURL = DeploymentManagerConfiguration.get("OPENSTACK_IP");
    private static String OP_TENANT = DeploymentManagerConfiguration.get("OPENSTACK_TENANT");
    private static String OP_USER = DeploymentManagerConfiguration.get("OPENSTACK_USER");
    private static String OP_PASS = DeploymentManagerConfiguration.get("OPENSTACK_PASSWORD");

    private ComputeService getClient(String imageId) {
	logger.info("Obtaining Client");

	String provider = "openstack-nova";
	String identity = OP_TENANT + ":" + OP_USER;
	String credential = OP_PASS;

	Properties properties = new Properties();
	properties.setProperty(Constants.PROPERTY_ENDPOINT, OP_AUTHURL);

	// example of injecting a ssh implementation
	Iterable<Module> modules = ImmutableSet.<Module> of(new SLF4JLoggingModule());

	ContextBuilder builder = ContextBuilder.newBuilder(provider).credentials(identity, credential).modules(modules)
		.overrides(properties);

	logger.info("Initializing Client With Data: " + builder.getApiMetadata());

	ComputeService srv = builder.buildView(ComputeServiceContext.class).getComputeService();

	logger.info("Client obtained successfully.");

	return srv;
    }

    @Override
    public Node createNode(NodeSpec nodeSpec) {

	System.out.println(">OpenStack: Create new Node.");
	Node node = new Node();

	// TODO: resource impact changes

	ComputeService client = getClient("");
	Set<? extends NodeMetadata> createdNodes = null;

	try {
	    try {
		createdNodes = client.createNodesInGroup("default", 1, getTemplate(client, getImages().get(0).getId()));
	    } catch (RunNodesException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	} catch (org.jclouds.rest.AuthorizationException e) {
	    logger.error("Authorization failed. Provided user doesn't have authorization to create a new node.");
	    throw e;
	}

	NodeMetadata cloudNode = Iterables.get(createdNodes, 0);

	setNodeProperties(node, cloudNode);
	client.getContext().close();

	logger.info("Node created successfully.");
	return node;
    }

    @Override
    public Node getNode(String nodeId) throws NodeNotFoundException {
	ComputeService client = getClient("");

	Node node = new Node();

	try {
	    NodeMetadata cloudNode = client.getNodeMetadata(nodeId);
	    setNodeProperties(node, cloudNode);
	} catch (Exception e) {
	    throw new NodeNotFoundException(nodeId);
	}

	return node;
    }

    @Override
    public List<Node> getNodes() {
	List<Node> nodeList = new ArrayList<Node>();
	Node node;
	ComputeService client = getClient("");

	logger.info("Getting list of nodes");
	Set<? extends ComputeMetadata> cloudNodes = client.listNodes();
	logger.debug("Got: " + cloudNodes);
	for (ComputeMetadata computeMetadata : cloudNodes) {
	    NodeMetadata cloudNode = client.getNodeMetadata(computeMetadata.getId());
	    node = new Node();

	    setNodeProperties(node, cloudNode);
	    if (node.getState() != 1) {
		nodeList.add(node);
	    }
	}

	client.getContext().close();

	logger.info("Node List obtained successfully.");

	return nodeList;
    }

    public List<Image> getImages() {
	logger.info("Getting image info...");
	ComputeService client = getClient("");
	Set<? extends Image> images = client.listImages();

	List<Image> imageList = new ArrayList<Image>();

	for (Image image : images) {
	    imageList.add(image);
	}

	logger.info("Images: " + imageList.toString());

	return imageList;

    }

    public List<Hardware> getHardwareProfiles() {
	logger.info("getting hardware profile info...");
	ComputeService client = getClient("");
	Set<? extends Hardware> profiles = client.listHardwareProfiles();

	List<Hardware> hardwareList = new ArrayList<Hardware>();

	for (Hardware profile : profiles) {
	    hardwareList.add(profile);
	}
	logger.info(hardwareList.toString());

	return hardwareList;

    }

    @Override
    public void destroyNode(String id) {
	logger.info("Destroy Node.");
	ComputeService client = getClient("");
	client.destroyNode(id);
	client.getContext().close();
	logger.info("Node destroyed successfully.");
    }

    @Override
    public Node createOrUseExistingNode(NodeSpec nodeSpec) {
	for (Node n : getNodes()) {
	    if (n.getImage().equals(nodeSpec.getImage()) && NodeMetadata.Status.RUNNING.ordinal() == n.getState()) {
		return n;
	    }
	}
	Node i = null;
	i = createNode(nodeSpec);
	return i;
    }

    private Template getTemplate(ComputeService client, String imageId) {

	if (imageId.isEmpty()) {
	    imageId = getImages().get(0).getId();
	}

	String hardwareId = getHardwareProfiles().get(1).getId();

	logger.info("Creating Template with image ID: " + imageId + "; hardware ID: " + hardwareId);

	TemplateBuilder builder = client.templateBuilder().imageId(imageId);
	builder.hardwareId(hardwareId);
	logger.info("Building Template...");
	Template template = builder.build();
	NovaTemplateOptions options = template.getOptions().as(NovaTemplateOptions.class);
	options.keyPairName(DeploymentManagerConfiguration.get("OPENSTACK_KEY_PAIR"));
	options.securityGroupNames("default");
	logger.info("	Template built successfully!");
	return template;
    }

    private void setNodeProperties(Node node, NodeMetadata cloudNode) {
	setNodeIp(node, cloudNode);
	node.setHostname(cloudNode.getName());
	node.setSo(cloudNode.getOperatingSystem().getName());
	node.setId(cloudNode.getId());
	node.setImage(cloudNode.getImageId());
	node.setState(cloudNode.getStatus().ordinal());
	node.setUser("ubuntu");
	node.setPrivateKeyFile(DeploymentManagerConfiguration.get("OPENSTACK_PRIVATE_SSH_KEY"));
    }

    private void setNodeIp(Node node, NodeMetadata cloudNode) {
	Iterator<String> publicAddresses = cloudNode.getPrivateAddresses().iterator();

	if (publicAddresses != null && publicAddresses.hasNext()) {
	    node.setIp(publicAddresses.next());
	}
    }

}