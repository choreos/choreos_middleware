package eu.choreos.nodepoolmanager.utils;

import static org.junit.Assert.assertEquals;

import org.jclouds.compute.RunNodesException;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


import com.jcraft.jsch.JSchException;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.chef.ConfigToChef;
import eu.choreos.nodepoolmanager.cloudprovider.AWSCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Config;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.SshUtil;

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
