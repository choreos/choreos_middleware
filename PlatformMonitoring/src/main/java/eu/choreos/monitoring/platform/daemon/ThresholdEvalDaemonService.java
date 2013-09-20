package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labsedc.glimpse.event.GlimpseBaseEventChoreos;
import it.cnr.isti.labsedc.glimpse.utils.Manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

import eu.choreos.monitoring.platform.exception.GangliaException;

public class ThresholdEvalDaemonService implements Runnable {

    private Logger logger = Logger.getLogger(ThresholdEvalDaemonService.class);

    private String host;
    private int port;
    private String javaNamingProviderUrl = null;
    private String thresholdListFileName = null;
    private ThresholdEvalDaemon daemon;
    private Properties probeSettingsProperties = null;
    private boolean running = false;

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
	    props.load(getResource("monitoring.properties"));
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

    private InputStream getResource(String resource) {

	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
	InputStream is;
	if (classLoader != null) {
	    is = classLoader.getResourceAsStream(resource);
	    if (is != null) {
		return is;
	    }
	}

	classLoader = ThresholdEvalDaemonService.class.getClassLoader();
	if (classLoader != null) {
	    is = classLoader.getResourceAsStream(resource);
	    if (is != null) {
		return is;
	    }
	}

	return ClassLoader.getSystemResourceAsStream(resource);

    }

    public void start() {
	new Thread(this).start();
	while (!running) {
	    try {
		Thread.sleep(1);
	    } catch (InterruptedException e) {
		logger.error(e);
	    }
	}
    }

    public void stop() {
	running = false;
    }

    public boolean status() {
	return running;
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

	running = true;

	int sleepTime = ThresholdEvalDaemon.getNotificationInterval();

	while (running) {
	    try {
		daemon.evaluateThresholdsSendMessagesAndSleep(getBaseEvent());
	    } catch (GangliaException e) {
		e.handleException();
		try {
		    Thread.sleep(sleepTime);
		} catch (InterruptedException e1) {
		    logger.error("It should not have happened!");
		}
	    }
	}
    }
}
