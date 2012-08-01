package org.ow2.choreos.enactment.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.ow2.choreos.enactment.Configuration;
import org.ow2.choreos.enactment.Configuration.Option;
import org.ow2.choreos.utils.LogConfigurator;

public class EnactEngServer implements Runnable {

	private static Logger logger;
	
	public static String URL;
	private static boolean running = false;

    static {
    	String port = Configuration.get(Option.ENACTMENT_ENGINE_PORT);
    	URL = "http://localhost:" + port + "/enactmentengine/";
    }
    
	public static void start() {
		
		logger = Logger.getLogger(EnactEngServer.class);
		new Thread(new EnactEngServer()).start();
		while (!running) {
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				//logger.error(e);
			}
		}

	}

	public static void stop() {
		running = false;
	}

	public void run() {
		JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
		sf.setResourceClasses(ChorResource.class);
		sf.setAddress(URL);
		sf.create();
		logger.info("Enactment Engine has started [" + URL + "]");
		running = true;

		while (running) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("Enactment Engine has stopped");

	}

	public static void main(String[] args) throws InterruptedException {
		
    	LogConfigurator.configLog();
		EnactEngServer.start();
	}
}

