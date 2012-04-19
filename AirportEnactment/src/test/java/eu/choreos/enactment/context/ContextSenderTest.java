package eu.choreos.enactment.context;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests requirements:
 * Run hotel service on localhost.
 * Just the POWS is enough.
 * 
 * @author leonardo
 *
 */
public class ContextSenderTest {

	private static final String HOTEL_ENDPOINT = "http://localhost:8181/hotel";
	
	@Test
	public void shouldSetInvokeAddressOnHotelService() {
		
		String role = "airline";
		String airlineEndpoint = "http://localhost:8181/airline";
		
		ContextSender sender = new ContextSender();
		boolean status = sender.sendContext(HOTEL_ENDPOINT, role, airlineEndpoint);
		assertTrue(status);
	}
}
