package org.ow2.choreos.servicedeployer.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.ow2.choreos.npm.Configuration;
import org.ow2.choreos.utils.LogConfigurator;


/**
 * Stand alone server that makes the REST API available to clients
 * 
 * @author alfonso, leonardo
 * 
 */
public class ServiceDeployerServer implements Runnable {

	private static Logger logger;
	
	public static String URL;
	private static boolean running = false;

    static {
    	String port = Configuration.get("SERVICE_DEPLOYER_PORT");
    	URL = "http://localhost:" + port + "/servicedeployer/";
    }
    
	public static void start() {
		
		logger = Logger.getLogger(ServiceDeployerServer.class);
		new Thread(new ServiceDeployerServer()).start();
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
		sf.setResourceClasses(ServicesResource.class);
		sf.setAddress(URL);
		sf.create();
		logger.info("Service Deployer has started [" + URL + "]");
		running = true;

		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("Service Deployer has stopped");

	}

	public static void main(String[] args) throws InterruptedException {
		
    	LogConfigurator.configLog();
		ServiceDeployerServer.start();
	}
}
