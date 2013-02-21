package org.ow2.choreos;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;


@WebService
public class TravelAgencyService implements TravelAgency {

    private static final String AIRLINE_ERROR_MESSAGE = "Not possible to buy now: Airline service is not working";
    private static final String AIRLINE_NOT_SET_ERROR_MESSAGE = "Not possible to buy now: I don't know the Airline service yet";

    private static List<URL> endpoints = new ArrayList<URL>();

    private final SimpleLogger logger = new SimpleLoggerImpl("/tmp/travel_agency.log");
    
    private AtomicInteger counter = new AtomicInteger();

    @WebMethod
    @Override
    public void setInvocationAddress(String role, String name, List<String> endpoints) {

        if (role.equals("airline")) {
        	String endpointStr = ""; 
            try { 
            	for(String str: endpoints) {
            		endpointStr = str;
            		TravelAgencyService.endpoints.add(new URL(endpointStr));
            		logger.info("Endpoint to airline: " + endpointStr);
            	}
            } catch (MalformedURLException e) {
                TravelAgencyService.endpoints.clear();
                logger.error("Invalid airline endpoint URL: " + endpointStr);
            }
        } else {
            logger.warn("Invalid role (" + role + ") in setInvocationAddress");
        }
    }
	
    @WebMethod
    @Override
    public String buyTrip() {
    	
    	if(endpoints.size() == 0) {
    		logger.error(AIRLINE_NOT_SET_ERROR_MESSAGE);
    		return AIRLINE_NOT_SET_ERROR_MESSAGE;
    	}
    	
    	int index = counter.getAndIncrement();
    	index %= endpoints.size();
    		
        String flightTicketNumber;
        try {
            FlightTicketNumberRetriever retriever = new FlightTicketNumberRetriever(endpoints.get(index));
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
