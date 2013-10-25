package org.ow2.choreos.deployment.nodes.cm;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class BootstrapFixedMachines {

    public void run() throws NodeNotAccessibleException, NodeNotBootstrappedException {
        CloudProviderFactory factory = CloudProviderFactory.getFactoryInstance();
        CloudProvider cp = factory.getCloudProviderInstance("FIXED");
        List<CloudNode> nodes = cp.getNodes();
        for (CloudNode node : nodes) {
            bootstrapNode(node);        
        }
    }

    private void bootstrapNode(CloudNode node) throws NodeNotAccessibleException, NodeNotBootstrappedException {
        BootstrapChecker checker = new BootstrapChecker();
        if (!checker.isBootstrapped(node)) {
            System.out.println("Going to bootstrap " + node);
            NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
            bootstrapper.bootstrapNode();
            System.out.println("Checking if bootstrap was OK");
            assertTrue(checker.isBootstrapped(node));
            System.out.println("Bootstrap OK for " + node);
        } else {
            System.out.println(node + " was already bootstrapped");
        }
    }
    
    public static void main(String[] args) throws NodeNotAccessibleException, NodeNotBootstrappedException {
        BootstrapFixedMachines task = new BootstrapFixedMachines();
        task.run();
    }

}
