package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventChoreos;
import it.cnr.isti.labse.glimpse.utils.Manager;

import java.io.IOException;
import java.util.Properties;

import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdEvalDaemonWrapper implements Runnable {

    private String host;
    private int port;
    private String javaNamingProviderUrl = null;
    private String thresholdListFileName = null;
    private ThresholdEvalDaemon daemon;
    private Properties probeSettingsProperties = null;

    public Properties getProperties() {

	if (probeSettingsProperties == null) {
	    probeSettingsProperties = Manager.createProbeSettingsPropertiesObject(
		    "org.apache.activemq.jndi.ActiveMQInitialContextFactory", javaNamingProviderUrl, "system",
		    "manager", "TopicCF", "jms.probeTopic", false, "probeName", "probeTopic");
	}

	return probeSettingsProperties;

    }

    private GlimpseBaseEventChoreos<String> getBaseEvent() {
	return (new GlimpseBaseEventChoreos<String>("", System.currentTimeMillis(), "", false, "", "", ""));
    }

    private boolean readConfig() {

	Properties props = new Properties();

	try {
	    props.load(ClassLoader.getSystemResourceAsStream("monitoring.properties"));
	} catch (IOException e) {
	    System.err.println("Error while loading configuration");
	    return false;
	}

	host = props.getProperty("Monitoring.gangliaLocation", "localhost");
	port = Integer.parseInt(props.getProperty("Monitoring.gangliaPort", "8649"));
	javaNamingProviderUrl = props.getProperty("Monitoring.javaNamingProviderUrl", "tcp://localhost:61616");
	thresholdListFileName = props.getProperty("Monitoring.thresholdFileListName", null);

	if (thresholdListFileName == null)
	    System.out.println("Loading default configuration...");

	return true;
    }

    @Override
    public void run() {
	if (!readConfig()) {
	    throw new IllegalArgumentException();
	}

	daemon = null;

	try {
	    daemon = new ThresholdEvalDaemon(getProperties(), host, port);
	} catch (GangliaException e) {
	    e.printStackTrace();
	}

	Config config = Config.getInstance(thresholdListFileName);

	daemon.setConfig(config);

	daemon.continuouslyEvaluateThresholdsAndSendMessages(getBaseEvent());
    }
}
