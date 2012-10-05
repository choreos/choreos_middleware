package eu.choreos.monitoring.platform.exception;


@SuppressWarnings("serial")
public class MessageHandlingFault extends Exception {

	public MessageHandlingFault(String exceptionMessage) {
		super(exceptionMessage);
	}

	public void handleException() {
		this.printStackTrace();		
	}

}
