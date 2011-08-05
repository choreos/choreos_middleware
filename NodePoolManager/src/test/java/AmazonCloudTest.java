import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.Template;
import org.jclouds.ec2.compute.options.EC2TemplateOptions;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

import com.google.common.collect.Iterables;

public class AmazonCloudTest {

    @Test
    public void testCloud() throws Exception {
        // get a context with ec2 that offers the portable ComputeService api

        ComputeServiceContext context = new ComputeServiceContextFactory().createContext("aws-ec2",
                Configuration.get("AMAZON_ACCESSKEYID"), Configuration.get("AMAZON_SECRETKEY")); // ,
        // ImmutableSet.<Module> of(new Log4JLoggingModule(), new
        // JschSshClientModule()));

        Template template = context.getComputeService().templateBuilder().imageId("us-east-1/ami-ccf405a5").build();
        template.getOptions().as(EC2TemplateOptions.class).securityGroups("default");
        template.getOptions().as(EC2TemplateOptions.class).keyPair("amazon");
        Set<? extends NodeMetadata> nodes = context.getComputeService().createNodesInGroup("default", 1);

        // EC2Client ec2Client =
        // EC2Client.class.cast(context.getProviderSpecificContext().getApi());

        NodeMetadata node = Iterables.get(nodes, 0);
        String ip = node.getPublicAddresses().iterator().next();

        assertTrue(ip.contains("."));
        assertTrue(context.getComputeService().listNodes().contains(node));

        context.getComputeService().destroyNode(node.getId());

        assertFalse(context.getComputeService().listNodes().contains(node));
        context.close();
    }

}
