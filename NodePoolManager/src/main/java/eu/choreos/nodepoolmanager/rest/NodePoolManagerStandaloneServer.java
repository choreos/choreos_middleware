package eu.choreos.nodepoolmanager.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import eu.choreos.nodepoolmanager.Configuration;



public class NodePoolManagerStandaloneServer implements Runnable {

	public static String URL;
    private static boolean running = false;
    
    static {
    	String port = Configuration.get("NODE_POOL_MANAGER_PORT");
    	URL = "http://localhost:" + port + "/nodepoolmanager/";
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
        System.out.println("Node Pool Manager is started...");
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
        NodePoolManagerStandaloneServer.startNodePoolManager();
    }
}
