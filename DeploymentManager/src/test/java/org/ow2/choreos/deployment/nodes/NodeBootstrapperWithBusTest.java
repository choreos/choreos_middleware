/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.tests.IntegrationTest;

@Category(IntegrationTest.class)
public class NodeBootstrapperWithBusTest extends NodeBootstrapperTest {

    @Test
    @Override
    public void shouldLeaveNodeBootstraped() throws Exception {

	DeploymentManagerConfiguration.set("BUS", "true");
	super.shouldLeaveNodeBootstraped();
	String adminEndpoint = "http://" + super.node.getIp() + ":8180/services/adminExternalEndpoint";
	System.out.println("bus at " + adminEndpoint);
	String wsdl = adminEndpoint.replaceAll("/$", "").concat("?wsdl");
	WebClient client = WebClient.create(wsdl);
	Response response = client.get();
	assertEquals(200, response.getStatus());
    }
}
