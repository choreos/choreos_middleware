package additionService;

import javax.xml.ws.Endpoint;

public class WebServicePublish {
	
	static String
		url = "http://localhost:8801/CalculatorWebService";
	
	public static void main(String[] args) {
		WebServiceExposition ws = new WebServiceExposition();
		
		Endpoint ep = Endpoint.create(ws);
		
		ep.publish(url);
	}

}
