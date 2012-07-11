package org.ow2.choreos.npm.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.log4j.Logger;
import org.ow2.choreos.npm.Configuration;
import org.ow2.choreos.utils.LogConfigurator;

public class NPMStandaloneServer implements Runnable {

	private static Logger logger;
	
	public static String URL;
    private static boolean running = false;
    
    static {
    	String port = Configuration.get("NODE_POOL_MANAGER_PORT");
    	URL = "http://localhost:" + port + "/nodepoolmanager/";
    }

    public static void startNodePoolManager() throws InterruptedException {
    	
    	logger = Logger.getLogger(NPMStandaloneServer.class);
    	
        Runnable npmServer = new NPMStandaloneServer();
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
        logger.info("Node Pool Manager has started [" + URL + "]");
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
        
        NPMStandaloneServer.startNodePoolManager();
    }
}
