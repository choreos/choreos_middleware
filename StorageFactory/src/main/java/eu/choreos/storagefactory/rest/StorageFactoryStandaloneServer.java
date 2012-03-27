package eu.choreos.storagefactory.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import eu.choreos.storagefactory.Configuration;


/**
 * Stand alone server that makes the REST API available to clients
 * 
 * @author leonardo, alfonso
 * 
 */
public class StorageFactoryStandaloneServer implements Runnable {

	public static final String URL;
	private static boolean running = false;

	static {
		String port = Configuration.get("STORAGE_FACTORY_PORT");
		URL = "http://localhost:" + port + "/storagefactory/";
	}
	
	public static void start() throws InterruptedException {
		new Thread(new StorageFactoryStandaloneServer()).start();
		while (!running) {
			Thread.sleep(1);
		}

	}

	public static void stop() {
		running = false;
	}

	public void run() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(StoragesResource.class);
		sf.setAddress(URL);
		sf.create();
		System.out.println("Starting Storage Factory...");
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
		StorageFactoryStandaloneServer.start();
	}
}
