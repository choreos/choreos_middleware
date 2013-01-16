package org.ow2.choreos;

import java.net.MalformedURLException;
import java.net.URL;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;


@WebService
public class TravelAgencyService implements TravelAgency {

    private static final String AIRLINE_ERROR_MESSAGE = "Not possible to buy now: Airline service is not working";
    private static final String AIRLINE_NOT_SET_ERROR_MESSAGE = "Not possible to buy now: I don't know the Airline service yet";

    private static URL endpoint;

    private final SimpleLogger logger = new SimpleLoggerImpl("/tmp/travel_agency.log");

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
	
        if (endpoint == null) {
			logger.error(AIRLINE_NOT_SET_ERROR_MESSAGE);
			return AIRLINE_NOT_SET_ERROR_MESSAGE;
        }

        String flightTicketNumber;
        try {
            FlightTicketNumberRetriever retriever = new FlightTicketNumberRetriever(endpoint);
            flightTicketNumber = retriever.getFlightTicketNumber(); // "33"
        } catch (IllegalStateException e) {
            logger.error(AIRLINE_ERROR_MESSAGE);
            return AIRLINE_ERROR_MESSAGE;
        }

        String hotelReservationNumber = "22";
        String result = flightTicketNumber + "--" + hotelReservationNumber;
        logger.info("request to buy trip; response: " + result);
        return  result;
    }

}
