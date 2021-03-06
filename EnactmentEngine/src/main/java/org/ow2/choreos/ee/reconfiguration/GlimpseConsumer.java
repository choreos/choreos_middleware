package org.ow2.choreos.ee.reconfiguration;

import it.cnr.isti.labsedc.glimpse.utils.Manager;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.ow2.choreos.ee.ChoreographyContext;
import org.ow2.choreos.ee.config.QoSManagementConfiguration;

public class GlimpseConsumer implements Runnable {

    private static final String namingURL = "tcp://"
	    + QoSManagementConfiguration.get(QoSManagementConfiguration.RESOURCE_METRIC_AGGREGATOR) + ":61616";

    private final ChoreographyContext context;

    String rules;
    Properties properties;

    private boolean running = false;

    Logger logger = Logger.getLogger("reconfLogger");

    public GlimpseConsumer(ChoreographyContext context) {
	this.context = context;
	this.properties = getConsumerProperties();
	this.rules = getConsumerRules();
    }

    private Properties getConsumerProperties() {
	return Manager.createConsumerSettingsPropertiesObject("org.apache.activemq.jndi.ActiveMQInitialContextFactory",
		namingURL, "system", "manager", "TopicCF", "jms.serviceTopic", false, "eeConsumer");
    }

    private String getConsumerRules() {
	String fileContent = null;

	fileContent = new ChorRulesBuilder().assemblyGlimpseRules(this.context.getChoreography());
	logger.debug(fileContent);

	return fileContent;
    }

    @Override
    public void run() {

	logger.info("Starting running glimpse consumer for chor " + context.getChoreography().getId());
	new ChorGlimpseConsumer(this.properties, this.rules, context.getChoreography());

	logger.info("Glimpse consumer stated!");
	running = true;

	while (running) {
	    try {
		Thread.sleep(1000);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
	logger.info("Finished consumer for chor " + context.getChoreography().getId());
    }

    public void start() {
	new Thread(this).start();
	while (!running) {
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		e.printStackTrace();
	    }
	}
    }

    public void stop() {
	running = false;
    }

}
