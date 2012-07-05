package eu.choreos.monitoring.platform.daemon.notifier;

import eu.choreos.monitoring.platform.exception.ChoreosMonitoringException;

@SuppressWarnings("serial")
public class MessageHandlingFault extends ChoreosMonitoringException {

	public MessageHandlingFault(String exceptionMessage) {
		super(exceptionMessage);
	}

}
