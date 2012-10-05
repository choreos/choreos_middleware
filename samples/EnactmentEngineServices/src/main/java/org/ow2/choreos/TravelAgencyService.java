package org.ow2.choreos;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;


@WebService
public class TravelAgencyService implements TravelAgency {

	private static final String ERROR_MESSAGE = "Not possible to buy now";
	
	private static URL endpoint;
	
	private final SimpleLogger logger = new SimpleLoggerImpl("travel_agency.log");
	
	@WebMethod
	@Override
	public void setInvocationAddress(String role, String endpoint) {

		if (role.equals("airline")) {
			try {
				TravelAgencyService.endpoint = new URL(endpoint);
				logger.info("Endpoint to airline: " + endpoint);
			} catch (MalformedURLException e) {
				TravelAgencyService.endpoint = null;
				logger.error("Invalid airline endpoint URL: " + endpoint);
			}
		} else {
			logger.warn("Invalid role (" + role + ") in setInvocationAddress");
		}
	}
	
	@WebMethod
	@Override
	public String buyTrip() {
		
		String flightTicketNumber;
		try {
			FlightTicketNumberRetriever retriever = new FlightTicketNumberRetriever(endpoint);
			flightTicketNumber = retriever.getFlightTicketNumber(); // "33"
		} catch (IllegalStateException e) {
			logger.info(ERROR_MESSAGE);
			return ERROR_MESSAGE;
		}

		String hotelReservationNumber = "22";
		String result = flightTicketNumber + "--" + hotelReservationNumber;
		logger.info("request to buy trip; response: " + result);
		return  result;
	}

}
