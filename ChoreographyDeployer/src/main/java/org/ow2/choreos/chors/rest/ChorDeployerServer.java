package org.ow2.choreos.chors.rest;

import org.ow2.choreos.chors.Configuration;
import org.ow2.choreos.chors.Configuration.Option;
import org.ow2.choreos.rest.RESTServer;
import org.ow2.choreos.utils.LogConfigurator;

public class ChorDeployerServer {

	public final String NAME = "Choreography Deployer";	
	public static String URL;
	private RESTServer restServer;

    static {
    	String port = Configuration.get(Option.CHOR_DEPLOYER_PORT);
    	URL = "http://0.0.0.0:" + port + "/choreographydeployer/";
    }
    
    public ChorDeployerServer() {
    	
    	this.restServer = new RESTServer(NAME, URL, new Class[]{ChorResource.class});

    }
    
	public void start() {
		
		this.restServer.start();
	}

	public void stop() {
		
		this.restServer.stop();
	}

	public static void main(String[] args) throws InterruptedException {
		
    	LogConfigurator.configLog();
		ChorDeployerServer server = new ChorDeployerServer();
		server.start();
	}
}

