package org.ow2.choreos.deployment;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.nodes.datamodel.CloudNode;

public class DeploymentManagerPreferencesTest {

    private static final String NODE_STR = "node";
    private DeploymentManagerPreferences prefs;
    private CloudNode node;

    @Before
    public void setUp() {

	prefs = DeploymentManagerPreferences.getInstance();
	node = new CloudNode();
	node.setIp("127.0.0.1");
	node.setHostname("localhost");
	node.setId("001");
	node.setUser("ubuntu");
    }

    @After
    public void tearDown() {
	prefs.getPrefs().remove(NODE_STR);
    }

    @Test
    public void shouldStoreAnObject() {

	try {
	    prefs.putObject(NODE_STR, node);
	    assertEquals(node, prefs.getObject(NODE_STR));
	} catch (IOException e) {
	    e.printStackTrace();
	} catch (ClassNotFoundException e) {
	    e.printStackTrace();
	}
    }
}
