package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEventChoreos;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import eu.choreos.monitoring.platform.daemon.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.exception.MessageHandlingFault;
import eu.choreos.monitoring.platform.utils.GmondDataReader;

public class ThresholdEvalDaemon {

	private static final int NOTIFICATION_INTERVAL = 6000;
	private GlimpseMessageHandler messageHandler;
	private ThresholdManager thresholdManager;
	private Config config;

	public ThresholdEvalDaemon(Properties settings, String host, int port)
			throws GangliaException {
		this(settings, host, port, (new GlimpseMessageHandler(settings)),
				(new ThresholdManager(new HostManager(new GmondDataReader(host,
						port)))));
	}

	public ThresholdEvalDaemon(Properties settings, String host, int port,
			GlimpseMessageHandler msgHandler, ThresholdManager tshdManager)
			throws GangliaException {
		messageHandler = msgHandler;
		thresholdManager = tshdManager;
		
		if(config == null) config = Config.getInstance(null);
		
		/* set threshold map */
		thresholdManager.addAllThreshold(config.getThresholdsConfig());
	}

	public void addThreshold(String instanceType, AbstractThreshold threshold) {
		thresholdManager.addThreshold(instanceType, threshold);
	}

	public void addMultipleThreshold(String instanceType, List<AbstractThreshold> thresholdList) {
		thresholdManager.addMultipleThresholds(instanceType, thresholdList);
	}

	public void continuouslyEvaluateThresholdsAndSendMessages(GlimpseBaseEventChoreos<String> event) {

		while (true) {
			try {
				evaluateThresholdsSendMessagesAndSleep(event, NOTIFICATION_INTERVAL);
			} catch (GangliaException e) {
				e.handleException();
				sleep(NOTIFICATION_INTERVAL);
			}
		}
	}

	public void evaluateThresholdsSendMessagesAndSleep(
			GlimpseBaseEventChoreos<String> message,
			int sleepingTime) throws GangliaException {

		if (thereAreSurpassedThresholds()) {
			sendAllSurpassedThresholdMessages(message);
		}

		sleep(sleepingTime);
	}

	public boolean thereAreSurpassedThresholds() throws GangliaException {
		return thresholdManager.thereAreSurpassedThresholds();
	}

	public Map<String, List<AbstractThreshold>> getSurpassedThresholds()
			throws GangliaException {

		Map<String, List<AbstractThreshold>> evaluateAllThresholds = thresholdManager
				.getSurpassedThresholds();
		return evaluateAllThresholds;
	}

	private void sendMessage(GlimpseBaseEventChoreos<String> message) {
		try {
			messageHandler.sendMessage(message);
		} catch (MessageHandlingFault e) {
			e.handleException();
		}
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendAllSurpassedThresholdMessages(
			GlimpseBaseEventChoreos<String> event) throws GangliaException {

		Map<String, List<AbstractThreshold>> surpassedThresholds = getSurpassedThresholds();

		for (String host : surpassedThresholds.keySet()) {
			
			for (AbstractThreshold threshold : surpassedThresholds.get(host)) {
								
				event.setEventData(threshold.toEventRuleData());
				event.setTimeStamp(threshold.timestampOccur);
				event.setEventName(threshold.getName());
				event.setChoreographySource("chor");
				event.setServiceSource("");
				event.setMachineIP(host);
				event.setConsumed(false);
				
				
				sendMessage(event);
			}
		}
	}

	public void setConfig(Config config) {
		this.config = config;
	}
}
