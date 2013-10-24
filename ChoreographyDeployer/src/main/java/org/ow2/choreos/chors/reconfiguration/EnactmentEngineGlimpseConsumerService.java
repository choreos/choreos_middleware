package org.ow2.choreos.chors.reconfiguration;

import org.apache.log4j.Logger;

public class EnactmentEngineGlimpseConsumerService {

    Logger logger = Logger.getLogger("reconfLogger");

    public void execute() {
	logger.info("Creating GLIMPSE consumer...");
	EnactmentEngineGlimpseConsumerRunnable consumer = new EnactmentEngineGlimpseConsumerRunnable();
	Thread t = new Thread(consumer);
	t.start();
	logger.info("Consumer started");
    }

    public static void main(String[] args) {
	new EnactmentEngineGlimpseConsumerService().execute();
    }
}
