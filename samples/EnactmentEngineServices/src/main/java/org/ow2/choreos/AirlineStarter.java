package org.ow2.choreos;

import javax.xml.ws.Endpoint;

public class AirlineStarter {

	public static void main(String[] args) {

		Airline service = new AirlineService();
		Endpoint endpoint = Endpoint.create(service);
		endpoint.publish("http://0.0.0.0:1234/airline");
	}

}
