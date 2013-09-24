package org.ow2.choreos.experiments.travelagency.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.xml.namespace.QName;

public class TravelAgencyClient {

    final static Logger logger = Logger.getLogger(TravelAgencyClient.class.getName());

    public static void main(String[] args) {
	String wsdl = "http://10.0.0.13:8080/a77fe330-f025-461e-895f-7d02ae2ae185/travelagency?wsdl";
	String namespace = "http://choreos.ow2.org/";
	String local = "TravelAgencyServiceService";

	TravelAgencyServiceService s = null;

	try {
	    s = new TravelAgencyServiceService(new URL(wsdl), new QName(namespace, local));
	} catch (MalformedURLException e) {
	    logger.warning("Failed to create URL for the wsdl Location: '" + wsdl + "', retrying as a local file");
	    logger.warning(e.getMessage());
	}

	TravelAgencyService t = s.getTravelAgencyServicePort();

	System.out.println(t.buyTrip());
    }

}
