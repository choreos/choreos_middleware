package org.ow2.choreos.chef;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chef.impl.KnifeImpl;

/**
 * Requirement: fill the CHEF_CONFIG_FILE in the test/resources/chef.properties file 
 * 
 * @author leonardo
 *
 */
public class KnifeNodeTest {
	
	private Knife knife;
	
	@Before
	public void setUp() {
		
		Properties chefProperties = new Properties();
        try {
        	chefProperties.load(ClassLoader.getSystemResourceAsStream("chef.properties"));
        } catch (IOException e) {
            System.out.println("Could not load chefProperties");
        }
        String knifeConfigFile = chefProperties.getProperty("CHEF_CONFIG_FILE");
        if (knifeConfigFile == null) {
        	System.out.println("knifeConfigFile is null");
        	fail();
        }
    	this.knife = new KnifeImpl(knifeConfigFile);
	}

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
