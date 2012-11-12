package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventChoreos;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.io.IOException;
import java.util.Properties;

import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdEvalDaemonLauncher {

	private static String host;
	private static int port;
	private static String javaNamingProviderUrl = null;
	private static String thresholdListFileName = null;
	private static ThresholdEvalDaemon daemon;

	public static Properties getProperties() {
		if (javaNamingProviderUrl == null)
			javaNamingProviderUrl = "tcp://dsbchoreos.petalslink.org:61616";
		Properties probeSettingsProperties = Manager
				.createProbeSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						javaNamingProviderUrl, "system", "manager",
						"GangliaFactory", "jms.probeTopic", false, "probeName",
						"probeTopic");
		return probeSettingsProperties;

	}

	private static GlimpseBaseEventChoreos<String> getBaseEvent() {
		return (new GlimpseBaseEventChoreos<String>(
				"", // event data
				System.currentTimeMillis(), // timestamp of event 
			    "", // the event name 
				false, // is exception? 
				"", // choreography source (of event) 
				"", // service source (of event) 
				"" // host address
				));						
	}


	public static void main(String[] args) throws InterruptedException, IOException {
		//parseArgs(args);
		
		
		// testing open conf dir.
		

		if(!readConfig()) 
			printUsage();

		daemon = null;
		try {
			daemon = new ThresholdEvalDaemon(getProperties(),
					host, port);
		} catch (GangliaException e) {
			e.printStackTrace();
		}

		Config config = Config.getInstance(thresholdListFileName);

		daemon.setConfig(config);
		daemon.continuouslyEvaluateThresholdsAndSendMessages(getBaseEvent());
	}

	private static void printUsage() {
		System.out.println("Verify your monitoring.properties file.");

	}

	private static boolean readConfig() {

		Properties props = new Properties();
		try {
			props.load(ClassLoader.getSystemResourceAsStream("monitoring.properties"));
		} catch (IOException e) {
			System.err.println("Error while loading configuration");
			return false;
		}

		host = props.getProperty("Monitoring.gangliaLocation", "localhost");
		port = Integer.parseInt(props.getProperty("Monitoring.gangliaPort", "8649"));
		javaNamingProviderUrl = props.getProperty("Monitoring.javaNamingProviderUrl", "tcp://dsbchoreos.petalslink.org:61616");
		thresholdListFileName = props.getProperty("Monitoring.thresholdFileListName", null); 

		if(thresholdListFileName == null)
			System.out.println("Loading default configuration...");

		return true;
	}

}
