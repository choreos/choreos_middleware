package org.ow2.choreos.chef;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.utils.LogConfigurator;

public class BaseKnifeTest {

	protected Knife knife;
	
	@BeforeClass
	public static void configLog() {
		LogConfigurator.configLog();
	}
	
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
        String chefRepo = chefProperties.getProperty("CHEF_REPO");
        if (chefRepo == null) {
        	System.out.println("chefRepo is null");
        	fail();
        }
    	this.knife = new KnifeImpl(knifeConfigFile, chefRepo, true);
	}

}
