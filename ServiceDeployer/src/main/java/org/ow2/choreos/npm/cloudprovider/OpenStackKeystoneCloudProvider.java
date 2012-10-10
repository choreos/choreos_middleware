package org.ow2.choreos.npm.cloudprovider;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

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
import org.ow2.choreos.npm.NodeNotFoundException;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.Configuration;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;

public class OpenStackKeystoneCloudProvider implements CloudProvider {

    public String getproviderName() {
        return "OpenStack Keystone Provider";
    }

    private static String OP_AUTHURL = Configuration.get("OPENSTACK_IP");
    private static String OP_TENANT = Configuration.get("OPENSTACK_TENANT");
    private static String OP_USER = Configuration.get("OPENSTACK_USER");
    private static String OP_PASS = Configuration.get("OPENSTACK_PASSWORD");

    private ComputeService getClient(String imageId) {
        System.out.println(">OpenStack: Obtain Client.");

        String provider = "openstack-nova";
        String identity = OP_TENANT + ":" + OP_USER;
        String credential = OP_PASS;

        Properties properties = new Properties();
        properties.setProperty(Constants.PROPERTY_ENDPOINT, OP_AUTHURL);

        // example of injecting a ssh implementation
        Iterable<Module> modules = ImmutableSet.<Module> of();

        ContextBuilder builder = ContextBuilder.newBuilder(provider)
                .credentials(identity, credential).modules(modules).overrides(properties);

        System.out.printf(">OpenStack: Initializing Client With Data: \n	> %s%n",
                builder.getApiMetadata());

        ComputeService srv = builder.buildView(ComputeServiceContext.class).getComputeService();

        System.out.println(">OpenStack: Client obtained successfully.");

        return srv;
    }

    @Override
    public Node createNode(Node node) throws RunNodesException {
        System.out.println(">OpenStack: Create new Node.");

        ComputeService client = getClient("");
        Set<? extends NodeMetadata> createdNodes;

        try {
            createdNodes = client.createNodesInGroup("default", 1,
                    getTemplate(client, getImages().get(0).getId()));
        } catch (org.jclouds.rest.AuthorizationException e) {
            System.out
                    .println(">OpenStack: Authorization failed. Provided user doesn't have authorization to create a new node.");
            throw e;
        }

        NodeMetadata cloudNode = Iterables.get(createdNodes, 0);

        setNodeProperties(node, cloudNode);
        client.getContext().close();

        System.out.println(">OpenStack: Node created successfully.");
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
        System.out.println(">OpenStack: List Nodes.");
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

        System.out.println(">OpenStack: Node List obtained successfully.");

        return nodeList;
    }

    public List<Image> getImages() {
        System.out.println(">OpenStack: List Images.");
        ComputeService client = getClient("");
        Set<? extends Image> images = client.listImages();

        List<Image> imageList = new ArrayList<Image>();

        for (Image image : images) {
            imageList.add(image);
        }

        System.out.println(">OpenStack: Image List obtained successfully.");

        return imageList;

    }

    public List<Hardware> getHardwareProfiles() {
        System.out.println(">OpenStack: List Hardware Profiles.");
        ComputeService client = getClient("");
        Set<? extends Hardware> profiles = client.listHardwareProfiles();

        List<Hardware> hardwareList = new ArrayList<Hardware>();

        for (Hardware profile : profiles) {
            hardwareList.add(profile);
        }

        System.out.println(">OpenStack: Hardware Profiles obtained successfully.");

        return hardwareList;

    }

    @Override
    public void destroyNode(String id) {
        System.out.println(">OpenStack: Destroy Node.");
        ComputeService client = getClient("");
        client.destroyNode(id);
        client.getContext().close();
        System.out.println(">OpenStack: Node destroyed successfully.");
    }

    @Override
    public Node createOrUseExistingNode(Node node) throws RunNodesException {
        // TODO Auto-generated method stub
        return null;
    }

    private Template getTemplate(ComputeService client, String imageId) {

        if (imageId.isEmpty()) {
            imageId = getImages().get(0).getId();
        }

        String hardwareId = getHardwareProfiles().get(0).getId();

        System.out.println("	Creating Template with:");
        System.out.println("	Image ID: " + imageId);
        System.out.println("	Hardware ID: " + hardwareId);

        TemplateBuilder builder = client.templateBuilder().imageId(imageId);
        builder.hardwareId(hardwareId);
        System.out.println("	Building Template...");
        Template template = builder.build();
        System.out.println("	Template built successfully!");
        // TemplateOptions options = template.getOptions();
        return template;
    }

    private void setNodeProperties(Node node, NodeMetadata cloudNode) {
        setNodeIp(node, cloudNode);
        node.setHostname(cloudNode.getName());
        node.setSo(cloudNode.getOperatingSystem().getName());
        node.setId(cloudNode.getId());
        node.setImage(cloudNode.getImageId());
        node.setState(cloudNode.getState().ordinal());
        node.setUser("ubuntu");
    }

    private void setNodeIp(Node node, NodeMetadata cloudNode) {
        Iterator<String> publicAddresses = cloudNode.getPrivateAddresses().iterator();

        if (publicAddresses != null && publicAddresses.hasNext()) {
            node.setIp(publicAddresses.next());
        }
    }

}
