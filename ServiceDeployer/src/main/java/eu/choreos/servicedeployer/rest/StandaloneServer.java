package eu.choreos.servicedeployer.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

/**
 * Stand alone server that makes the REST API available to clients
 * 
 * @author alfonso
 * 
 */
public class StandaloneServer implements Runnable {

	private static boolean running = false;

	public static void start() throws InterruptedException {
		new Thread(new StandaloneServer()).start();
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
		sf.setAddress("http://localhost:9101/");
		sf.create();
		System.out.println("Service Deployer...");
		running = true;

		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Stoping CHOReOS Middleware...");

	}

	public static void main(String[] args) throws InterruptedException {
		StandaloneServer.start();
	}
}
