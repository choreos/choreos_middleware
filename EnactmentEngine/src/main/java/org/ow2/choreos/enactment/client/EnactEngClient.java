package org.ow2.choreos.enactment.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.enactment.ChoreographyNotFoundException;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;

public class EnactEngClient implements EnactmentEngine {

	private String host;
	
	public EnactEngClient(String host) {
		this.host = host;
	}
	
	private WebClient setupClient() {
		
		WebClient client = WebClient.create(host);
		
		// remove time out
		// not proud of it!
		HTTPConduit http = (HTTPConduit)WebClient.getConfig(client).getConduit();
		HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
		httpClientPolicy.setConnectionTimeout(0);//indefined
		httpClientPolicy.setReceiveTimeout(0);//indefined
		http.setClient(httpClientPolicy);
		
		return client;
	}

	private Pattern pattern = Pattern.compile(".*/(.*)$");
	private String getId(Response response) {

		String location = (String) response.getMetadata().get("location").get(0);
		Matcher matcher = pattern.matcher(location);
		if (matcher.matches()) {
			return matcher.group(1);
		} else {
			return null;
		}
	}

	@Override
	public String createChoreography(ChorSpec chor) {

		WebClient client = setupClient();
		client.path("chors");
		String chorId;
		try {
    		Response response = client.post(chor);
    		chorId = getId(response);
    	} catch (WebApplicationException e) {
    		return null;
    	}

		return chorId; 
	}

	@Override
	public Choreography getChoreography(String chorId) throws ChoreographyNotFoundException {

		WebClient client = setupClient();
		client.path("chors");
		client.path(chorId);
		
		Choreography chor;
		try {
			chor = client.get(Choreography.class);
    	} catch (WebApplicationException e) {
			throw new ChoreographyNotFoundException(chorId);
    	}
		return chor;
	}

	@Override
	public Choreography enact(String chorId) throws EnactmentException, ChoreographyNotFoundException {
		
		WebClient client = setupClient();
		client.path("chors");
		client.path(chorId);
		client.path("enactment");
		Choreography chor;
		try {
			chor = client.post(null, Choreography.class);
    	} catch (WebApplicationException e) {
    		int code = e.getResponse().getStatus();
    		if (code == 400 || code == 404) {
    			throw new ChoreographyNotFoundException(chorId);
    		} else {
    			throw new EnactmentException("POST /chors/" + chorId + "/enactment has failed");
    		}
    	}
		
        return chor;
	}

}
