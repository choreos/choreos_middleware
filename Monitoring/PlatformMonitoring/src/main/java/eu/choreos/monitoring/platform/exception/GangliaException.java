package eu.choreos.monitoring.platform.exception;

public class GangliaException extends Exception {

	private static final long serialVersionUID = 1660352346166622370L;
	public static final String CONNECTION_ERROR_UNKNOWN_HOST = "Connection Error - unknown host";
	public static final String CONNECTION_ERROR_COULD_NOT_CLOSE_SOCKET = "Connetion Error - could not close socket";
	public static final String CONNECTION_ERROR_COULD_NOT_PARSE_METRICS_INPUT = "Connetion Error - could not parse metrics input";
	public static final String CONNECTION_ERROR_COULD_NOT_READ_FROM_SOCKET = "Connetion Error - could not read from socket";

	public GangliaException(String gangliaExceptionMessage) {
		super(gangliaExceptionMessage);
	}

	public void handleException() {
		this.printStackTrace();
	}

}
