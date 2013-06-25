/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.integration.deployment.nodes.cloudprovider;

import java.util.List;

import org.jclouds.compute.domain.Hardware;
import org.jclouds.compute.domain.Image;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.nodes.cloudprovider.OpenStackKeystoneCloudProvider;
import org.ow2.choreos.integration.deployment.nodes.BaseTest;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.tests.IntegrationTest;

@Category(IntegrationTest.class)
public class OpenStackCloudProviderTest extends BaseTest {

    private final OpenStackKeystoneCloudProvider infra = new OpenStackKeystoneCloudProvider();

    @Test
    public void shouldCreateNodeFromPool() throws Exception {
	System.out.println(">>>> Starting Openstack Tests.");

	boolean bTestNewNode = true;
	boolean bTestNodesList = true;
	boolean bTestImagesList = true;
	boolean bTestHWList = true;

	System.out.println(">>>> Will Test:");
	if (bTestNewNode) {
	    System.out.println("	> New Node Creation");
	}
	if (bTestNodesList) {
	    System.out.println("	> Node List");
	}
	if (bTestImagesList) {
	    System.out.println("	> Image List");
	}
	if (bTestHWList) {
	    System.out.println("	> Hardware Profile List");
	}

	System.out.println("");

	if (bTestNewNode) {
	    // Create new Node
	    System.out.println("TEST: Create New Node from OpenStack\n");
	    infra.createNode(new NodeSpec());
	}

	if (bTestNodesList) {
	    // List nodes
	    System.out.println("TEST: List Nodes from OpenStack\n");
	    List<CloudNode> nodes = infra.getNodes();

	    System.out.println("Node Count:" + nodes.size());

	    for (CloudNode node : nodes) {
		System.out.println("ID: " + node.getId());
		System.out.println("SO: " + node.getSo());
		System.out.println("Hostname: " + node.getHostname());
		System.out.println("Image: " + node.getImage());
		System.out.println("IP: " + node.getIp());
		System.out.println("User: " + node.getUser());
		System.out.println("--------------");
	    }
	}

	if (bTestImagesList) {
	    // Image List Test
	    System.out.println("TEST: List Images from OpenStack\n");
	    Image image = infra.getImage();

	    System.out.println("Image : " + image);
	}

	if (bTestHWList) {
	    // Hardware Profiles Test
	    System.out.println("TEST: List Hardware Profiles from OpenStack\n");
	    Hardware hardware = infra.getHardwareProfile();

	    System.out.println("Hardware : " + hardware);
	}

	System.out.println(">>>> Completed Openstack Tests.");
    }

}