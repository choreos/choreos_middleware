import static org.junit.Assert.assertTrue;

import java.util.Properties;
import java.util.Set;

import org.jclouds.aws.ec2.reference.AWSEC2Constants;
import org.jclouds.compute.ComputeService;
import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.NodeState;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.compute.options.EC2TemplateOptions;
import org.jclouds.ec2.domain.InstanceType;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.inject.Module;

public class AmazonCloudTest {
    private static ComputeService client;
    private static NodeMetadata node;
    private static final String IMAGE = "ami-ccf405a5";

    @BeforeClass
    public static void initClient() {
        // If we specify the image, it doesn't download info about all others
        Properties overrides = new Properties();
        overrides.setProperty(AWSEC2Constants.PROPERTY_EC2_AMI_QUERY, "image-id=" + IMAGE);

        // get a context with ec2 that offers the portable ComputeService api
        ComputeServiceContext context = new ComputeServiceContextFactory().createContext("aws-ec2",
                Configuration.get("AMAZON_ACCESSKEYID"), Configuration.get("AMAZON_SECRETKEY"),
                ImmutableSet.<Module> of(), overrides);

        client = context.getComputeService();
    }

    @AfterClass
    public static void closeContext() {
        client.getContext().close();
    }

    @Test
    public void testCreateNode() throws Exception {
        Set<? extends NodeMetadata> createdNodes = client.createNodesInGroup("default", 1, getTemplate());

        node = Iterables.get(createdNodes, 0);

        assertTrue(client.listNodes().contains(node));
    }

    @Test
    // This test depends on the above
    public void testDestroyNode() {
        client.destroyNode(node.getId());

        node = client.getNodeMetadata(node.getId());
        assertTrue(node == null || node.getState() != NodeState.RUNNING);
    }

    private Template getTemplate() {
        Template template = client.templateBuilder().hardwareId(InstanceType.M1_SMALL).imageId("us-east-1/" + IMAGE)
                .build();

        EC2TemplateOptions options = template.getOptions().as(EC2TemplateOptions.class);
        options.securityGroups("default");
        options.keyPair(Configuration.get("AMAZON_KEYPAIR"));
        options.blockUntilRunning(false);

        return template;
    }

}