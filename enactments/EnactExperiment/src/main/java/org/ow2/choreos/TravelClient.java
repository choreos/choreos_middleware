package org.ow2.choreos;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;

/**
 * Not working
 * @author leonardo
 *
 */
public class TravelClient {

	private static final String NAMESPACE = "http://choreos.ow2.org";
	private static final String TRAVEL_AGENCY = "travelagency";	

	
	public static TravelAgency getClient(String wsdl) {
        
		QName qname = new QName(NAMESPACE, TRAVEL_AGENCY);
        URL url = null;

        try {
            url = new URL(wsdl);
        } catch (MalformedURLException e) {
            System.out.println("MalformedURL: " + wsdl);
            e.printStackTrace();
        }

        System.out.println("creating");
        Service service = Service.create(url, qname);
        System.out.println("created");

        return service.getPort(TravelAgency.class);
	}
	
	public static void main(String[] args) {
		
		String wsdl = "http://107.20.27.212:1235/travelagency?wsdl";
		TravelAgency agency = getClient(wsdl);
		System.out.println("GOT!");
		String result = agency.buyTrip();
		System.out.println(result);
	}
}
