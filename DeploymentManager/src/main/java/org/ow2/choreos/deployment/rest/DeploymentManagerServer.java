package org.ow2.choreos.deployment.rest;

import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.rest.ConfigsResource;
import org.ow2.choreos.deployment.nodes.rest.NodesResource;
import org.ow2.choreos.deployment.services.rest.ServicesResource;
import org.ow2.choreos.rest.RESTServer;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * Stand alone server that makes the REST API available to clients.
 * 
 * @author alfonso, leonardo
 * 
 */
public class DeploymentManagerServer {

	public final String NAME = "Deployment Manager";
	public static String URL;
	private RESTServer restServer;
	
    static {
    	String port = Configuration.get("DEPLOYMENT_MANAGER_PORT");
    	URL = "http://localhost:" + port + "/deploymentmanager/";
    	System.out.println(URL);
    }

    public DeploymentManagerServer() {
    
    	this.restServer = new RESTServer(NAME, URL, new Class[]{
    			NodesResource.class, ConfigsResource.class, ServicesResource.class});
    }
    
    public void start() {
    	
    	this.restServer.start();
    }
    
    public void stop() {
    	
    	this.restServer.stop();
    }
    
	public static void main(String[] args) {
		
    	LogConfigurator.configLog();
    	DeploymentManagerServer server = new DeploymentManagerServer();
    	server.start();
	}
}
