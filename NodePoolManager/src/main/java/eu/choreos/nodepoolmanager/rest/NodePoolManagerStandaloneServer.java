package eu.choreos.nodepoolmanager.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.utils.LogConfigurator;



public class NodePoolManagerStandaloneServer implements Runnable {

	private static Logger logger;
	
	public static String URL;
    private static boolean running = false;
    
    static {
    	String port = Configuration.get("NODE_POOL_MANAGER_PORT");
    	URL = "http://localhost:" + port + "/nodepoolmanager/";
    }

    public static void startNodePoolManager() throws InterruptedException {
    	
    	logger = Logger.getLogger(NodePoolManagerStandaloneServer.class);
    	
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
    	
    	LogConfigurator.configLog();
        
        NodePoolManagerStandaloneServer.startNodePoolManager();
    }
}
