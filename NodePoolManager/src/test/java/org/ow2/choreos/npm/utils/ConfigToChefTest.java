package org.ow2.choreos.npm.utils;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.npm.chef.ConfigToChef;
import org.ow2.choreos.npm.datamodel.Config;


public class ConfigToChefTest {
    
    @Before
    public  void setUp() {
    
    }

    @Test
    public void shouldGetCookBookAndRecipeName(){
    	Config config = new Config();
    	config.setName("getting-started::default");
    
    	assertEquals(ConfigToChef.getCookbookNameFromConfigName(config.getName()), "getting-started");
        assertEquals(ConfigToChef.getRecipeNameFromConfigName(config.getName()), "default");
    }


    @Test
    public void shouldGetCookBookAndRecipeNameWithoutRecipeName(){
    	Config config = new Config();
    	config.setName("getting-started");
    
    	assertEquals(ConfigToChef.getCookbookNameFromConfigName(config.getName()), "getting-started");
        assertEquals(ConfigToChef.getRecipeNameFromConfigName(config.getName()), "default");
    }
}
