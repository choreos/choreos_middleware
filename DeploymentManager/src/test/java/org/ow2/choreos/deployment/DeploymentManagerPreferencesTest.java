package org.ow2.choreos.deployment;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class DeploymentManagerPreferencesTest {

    private DeploymentManagerPreferences prefs;
    private CloudNode node;

    @Before
    public void setUp() {

	prefs = new DeploymentManagerPreferences();
	node = new CloudNode();
	node.setIp("127.0.0.1");
	node.setHostname("localhost");
	node.setId("001");
	node.setUser("ubuntu");
    }

    @Test
    public void shouldStoreAnObject() {

	try {
	    prefs.putObject("node", node);
	    assertEquals(node, prefs.getObject("node"));
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }

    @Test
    public void test() throws IOException, ClassNotFoundException {
	System.out.println(prefs.getObject("infraMonitoringNode").toString());
    }
}
