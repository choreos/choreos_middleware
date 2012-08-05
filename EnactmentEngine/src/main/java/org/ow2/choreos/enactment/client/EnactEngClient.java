package org.ow2.choreos.enactment.client;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

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

	@Override
	public Map<String, Service> enact(Choreography chor) throws EnactmentException {

		WebClient client = setupClient();

		client.path("chors");
		String chorId;
		try {
    		Response response = client.post(chor.getServices());
    		chorId = getId(response);
    		if (chorId == null) {
    			throw new EnactmentException("POST /chors did not returned chor id");
    		}
    	} catch (WebApplicationException e) {
    		throw new EnactmentException("Failed in POST /chors");
    	}

		client.path("chors");
		client.path(chorId);
		client.path("enactment");
		Collection<Service> deployedServices;
		try {
			deployedServices = client.post(null, Collection.class);
    	} catch (WebApplicationException e) {
    		throw new EnactmentException("Failed in POST /chors/" + chorId + "enactment");
    	}
		
		Map<String, Service> deployedMap = new HashMap<String, Service>();
		for (Service svc: deployedServices) {
			deployedMap.put(svc.getName(), svc);
		}
		
        return deployedMap;
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

}
