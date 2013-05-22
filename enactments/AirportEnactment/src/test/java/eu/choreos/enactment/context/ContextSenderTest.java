package eu.choreos.enactment.context;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.List;

import org.apache.xmlbeans.XmlException;
import org.junit.Test;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.ItemImpl;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.MockDeploymentException;
import eu.choreos.vv.exceptions.NoMockResponseException;
import eu.choreos.vv.exceptions.NoReplyWithStatementException;
import eu.choreos.vv.exceptions.ParserException;
import eu.choreos.vv.exceptions.WSDLException;
import eu.choreos.vv.servicesimulator.MockResponse;
import eu.choreos.vv.servicesimulator.WSMock;

/**
 * Tests requirements:
 * Hotel service running on localhost.
 * Just the POWS is enough.
 * 
 * Test not working!
 * 
 * @author leonardo
 *
 */
public class ContextSenderTest {

	private static final String HOTEL_ENDPOINT = "http://localhost:8181/hotel";
	private static final String AIRLINE_ENDPOINT = "http://localhost:8181/airline";
	
	@Test
	public void shouldPassAirlineAddressToHotelService() {

		// hotel mock setup
//		WSMock mock = getHotelMock();
		
		// invocation arguments
//		String hotelMockEndpoint = getEndpoint(mock.getWsdl());
		String partnerRole = "airline";
		
		// doing the job
		ContextSender sender = new ContextSender();
		boolean status = sender.sendContext(HOTEL_ENDPOINT, 
				partnerRole, AIRLINE_ENDPOINT);
		assertTrue(status);
		
		// checking message exchange
//		List<Item> messages = mock.getInterceptedMessages(); 
//		assertTrue(findMessage(messages));
	}

	private boolean findMessage(List<Item> messages) {

		for (Item item: messages) {
			System.out.println(item);
		}
		return false;
	}
	
	private WSMock getHotelMock() {
		WSMock mock = null;
		try {
			mock = new WSMock("hotel", "8182", HOTEL_ENDPOINT + "?wsdl", true);

			Item setInvocationAddress = new ItemImpl("setInvocationAddress");
			setInvocationAddress.addChild("arg1").setContent("?");
			setInvocationAddress.addChild("arg0").setContent("?");			
			
			Item responseItem = new ItemImpl("setInvocationAddressResponse");
			MockResponse response = new MockResponse().whenReceive(setInvocationAddress).replyWith(responseItem);
			mock.returnFor("setInvocationAddress", response);
			mock.start();
		} catch (WSDLException e) {
			e.printStackTrace();
			fail();
		} catch (XmlException e) {
			e.printStackTrace();
			fail();
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		} catch (ParserException e) {
			e.printStackTrace();
			fail();
		} catch (InvalidOperationNameException e) {
			e.printStackTrace();
			fail();
		} catch (NoMockResponseException e) {
			e.printStackTrace();
			fail();
		} catch (NoReplyWithStatementException e) {
			e.printStackTrace();
			fail();
		} catch (MockDeploymentException e) {
			e.printStackTrace();
			fail();
		}
		return mock;
	}
	
	private String getEndpoint(String wsdl) {
		
		return wsdl.substring(0, wsdl.length()-5);
	}
}
