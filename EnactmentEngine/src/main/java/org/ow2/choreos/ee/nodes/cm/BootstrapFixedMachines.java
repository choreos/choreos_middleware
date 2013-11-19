/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.nodes.cm;

import java.util.List;

import org.ow2.choreos.ee.CloudConfiguration;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.ee.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class BootstrapFixedMachines {
    /*
     * You may edit this attr to the actual cloud account you want to use
     */
    private static final String CLOUD_ACCOUNT = CloudConfiguration.DEFAULT;

    public void run() throws NodeNotAccessibleException, NodeNotBootstrappedException {
	CloudConfiguration cloudConfiguration = CloudConfiguration.getCloudConfigurationInstance(CLOUD_ACCOUNT);
	CloudProviderFactory factory = CloudProviderFactory.getFactoryInstance();
	CloudProvider cp = factory.getCloudProviderInstance(cloudConfiguration);
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
	    if (checker.isBootstrapped(node)) {
		System.out.println("Bootstrap OK for " + node);
	    } else {
		System.out.println("Bootstrap NOT OK for " + node);
	    }
	} else {
	    System.out.println(node + " was already bootstrapped");
	}
    }

    public static void main(String[] args) throws NodeNotAccessibleException, NodeNotBootstrappedException {
	BootstrapFixedMachines task = new BootstrapFixedMachines();
	task.run();
    }

}
