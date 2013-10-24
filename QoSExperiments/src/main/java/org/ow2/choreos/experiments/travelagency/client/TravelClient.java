package org.ow2.choreos.experiments.travelagency.client;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;

public class TravelClient {

    public static void main(String[] args) {
	String wsdl = "http://10.0.0.24:8080/ed77c78b-dc52-4bbb-a4d2-f2c4dc9a466a/travelagency?wsdl";

	String namespace = "http://choreos.ow2.org/";
	String local = "TravelAgencyServiceService";

	QName travelAgencyNamespace = new QName(namespace, local);
	TravelAgencyServiceService travel;
	try {
	    travel = new TravelAgencyServiceService(new URL(wsdl), travelAgencyNamespace);
	    TravelAgencyService wsClient = travel.getTravelAgencyServicePort();

	    System.out.println(wsClient.buyTrip());
	} catch (MalformedURLException e) {
	    e.printStackTrace();
	}

    }
}
