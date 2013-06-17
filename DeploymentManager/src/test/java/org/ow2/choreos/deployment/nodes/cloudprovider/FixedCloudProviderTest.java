/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.LogConfigurator;

public class FixedCloudProviderTest {

    @Before
    public void setUp() {
	LogConfigurator.configLog();
    }

    @Test
    public void shouldReturnNodeInfo() throws NodeNotCreatedException {

	DeploymentManagerConfiguration.set("FIXED_VM_IPS", "192.168.56.101");
	DeploymentManagerConfiguration.set("FIXED_VM_HOSTNAMES", "choreos");
	DeploymentManagerConfiguration.set("FIXED_VM_PRIVATE_SSH_KEYS", "key");
	DeploymentManagerConfiguration.set("FIXED_VM_USERS", "choreos");
	DeploymentManagerConfiguration.set("FIXED_VM_TYPES", "SMALL");
	DeploymentManagerConfiguration.set("MAPPER_POLICY", "ANY_FIT");
	CloudProvider cp = new FixedCloudProvider();
	CloudNode node = cp.createOrUseExistingNode(new NodeSpec());

	assertTrue(node.getHostname() != null && !node.getHostname().isEmpty());

	Pattern pat = Pattern.compile("(\\d{1,3}\\.){3}\\d{1,3}");
	Matcher matcher = pat.matcher(node.getIp());
	assertTrue(matcher.matches());
    }

    @Test
    public void shouldReturnAvalableVMs() {

	DeploymentManagerConfiguration.set("FIXED_VM_IPS", "192.168.56.101, 192.168.56.102");
	DeploymentManagerConfiguration.set("FIXED_VM_HOSTNAMES", "choreos, choreos");
	DeploymentManagerConfiguration.set("FIXED_VM_PRIVATE_SSH_KEYS", "key, samekey");
	DeploymentManagerConfiguration.set("FIXED_VM_USERS", "choreos, choreos");
	DeploymentManagerConfiguration.set("FIXED_VM_TYPES", "SMALL, SMALL");
	DeploymentManagerConfiguration.set("MAPPER_POLICY", "ANY_FIT");

	CloudProvider cp = new FixedCloudProvider();
	List<CloudNode> nodes = cp.getNodes();
	assertEquals(2, nodes.size());

	CloudNode node0 = nodes.get(0);
	CloudNode node1 = nodes.get(1);

	assertTrue(node0.getId().equals("0") || node0.getId().equals("1"));
	assertTrue(node1.getId().equals("0") || node1.getId().equals("1"));
	assertTrue(!node0.getId().equals(node1.getId()));

	assertTrue(node0.getIp().equals("192.168.56.101") || node0.getIp().equals("192.168.56.102"));
	assertTrue(node1.getIp().equals("192.168.56.101") || node1.getIp().equals("192.168.56.102"));
	assertTrue(!node0.getIp().equals(node1.getIp()));
    }

    @Test(expected = NodeNotFoundException.class)
    public void shouldNotFindVMs() throws NodeNotFoundException {

	DeploymentManagerConfiguration.set("FIXED_VM_IPS", "192.168.56.101; 192.168.56.102 ");
	DeploymentManagerConfiguration.set("FIXED_VM_HOSTNAMES", "choreos, choreos");
	DeploymentManagerConfiguration.set("FIXED_VM_PRIVATE_SSH_KEYS", "key, samekey");
	DeploymentManagerConfiguration.set("FIXED_VM_USERS", "choreos, choreos");
	CloudProvider cp = new FixedCloudProvider();
	cp.getNode("2");
    }

}
