package eu.choreos.nodepoolmanager.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;

import eu.choreos.nodepoolmanager.Configuration;



public class NodePoolManagerStandaloneServer implements Runnable {

	public static String LOCAL_HOST;
    private static boolean running = false;
    
    static {
    	String port = Configuration.get("NODE_POOL_MANAGER_PORT");
    	LOCAL_HOST = "http://localhost:" + port + "/nodepoolmanager/";
    }

    public static void start() throws InterruptedException {
        new Thread(new NodePoolManagerStandaloneServer()).start();
        while (!running) {
            Thread.sleep(1);
        }

    }

    public static void stop() {
        running = false;
    }

    public void run() {
        JAXRSServerFactoryBean sf = new JAXRSServerFactoryBean();
        sf.setResourceClasses(ConfigsResource.class, NodesResource.class);
        sf.setAddress(LOCAL_HOST);
        sf.create();
        System.out.println("Starting Node Pool Manager ...");
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
        NodePoolManagerStandaloneServer.start();
    }
}
