package org.ow2.choreos;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.xmlbeans.XmlException;
import org.ow2.choreos.log.SimpleLogger;
import org.ow2.choreos.log.SimpleLoggerImpl;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

@WebService
public class TravelAgencyService implements TravelAgency {

	private static final String ERROR_MESSAGE = "Not possible to buy now";
	
	private static String airlineWSDL;
	
	private final SimpleLogger logger = new SimpleLoggerImpl("travel_agency.log");
	
	@WebMethod
	@Override
	public String buyTrip() {

		WSClient client = getAirlineClient();
		
		if (client == null) {
			logger.error(ERROR_MESSAGE + " (airline client is null)");
			return ERROR_MESSAGE;
		}
		else {
			try {
			
				Item response = client.request("buyFlight");
				String flightTicketNumber = response.getChild("return").getContent();
				String hotelReservationNumber = "22";
				String result = flightTicketNumber + "--" + hotelReservationNumber;
				logger.info("request to buy trip; response: " + result);
				return  result;

			} catch (InvalidOperationNameException e) {
				logger.error("", e);
				return ERROR_MESSAGE;
			} catch (FrameworkException e) {
				logger.error("", e);
				return ERROR_MESSAGE;
			} catch (NoSuchFieldException e) {
				logger.error("", e);
				return ERROR_MESSAGE;
			}
		}
	}

	private WSClient getAirlineClient() {

		if (airlineWSDL == null)
			return null;
		
		WSClient client;
		try {
			client = new WSClient(airlineWSDL);
		} catch (WSDLException e) {
			logger.error("get client failed", e);
			return null;
		} catch (XmlException e) {
			logger.error("get client failed", e);
			return null;
		} catch (IOException e) {
			logger.error("get client failed", e);
			return null;
		} catch (FrameworkException e) {
			logger.error("get client failed", e);
			return null;
		}
		
		return client;
	}

	@WebMethod
	@Override
	public void setInvocationAddress(String role, String endpoint) {

		if (role.equals("airline")) {
			logger.info("Endpoint to airline: " + endpoint);
			airlineWSDL = endpoint + "?wsdl";
		} else {
			logger.warn("Invalid role (" + role + ") in setInvocationAddress");
		}
	}

}
