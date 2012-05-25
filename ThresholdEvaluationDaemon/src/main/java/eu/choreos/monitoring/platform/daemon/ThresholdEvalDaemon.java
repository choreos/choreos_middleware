package eu.choreos.monitoring.platform.daemon;

import it.cnr.isti.labse.glimpse.event.GlimpseBaseEvent;

import java.util.List;
import java.util.Properties;

import eu.choreos.monitoring.platform.daemon.notifier.GlimpseMessageHandler;
import eu.choreos.monitoring.platform.daemon.notifier.MessageHandlingFault;
import eu.choreos.monitoring.platform.exception.GangliaException;
import eu.choreos.monitoring.platform.utils.GmondDataReader;
import eu.choreos.monitoring.platform.utils.HostnameHandler;
import eu.choreos.platform.utils.CommandRuntimeException;

public class ThresholdEvalDaemon {

	private static final int ALIVE_INTERVAL = 120000;
	private static final int NOTIFICATION_INTERVAL = 6000;
	private GlimpseMessageHandler messageHandler;
	private int nonSentMessagesIterationsCounter = 0;

	private ThresholdManager thresholdManager;
	private HostManager hostManager;
	private GmondDataReader dataReader;

	public ThresholdEvalDaemon(Properties settings, String host, int port) throws GangliaException {
		this(settings, host, port, (new GlimpseMessageHandler(settings)),
				(new GmondDataReader(host, port)));
	}

	public ThresholdEvalDaemon(Properties settings, String host, int port,
			GlimpseMessageHandler msgHandler, GmondDataReader dataReader) throws GangliaException {
		messageHandler = msgHandler;
		this.dataReader = dataReader;
		thresholdManager = new ThresholdManager(dataReader);
		hostManager = new HostManager(dataReader);
	}

	public void addThreshold(Threshold threshold) {
		thresholdManager.addThreshold(threshold);
	}

	public void addMultipleThreshold(List<Threshold> thresholdList) {
		thresholdManager.addMultipleThresholds(thresholdList);
	}

	public void continuouslyEvaluateThresholdsAndSendMessages(
			GlimpseBaseEvent<String> message) {

		while (true) {
			try {
				evaluateThresholdsSendMessagesAndSleep(message,
						NOTIFICATION_INTERVAL);
			} catch (GangliaException e) {
				e.handleException();
				sleep(NOTIFICATION_INTERVAL);
			}
		}
	}

	public void evaluateThresholdsSendMessagesAndSleep(
			GlimpseBaseEvent<String> message, int sleepingTime)
			throws GangliaException {
		updateManager();
		if (thereAreSurpassedThresholds()) {
			sendAllSurpassedThresholdMessages(message);
		} else {
			nonSentMessagesIterationsCounter++;

			int timeSinceLastMessage = nonSentMessagesIterationsCounter
					* NOTIFICATION_INTERVAL;

			if (timeSinceLastMessage >= ALIVE_INTERVAL) {
				sendHeartbeat(message);
			}
		}

		sleep(sleepingTime);
	}

	private void updateManager() throws GangliaException {
		//dataReader.update();
		
	}

	public boolean thereAreSurpassedThresholds() throws GangliaException {
		return !getSurpassedThresholds().isEmpty();
	}

	public List<Threshold> getSurpassedThresholds() throws GangliaException {

		List<Threshold> evaluateAllThresholds = thresholdManager
				.getAllSurpassedThresholds();
		return evaluateAllThresholds;
	}

	public void sendHeartbeat(GlimpseBaseEvent<String> message) {
		message.setData("Alive");
		sendMessage(message);

	}

	private void sendMessage(GlimpseBaseEvent<String> message) {
		try {
			messageHandler.sendMessage(message);
		} catch (MessageHandlingFault e) {
			e.handleException();
		}
		nonSentMessagesIterationsCounter = 0;
	}

	private void sleep(int time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void sendAllSurpassedThresholdMessages(
			GlimpseBaseEvent<String> message) throws GangliaException {
		for (Threshold surpassed : getSurpassedThresholds()) {
			message.setData(surpassed.toString());
			sendMessage(message);
		}
	}

	public boolean thereAreHostsDown() throws GangliaException {
		return hostManager.thereAreHostsDown();
	}

}
