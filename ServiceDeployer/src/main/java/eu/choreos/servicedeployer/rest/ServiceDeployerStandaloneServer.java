package eu.choreos.servicedeployer.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import eu.choreos.servicedeployer.Configuration;


/**
 * Stand alone server that makes the REST API available to clients
 * 
 * @author alfonso, leonardo
 * 
 */
public class ServiceDeployerStandaloneServer implements Runnable {

	public static String URL;
	private static boolean running = false;

    static {
    	String port = Configuration.get("SERVICE_DEPLOYER_PORT");
    	URL = "http://localhost:" + port + "/servicedeployer/";
    }
    
	public static void start() throws InterruptedException {
		new Thread(new ServiceDeployerStandaloneServer()).start();
		while (!running) {
			Thread.sleep(1);
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
		System.out.println("Starting Service Deployer...");
		running = true;

		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Stoping CHOReOS Service Deployer...");

	}

	public static void main(String[] args) throws InterruptedException {
		ServiceDeployerStandaloneServer.start();
	}
}
