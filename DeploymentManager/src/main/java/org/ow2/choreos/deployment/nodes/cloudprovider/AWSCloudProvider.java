package org.ow2.choreos.deployment.nodes.cloudprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.apache.log4j.Logger;
import org.jclouds.aws.ec2.compute.AWSEC2ComputeService;
import org.jclouds.aws.ec2.compute.AWSEC2TemplateOptions;
import org.jclouds.aws.ec2.reference.AWSEC2Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.ec2.domain.InstanceType;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.services.datamodel.ResourceImpact;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;

@SuppressWarnings("deprecation")
public class AWSCloudProvider implements CloudProvider {
	
	private Logger logger = Logger.getLogger(AWSCloudProvider.class);

	private static String DEFAULT_USER = "ubuntu";
	private static String PROVIDER="aws-ec2";
	private static String DEFAULT_IMAGE= "us-east-1/ami-3b4ff252";
//	private static String DEFAULT_IMAGE= "us-east-1/ami-ccf405a5";
	
	private NodeRegistry registry = NodeRegistry.getInstance();
	
	public String getProviderName() {
		return PROVIDER;
	}

	ComputeService getClient(String image) {

		Properties overrides = new Properties();
		overrides.setProperty(AWSEC2Constants.PROPERTY_EC2_AMI_QUERY,
				"image-id=" + image);
		
		ComputeServiceContext context = new ComputeServiceContextFactory()
		.createContext(PROVIDER,
				Configuration.get("AMAZON_ACCESS_KEY_ID"),
				Configuration.get("AMAZON_SECRET_KEY"),
				ImmutableSet.<Module> of(), overrides);
		
		return context.getComputeService();
	}

	public Node createNode(Node node, ResourceImpact resourceImpact) throws RunNodesException {
		
		long t0 = System.currentTimeMillis(); 
		logger.debug("Creating node...");

		String imageId = node.getImage();
		if (StringUtils.isEmpty(imageId)) {
			imageId = DEFAULT_IMAGE;
		}
		String image = imageId.substring(imageId.indexOf('/') + 1);

		ComputeService client = getClient(image); 
		Set<? extends NodeMetadata> createdNodes = client.createNodesInGroup(
				"default", 1, getTemplate(client, imageId, resourceImpact));
		NodeMetadata cloudNode = Iterables.get(createdNodes, 0);

		setNodeProperties(node, cloudNode);
		client.getContext().close();

		long tf = System.currentTimeMillis();
		long duration = tf - t0;
		logger.debug(node + " created in " + duration + " miliseconds");
		
		this.registry.putNode(node);
		return node;
	}



	public Node getNode(String nodeId) throws NodeNotFoundException {

		Node node = this.registry.getNode(nodeId);
		if (node == null) {
			throw new NodeNotFoundException("Node " + nodeId + " not found");
		}
		return node;
	}

	public List<Node> getNodes() {

		List<Node> nodes= this.registry.getNodes();
		if (nodes.isEmpty()) {
			nodes = fillRegistry();
		}
		return nodes; 
	}
	
	/**
	 * Maybe NPM has just started and the registry is empty,
	 * although may be there VMs on Amazon
	 * @return
	 */
	private List<Node> fillRegistry() {
		
		List<Node> nodes = new ArrayList<Node>();
		for (Node node: getNodesWithoutCache()) {
			this.registry.putNode(node);
			nodes.add(node);
		}
		return nodes;
	}
	
	public List<Node> getNodesWithoutCache() {
		List<Node> nodeList = new ArrayList<Node>();
		Node node;

		ComputeService client = getClient("");
		Set<? extends ComputeMetadata> cloudNodes = client.listNodes();
		for (ComputeMetadata computeMetadata : cloudNodes) {
			NodeMetadata cloudNode = client.getNodeMetadata(computeMetadata
					.getId());
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
		this.registry.deleteNode(id);
	}

	private void setNodeProperties(Node node, NodeMetadata cloudNode) {
		setNodeIp(node, cloudNode);
		node.setHostname(cloudNode.getName());
		node.setSo(cloudNode.getOperatingSystem().getName());
		node.setId(cloudNode.getId());
		node.setImage(cloudNode.getImageId());
		node.setState(cloudNode.getState().ordinal());
		node.setUser(DEFAULT_USER);
		node.setPrivateKey(Configuration.get("AMAZON_PRIVATE_SSH_KEY"));
	}

	private void setNodeIp(Node node, NodeMetadata cloudNode) {
		Iterator<String> publicAddresses = cloudNode.getPublicAddresses()
				.iterator();

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
			AWSEC2TemplateOptions options = template.getOptions().as(
					AWSEC2TemplateOptions.class);
			options.securityGroups("default");
			options.keyPair(Configuration.get("AMAZON_KEY_PAIR"));
			return template;
		}

		return builder.build();
	}

	private String getInstanceTypeFromResourceImpact(
			ResourceImpact resourceImpact) {
		
		String defaultImage = InstanceType.M1_SMALL;
		
		if (resourceImpact != null && resourceImpact.getMemory() != null) {
			switch (resourceImpact.getMemory()) {
			case SMALL :
				return InstanceType.M1_SMALL;
			case MEDIUM :
				return InstanceType.M1_MEDIUM;
			case LARGE :
				return InstanceType.M1_LARGE;
			default:
				return defaultImage;
			}
		}
		return defaultImage;
	}

	public Node createOrUseExistingNode(Node node, ResourceImpact resourceImpact) throws RunNodesException {
		
		List<Node> nodes = this.getNodes();
		if (nodes.size() > 0)
			return nodes.get(0);
		else
			return createNode(node, resourceImpact);
	}

}