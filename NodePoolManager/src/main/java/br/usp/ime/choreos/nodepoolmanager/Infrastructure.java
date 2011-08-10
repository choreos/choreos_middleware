package br.usp.ime.choreos.nodepoolmanager;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.cxf.common.util.StringUtils;
import org.jclouds.aws.ec2.compute.AWSEC2ComputeService;
import org.jclouds.aws.ec2.reference.AWSEC2Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.RunNodesException;
import org.jclouds.compute.domain.ComputeMetadata;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeState;
import org.jclouds.compute.domain.Template;
import org.jclouds.compute.domain.TemplateBuilder;
import org.jclouds.ec2.compute.options.EC2TemplateOptions;
import org.jclouds.ec2.domain.InstanceType;

import br.usp.ime.choreos.nodepoolmanager.cm.NodeInitializer;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;

public class Infrastructure {

    private ComputeService getClient(String imageId) {
        String provider = Configuration.get("DEFAULT_PROVIDER");
        if (StringUtils.isEmpty(provider)) {
            provider = "aws-ec2";
        }

        return getClient(imageId, provider);
    }

    private ComputeService getClient(String imageId, String provider) {
        // If we specify the image, it doesn't download info about all others
        Properties overrides = new Properties();
        overrides.setProperty(AWSEC2Constants.PROPERTY_EC2_AMI_QUERY, "image-id=" + imageId);

        // get a context with ec2 that offers the portable ComputeService api
        ComputeServiceContext context = new ComputeServiceContextFactory().createContext(provider,
                Configuration.get("AMAZON_ACCESS_KEY_ID"), Configuration.get("AMAZON_SECRET_KEY"),
                ImmutableSet.<Module> of(), overrides);

        return context.getComputeService();
    }

    public Node createNode(Node node) throws RunNodesException {
        System.out.println("Creating node...");
        String imageId = node.getImage();
        if (StringUtils.isEmpty(imageId)) {
            imageId = "us-east-1/ami-ccf405a5"; // TODO put default image in a
                                                // configuration file
        }
        String image = imageId.substring(imageId.indexOf('/') + 1);

        ComputeService client = getClient(image);
        Set<? extends NodeMetadata> createdNodes = client
                .createNodesInGroup("default", 1, getTemplate(client, imageId));
        NodeMetadata cloudNode = Iterables.get(createdNodes, 0);

        setNodeProperties(node, cloudNode);
        if (!(client.getContext() instanceof Proxy)) {
            System.out.println("Waiting for SSH...");
            SshUtil ssh = new SshUtil(node.getIp());
            while (!ssh.isAccessible())
                ;

            NodeInitializer ni = new NodeInitializer(node.getIp());
            try {
                ni.initialize();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        client.getContext().close();
        return node;
    }

    public Node getNode(String nodeId) throws NodeNotFoundException {
        ComputeService client = getClient("");

        Node node = new Node();

        try {
            NodeMetadata cloudNode = client.getNodeMetadata(nodeId);
            setNodeProperties(node, cloudNode);
        } catch (Exception e) {
            throw new NodeNotFoundException();
        }

        return node;
    }

    public List<Node> getNodes() {
        List<Node> nodeList = new ArrayList<Node>();
        Node node;
        NodeMetadata cloudNode;

        ComputeService client = getClient("");
        Set<? extends ComputeMetadata> cloudNodes = client.listNodes();
        for (ComputeMetadata computeMetadata : cloudNodes) {
            cloudNode = client.getNodeMetadata(computeMetadata.getId());
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
        node.setHostname(cloudNode.getHostname());
        node.setSo(cloudNode.getOperatingSystem().getName());
        node.setId(cloudNode.getId());
        node.setImage(cloudNode.getImageId());
        node.setState(cloudNode.getState().ordinal());
    }

    private void setNodeIp(Node node, NodeMetadata cloudNode) {
        Iterator<String> publicAddresses = cloudNode.getPublicAddresses().iterator();

        if (publicAddresses != null && publicAddresses.hasNext()) {
            node.setIp(publicAddresses.next());
        }
    }

    private Template getTemplate(ComputeService client, String imageId) {
        TemplateBuilder builder = client.templateBuilder().imageId(imageId);
        if (client instanceof AWSEC2ComputeService) {
            builder.hardwareId(InstanceType.M1_SMALL);
            Template template = builder.build();
            EC2TemplateOptions options = template.getOptions().as(EC2TemplateOptions.class);
            options.securityGroups("default");
            options.keyPair(Configuration.get("AMAZON_KEY_PAIR"));
            return template;
        }

        return builder.build();
    }

    public Node createOrUseExistingNode(Node node) throws RunNodesException {
        for (Node n : getNodes()) {
            if (n.getImage().equals(node.getImage()) && NodeState.RUNNING.ordinal() == n.getState()) {
                return n;
            }
        }
        return createNode(node);
    }
}