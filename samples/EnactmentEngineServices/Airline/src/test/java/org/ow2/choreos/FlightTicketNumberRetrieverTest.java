package org.ow2.choreos;

import java.net.MalformedURLException;

import org.junit.Before;
import org.junit.Test;

public class FlightTicketNumberRetrieverTest {

	@Before
	public void setUp() {
	
		AirlineStarter.start();
	}
	
	@Test
	public void shouldRetriveFlightTicketNumberFromAirlineService() throws MalformedURLException {
		
		/*final String EXPECTED_FLIGHT_TICKET_NUMBER = "33";
		
		URL endpointURL = new URL(AirlineStarter.SERVICE_ADDRESS);
		FlightTicketNumberRetriever retriever = null ; //new FlightTicketNumberRetriever(endpointURL);
		
		String flightTicketNumber = retriever.getFlightTicketNumber();
		
		assertEquals(EXPECTED_FLIGHT_TICKET_NUMBER, flightTicketNumber);*/
	}
}
