package org.ow2.choreos.chef.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.ow2.choreos.chef.ChefNode;

public class ShowNodeParserTest {
	
	@Test
	public void shouldParseShowNodeOutput() throws IOException {
		
		String knifeShowNodeOutput = 
				"Node Name:   leo-arch-node\n"
				+ "Environment: _default\n"
				+ "FQDN:        leo-arch-node\n"
				+ "IP:          10.0.3.15\n"
				+ "Run List:    recipe[servicetravelagency::jar], recipe[serviceairline::jar], recipe[java]\n"
				+ "Roles:       \n"
				+ "Recipes:     servicetravelagency::jar, serviceairline::jar, java\n"
				+ "Platform:    ubuntu 11.10\n" 
				+ "Tags:\n";
		
		ShowNodeParser parser = new ShowNodeParser();
		ChefNode node = parser.parse(knifeShowNodeOutput); 
		
		assertEquals("leo-arch-node", node.getName());
		assertEquals("_default", node.getEnvironment());
		assertEquals("leo-arch-node", node.getFqdn());
		assertEquals("10.0.3.15", node.getIp());
		assertEquals("ubuntu 11.10", node.getPlatform());
		
		List<String> runList = node.getRunList();
		assertEquals(3, runList.size());
		assertEquals("recipe[servicetravelagency::jar]", runList.get(0));
		assertEquals("recipe[serviceairline::jar]", runList.get(1));
		assertEquals("recipe[java]", runList.get(2));
		
		List<String> recipes = node.getRecipes();
		assertEquals(3, recipes.size());
		assertEquals("servicetravelagency::jar", recipes.get(0));
		assertEquals("serviceairline::jar", recipes.get(1));
		assertEquals("java", recipes.get(2));
	}
	
	@Test
	public void shouldParseShowNodeOutputWithEmptyRunList() throws IOException {
		
		String knifeShowNodeOutput = 
				"Node Name:   leo-arch-node\n"
				+ "Environment: _default\n"
				+ "FQDN:        leo-arch-node\n"
				+ "IP:          10.0.3.15\n"
				+ "Run List:    \n"
				+ "Roles:       \n"
				+ "Recipes:     \n"
				+ "Platform:    ubuntu 11.10\n" 
				+ "Tags:\n";
		
		ShowNodeParser parser = new ShowNodeParser();
		ChefNode node = parser.parse(knifeShowNodeOutput); 
		
		assertEquals("leo-arch-node", node.getName());
		assertEquals("_default", node.getEnvironment());
		assertEquals("leo-arch-node", node.getFqdn());
		assertEquals("10.0.3.15", node.getIp());
		assertEquals("ubuntu 11.10", node.getPlatform());
		
		List<String> runList = node.getRunList();
		assertTrue(runList.isEmpty());
		
		List<String> recipes = node.getRecipes();
		assertTrue(recipes.isEmpty());
	}

}
