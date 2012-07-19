package org.ow2.choreos;

import java.io.IOException;

import javax.jws.WebMethod;
import javax.jws.WebService;

import org.apache.xmlbeans.XmlException;

import eu.choreos.vv.clientgenerator.Item;
import eu.choreos.vv.clientgenerator.WSClient;
import eu.choreos.vv.exceptions.FrameworkException;
import eu.choreos.vv.exceptions.InvalidOperationNameException;
import eu.choreos.vv.exceptions.WSDLException;

@WebService
public class TravelAgencyService implements TravelAgency {

	private static final String ERROR_MESSAGE = "Not possible to buy now";
	
	private static String airlineWSDL;
	
	@WebMethod
	@Override
	public String buyTrip() {

		WSClient client = getAirlineClient();
		
		if (client == null)
			return ERROR_MESSAGE;
		else {
			try {
			
				Item response = client.request("buyFlight");
				String flightTicketNumber = response.getChild("return").getContent();
				String hotelReservationNumber = "22";
				return flightTicketNumber + "--" + hotelReservationNumber ;

			} catch (InvalidOperationNameException e) {
				return ERROR_MESSAGE;
			} catch (FrameworkException e) {
				return ERROR_MESSAGE;
			} catch (NoSuchFieldException e) {
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
			return null;
		} catch (XmlException e) {
			return null;
		} catch (IOException e) {
			return null;
		} catch (FrameworkException e) {
			return null;
		}
		
		return client;
	}

	@WebMethod
	@Override
	public void setInvocationAddress(String role, String endpoint) {

		if (role.equals("airline"))
			airlineWSDL = endpoint + "?wsdl";
	}

}
