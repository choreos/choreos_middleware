package org.ow2.choreos.chef;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

/**
 * Requirement: fill the CHEF_CONFIG_FILE in the test/resources/chef.properties file 
 * 
 * @author leonardo
 *
 */
public class KnifeNodeTest extends BaseKnifeTest {
	
	@Test
	public void shouldAddAndRemoveNode() throws KnifeException {
		
		String nodeName = "VirtualTestNode";
		
		List<String> nodes = knife.node().list();
		if (nodes.contains(nodeName)) {
			knife.node().delete(nodeName);
		}

		nodes = knife.node().list();
		assertFalse(nodes.contains(nodeName));
		
		knife.node().create(nodeName);
		nodes = knife.node().list();
		assertTrue(nodes.contains(nodeName));
		
		knife.node().delete(nodeName);
		nodes = knife.node().list();
		assertFalse(nodes.contains(nodeName));
		
	}
}
