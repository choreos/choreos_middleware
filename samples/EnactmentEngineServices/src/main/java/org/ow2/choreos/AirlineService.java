package org.ow2.choreos;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class AirlineService implements Airline {

	@WebMethod
	@Override
	public String buyFlight() {

		return "33";
	}

}
