package org.ow2.choreos.deployment.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.rest.ConfigsResource;
import org.ow2.choreos.deployment.nodes.rest.NodesResource;
import org.ow2.choreos.deployment.services.rest.ServicesResource;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * Stand alone server that makes the REST API available to clients.
 * 
 * @author alfonso, leonardo
 * 
 */
public class DeploymentManagerServer implements Runnable {

	private static Logger logger;
	
	public static String URL;
	private static boolean running = false;

    static {
    	String port = Configuration.get("DEPLOYMENT_MANAGER_PORT");
    	URL = "http://localhost:" + port + "/deploymentmanager/";
    	System.out.println(URL);
    }
    
	public static void start() {
		
		logger = Logger.getLogger(DeploymentManagerServer.class);
		new Thread(new DeploymentManagerServer()).start();
		while (!running) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				logger.error(e);
			}
		}

	}

	public static void stop() {
		running = false;
	}

	public void run() {
		
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(NodesResource.class);
		sf.setResourceClasses(ServicesResource.class);
		sf.setResourceClasses(ConfigsResource.class);
		sf.setAddress(URL);
		sf.create();
		logger.info("Deployment Manager has started [" + URL + "]");
		running = true;

		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("Deployment Manager has stopped");

	}

	public static void main(String[] args) throws InterruptedException {
		
    	LogConfigurator.configLog();
		DeploymentManagerServer.start();
	}
}
