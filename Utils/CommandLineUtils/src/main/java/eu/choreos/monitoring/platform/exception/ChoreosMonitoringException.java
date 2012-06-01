package eu.choreos.monitoring.platform.exception;

public class ChoreosMonitoringException extends Exception {

	private static final long serialVersionUID = 1L;

	public ChoreosMonitoringException(String exceptionMessage) {
		super(exceptionMessage);
	}

	public void handleException(){
		System.out.println(getMessage());
	}
}
