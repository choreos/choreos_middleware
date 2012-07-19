package org.ow2.choreos;

import javax.xml.ws.Endpoint;

public class TravelAgencyStarter {

	public static void main(String[] args) {

		TravelAgency service = new TravelAgencyService();
		Endpoint endpoint = Endpoint.create(service);
		endpoint.publish("http://localhost:1235/travelagency");
	}
	
}
