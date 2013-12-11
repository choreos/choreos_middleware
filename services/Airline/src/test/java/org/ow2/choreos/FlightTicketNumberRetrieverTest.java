package org.ow2.choreos;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;
import java.net.URL;

import org.junit.Before;
import org.junit.Test;

public class FlightTicketNumberRetrieverTest {

	@Before
	public void setUp() {
	
		AirlineStarter.start();
	}
	
	@Test
	public void shouldRetriveFlightTicketNumberFromAirlineService() throws MalformedURLException {
		
		final String EXPECTED_FLIGHT_TICKET_NUMBER_BEGINNING = "33";
		
		URL endpointURL = new URL(AirlineStarter.SERVICE_ADDRESS);
		FlightTicketNumberRetriever retriever = new FlightTicketNumberRetriever(endpointURL);
		
		String flightTicketNumber = retriever.getFlightTicketNumber();
		
		assertTrue(flightTicketNumber.startsWith(EXPECTED_FLIGHT_TICKET_NUMBER_BEGINNING));
	}
}
