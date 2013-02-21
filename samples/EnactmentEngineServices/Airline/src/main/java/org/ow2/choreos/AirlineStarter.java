package org.ow2.choreos;

import javax.xml.ws.Endpoint;

public class AirlineStarter {

	public static final String SERVICE_ADDRESS = "http://0.0.0.0:1234/airline";
	private static Endpoint endpoint;
	
	public static void start() {

		Airline service = new AirlineService();
		endpoint = Endpoint.create(service);
		endpoint.publish(SERVICE_ADDRESS);
	}

	public static void main(String[] args) {
		
		start();
	}
}
