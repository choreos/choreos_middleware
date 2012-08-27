package org.ow2.choreos;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;

@WebService
public class AirlineService implements Airline {

	private final SimpleLogger logger = new SimpleLoggerImpl("airline.log");

	@WebMethod
	@Override
	public String buyFlight() {

		String result = "33";
		logger.info("Request to buy flight; response: " + result);
		return result;
	}

}
