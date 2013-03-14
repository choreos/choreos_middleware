package org.ow2.choreos;

import java.lang.management.ManagementFactory;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;

@WebService
public class AirlineService implements Airline {

	private final SimpleLogger logger = new SimpleLoggerImpl("/tmp/airline.log");

	public AirlineService() {
		
		logger.info("Airline started at " + AirlineStarter.SERVICE_ADDRESS);
	}
	
	@WebMethod
	@Override
	public String buyFlight() {

		String result = "33 (Thread ID: " + ManagementFactory.getRuntimeMXBean().getName() + ")";
		logger.info("Request to buy flight; response: " + result);
		return result;
	}

}
