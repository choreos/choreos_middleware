package eu.choreos.monitoring.glimpse.consumer;

import it.cnr.isti.labse.glimpse.utils.Manager;

public class GangliaConsumerLauncher {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new GangliaConsumer(
				Manager.createConsumerSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						"tcp://localhost:61616",
						"system",
						"manager",
						"GangliaFactory",
						"jms.serviceTopic",
						true,
						"consumerGanglia"),
						Manager.ReadTextFromFile(
								System.getProperty("user.dir") + "/bin/eu/choreos/monitoring/glimpse/consumer/GangliaRule.xml")
								);
		}
}

