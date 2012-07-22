package eu.choreos.platform.utils;

import eu.choreos.monitoring.platform.exception.ChoreosMonitoringException;

@SuppressWarnings("serial")
public class CommandRuntimeException extends ChoreosMonitoringException{

	public CommandRuntimeException(String errorMessage) {
		super(errorMessage);
	}

}
