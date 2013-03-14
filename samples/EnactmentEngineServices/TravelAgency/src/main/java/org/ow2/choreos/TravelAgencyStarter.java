package org.ow2.choreos;

import javax.xml.ws.Endpoint;

public class TravelAgencyStarter {
	
	public static final String SERVICE_ADDRESS = "http://0.0.0.0:1235/travelagency";
	private static Endpoint endpoint;

	public static void start() {
	
		TravelAgency service = new TravelAgencyService();
		endpoint = Endpoint.create(service);
		endpoint.publish("http://0.0.0.0:1235/travelagency");
	}
	
	public static void main(String[] args) {

		start();
	}
	
}
