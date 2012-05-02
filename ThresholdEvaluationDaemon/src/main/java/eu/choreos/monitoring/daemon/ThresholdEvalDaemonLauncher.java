package eu.choreos.monitoring.daemon;

import java.util.Properties;

import sun.misc.JavaNetAccess;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;
import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventImpl;
import it.cnr.isti.labse.glimpse.utils.Manager;

public class ThresholdEvalDaemonLauncher {

	private static String host;
	private static int port;
	private static String javaNamingProviderUrl = null;

	private static void parseArgs(String[] args) {
		host = "localhost";
		port = 8649;

		switch (args.length) {
		case 0:
			break;
		case 3:
			javaNamingProviderUrl = args[2];
		case 2:
			port = Integer.parseInt(args[1]);
		case 1:
			host = args[0];

			break;
		default:
			System.out
					.println("USAGE: ThresholdEvalDaemon [hostLocation] [port] [java naming provider URL]");
			System.out
					.println("Default values: hostLocation = 'http://localhost/'");
			System.out.println("                port = 8649");
			host = args[0];

			System.out
					.println("Note: to set a port, the hostLocation must also be present");
			break;
		}
	}

	private static Properties getProperties() {
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

	
	public static void main(String[] args) throws InterruptedException {
		parseArgs(args);
		
		ThresholdEvalDaemon daemon = new ThresholdEvalDaemon(getProperties(),
				host, port);
		
		daemon.addThreshold(new Threshold("load_one", Threshold.MAX, 1));
		daemon.continuouslyEvaluateThresholdsAndSendMessages(getBaseEvent());
	}

}