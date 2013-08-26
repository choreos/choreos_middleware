package monitoring.glimpse.consumer.daemon;

import it.cnr.isti.labse.glimpse.utils.Manager;

public class MyGlimpseConsumerLauncher {

    public static void main(String[] args) {

	new MyGlimpseConsumer(

	Manager.createConsumerSettingsPropertiesObject("org.apache.activemq.jndi.ActiveMQInitialContextFactory",
		"tcp://10.0.0.8:61616", "system", "manager", "TopicCF", "jms.serviceTopic", true, "consumerTest"),
		Manager.ReadTextFromFile(ClassLoader.getSystemResource("SLAViolations.xml").getFile()));
    }
}
