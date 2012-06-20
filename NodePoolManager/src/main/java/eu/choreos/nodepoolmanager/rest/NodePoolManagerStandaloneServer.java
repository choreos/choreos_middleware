package eu.choreos.nodepoolmanager.rest;

import java.io.IOException;
import java.util.Properties;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eu.choreos.nodepoolmanager.Configuration;



public class NodePoolManagerStandaloneServer implements Runnable {

	private static final String LOG_CONFIG_FILE = "log.properties";
	private static Logger logger;
	
	public static String URL;
    private static boolean running = false;
    
    static {
    	String port = Configuration.get("NODE_POOL_MANAGER_PORT");
    	URL = "http://localhost:" + port + "/nodepoolmanager/";
    }

    private static void configureLog() {
    	
    	Properties logProperties = new Properties();
    	try {
			logProperties.load(ClassLoader.getSystemResourceAsStream(LOG_CONFIG_FILE));
			PropertyConfigurator.configure(logProperties); 
		} catch (IOException e) {
			System.out.println("Could not load log.properties. Let's use basic log.");
			BasicConfigurator.configure();
		} 
    }

    
    public static void startNodePoolManager() throws InterruptedException {
        Runnable npmServer = new NodePoolManagerStandaloneServer();
		
        new Thread(npmServer).start();
        
        while (!running) {
            Thread.sleep(1);
        }

    }

    public static void stopNodePoolManager() {
        running = false;
    }

    public void run() {
    	
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(ConfigsResource.class, NodesResource.class);
        sf.setAddress(URL);
        sf.create();
        logger.info("Node Pool Manager has started");
        running = true;

        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.info("Node Pool Manager has stopped");
    }

    public static void main(String[] args) throws InterruptedException {
    	
    	configureLog();
        logger = Logger.getLogger(NodePoolManagerStandaloneServer.class);
        
        NodePoolManagerStandaloneServer.startNodePoolManager();
    }
}
