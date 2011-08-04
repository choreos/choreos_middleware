import java.util.Set;

import org.jclouds.compute.ComputeServiceContext;
import org.jclouds.compute.ComputeServiceContextFactory;
import org.jclouds.compute.domain.Image;
import org.jclouds.compute.domain.NodeMetadata;
import org.jclouds.compute.domain.OsFamily;
import org.jclouds.compute.domain.Template;
import org.jclouds.domain.Location;
import org.jclouds.ec2.EC2Client;
import org.jclouds.ec2.compute.options.EC2TemplateOptions;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

import com.google.common.collect.Iterables;

public class AmazonCloudTest {


    public void testCloud() throws Exception {
        // get a context with ec2 that offers the portable ComputeService api
        ComputeServiceContext context = new ComputeServiceContextFactory().createContext("aws-ec2",
                Configuration.get("accesskeyid"), Configuration.get("secretkey")); // ,
        // ImmutableSet.<Module> of(new Log4JLoggingModule(), new
        // JschSshClientModule()));

        // here's an example of the portable api
        Set<? extends Location> locations = context.getComputeService().listAssignableLocations();

        Set<? extends Image> images = context.getComputeService().listImages();

        // pick the highest version of the RightScale CentOs template
        Template template = context.getComputeService().templateBuilder().osFamily(OsFamily.UBUNTU).build();

        // specify your own groups which already have the correct rules applied
        template.getOptions().as(EC2TemplateOptions.class).securityGroups("default");

        // specify your own keypair for use in creating nodes
        template.getOptions().as(EC2TemplateOptions.class).keyPair("amazon");

        // run a couple nodes accessible via group
        Set<? extends NodeMetadata> nodes = context.getComputeService().createNodesInGroup("webserver", 2, template);

        // when you need access to very ec2-specific features, use the
        // provider-specific context
        EC2Client ec2Client = EC2Client.class.cast(context.getProviderSpecificContext().getApi());

        // ec2Client.getInstanceServices().describeInstancesInRegion(region,
        // instanceIds)
        // ex. to get an ip and associate it with a node
        NodeMetadata node = Iterables.get(nodes, 0);
        String ip = ec2Client.getElasticIPAddressServices().allocateAddressInRegion(node.getLocation().getId());
        ec2Client.getElasticIPAddressServices().associateAddressInRegion(node.getLocation().getId(), ip,
                node.getProviderId());

        context.close();

    }

}
