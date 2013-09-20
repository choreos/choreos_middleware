package org.ow2.choreos.chors.reconfiguration;

import it.cnr.isti.labsedc.glimpse.utils.Manager;

import java.util.Properties;

public class EnactmentEngineGlimpseConsumerRunnable implements Runnable {

    private static final String namingURL = "tcp://10.0.0.2:61616";

    private Properties getConsumerProperties() {
	return Manager.createConsumerSettingsPropertiesObject("org.apache.activemq.jndi.ActiveMQInitialContextFactory",
		namingURL, "system", "manager", "TopicCF", "jms.serviceTopic", true, "consumerTest");

    }

    private String getConsumerRules() {
	return Manager.ReadTextFromFile(this.getClass().getClassLoader().getResource("rules/SLAViolations.xml")
		.getFile());
    }

    public void createConsumer() {
	new EnactmentEngineGlimpseConsumer(getConsumerProperties(), getConsumerRules());
    }

    @Override
    public void run() {
	createConsumer();
    }

}
