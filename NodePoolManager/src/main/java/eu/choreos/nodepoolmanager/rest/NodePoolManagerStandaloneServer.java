package eu.choreos.nodepoolmanager.rest;

import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;



public class NodePoolManagerStandaloneServer implements Runnable {

	private static String LOCAL_HOST = "http://localhost:8080/";
    private static boolean running = false;

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
        sf.setResourceClasses(NodeResource.class, NodesResource.class);
        sf.setAddress(LOCAL_HOST);
        sf.create();
        System.out.println("Starting CHOReOS Middleware ...");
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
