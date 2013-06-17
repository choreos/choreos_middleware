package org.ow2.choreos.chef;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class ChefNodeTest {


    @Test
    public void test() {
	ChefNode node = new ChefNode();
	node.addRecipe("tomcat");
	node.addRecipe("easyesb::client");
	
	String expectedJson = "{ 'run_list' : [ 'recipe[tomcat]', 'recipe[easyesb::client]' ] }";
	String json = node.toJson();
	assertEquals(expectedJson, json);
    }

}
