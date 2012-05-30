package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.YamlParser;

public class ThresholdEvalDaemonLauncher {

	private static String host;
	private static int port;
	private static String javaNamingProviderUrl = null;
	private static String thresholdListFileName = null;

	private static void parseArgs(String[] args) {
		host = "localhost";
		port = 8649;
		javaNamingProviderUrl=null;
		thresholdListFileName = "";

		switch (args.length) {
		case 1:
			thresholdListFileName = args[0];
			break;

		case 4:
			javaNamingProviderUrl = args[3];
		
		case 3:
			port = Integer.parseInt(args[2]);
		
		case 2:
			host = args[1];
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

		return new GlimpseBaseEventImpl<String>("thresholdAlarm",
				"connector1", "connInstance1", "connExecution1", 1, 2,
				System.currentTimeMillis(), "NS1", false);

	}

	
	public static void main(String[] args) throws InterruptedException, IOException {
		parseArgs(args);
		
		ThresholdEvalDaemon daemon = null;
		try {
			daemon = new ThresholdEvalDaemon(getProperties(),
					host, port);
		} catch (GangliaException e) {
			e.printStackTrace();
		}
		
		List<Threshold> thresholdList = YamlParser.getThresholdsFromFile(thresholdListFileName);
		daemon.addMultipleThreshold(thresholdList);
		daemon.continuouslyEvaluateThresholdsAndSendMessages(getBaseEvent());
	}

}