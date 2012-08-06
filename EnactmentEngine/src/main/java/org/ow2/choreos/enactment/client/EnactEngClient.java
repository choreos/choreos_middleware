package org.ow2.choreos.enactment.client;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.enactment.EnactmentEngine;
import org.ow2.choreos.enactment.EnactmentException;
import org.ow2.choreos.enactment.datamodel.ChorService;
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
	public String createChoreography(List<ChorService> services) {

		WebClient client = setupClient();
		client.path("chors");
		String chorId;
		try {
    		Response response = client.post(services);
    		chorId = getId(response);
    	} catch (WebApplicationException e) {
    		return null;
    	}

		return chorId; 
	}

	@Override
	public List<ChorService> getChorSpec(String chorId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Service> enact(String chorId) throws EnactmentException {
		
		WebClient client = setupClient();
		client.path("chors");
		client.path(chorId);
		client.path("enactment");
		List<Service> deployedServices;
		try {
			deployedServices = client.post(null, List.class);
    	} catch (WebApplicationException e) {
    		throw new EnactmentException("Failed in POST /chors/" + chorId + "enactment");
    	}
		
        return deployedServices;
	}

	@Override
	public List<Service> getEnactedServices(String chorId) {
		// TODO Auto-generated method stub
		return null;
	}

}
