package org.ow2.choreos.servicedeployer.rest;

import org.ow2.choreos.npm.rest.NPMServer;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * Starts both NPMServer and ServiceDeployerService
 * 
 * @author leonardo
 *
 */
public class Servers {

	public static void main(String[] args) {

    	LogConfigurator.configLog();
    	NPMServer.start();
		ServiceDeployerServer.start();
	}

}
