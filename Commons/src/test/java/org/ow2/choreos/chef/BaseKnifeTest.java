/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef;

import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.experimental.categories.Category;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.tests.IntegrationTest;
import org.ow2.choreos.utils.LogConfigurator;

@Category(IntegrationTest.class)
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
