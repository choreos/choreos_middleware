package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
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

	@SuppressWarnings("unused")
	private static void parseArgs(String[] args) {
		host = "localhost";
		port = 8649;
		javaNamingProviderUrl=null;

		switch (args.length) {

		case 4:
			javaNamingProviderUrl = args[3];

		case 3:
			port = Integer.parseInt(args[2]);

		case 2:
			host = args[1];

		case 1:
			thresholdListFileName = args[0];
			break;

		default:
			System.out
			.println("USAGE: ThresholdEvalDaemon THRESHOLD_LIST_FILE [HOST_LOCATION] [PORT] [JAVA_NAMING_PROVIDER_URL]");
			System.out
			.println("Default values: hostLocation = 'http://localhost/'");
			System.out.println("                port = 8649");
			System.out
			.println("Note: to set a port, the hostLocation must also be present");
			System.exit(1);
		}
	}

	public static Properties getProperties() {
		if (javaNamingProviderUrl == null)
			javaNamingProviderUrl = "tcp://dsbchoreos.petalslink.org:61616";
		Properties probeSettingsProperties = Manager
				.createProbeSettingsPropertiesObject(
						"org.apache.activemq.jndi.ActiveMQInitialContextFactory",
						javaNamingProviderUrl, "system", "manager",
						"GangliaFactory", "jms.probeTopic", true, "probeName",
						"probeTopic");
		return probeSettingsProperties;

	}

	private static GlimpseBaseEventImpl<String> getBaseEvent() {

		return (new GlimpseBaseEventImpl<String>(
				"thresholdAlarm", 				// event name
				"connector1",     				// connector id
				"connInstance1",  				// connector instance id
				"connExecution1",	 			// connector instance execution id
				1,                				// event id
				2,                				// event in response to id
				System.currentTimeMillis(), 	// time stamp
				"unknown", 							// networked system source
				false 							// is exception
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
		thresholdListFileName = props.getProperty("Monitoring.thresholdFileListName", null); // uses default

		if(thresholdListFileName == null)
			System.out.println("Loading default configuration...");

		return true;
	}

}