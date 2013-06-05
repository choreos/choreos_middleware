/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;
import org.ow2.choreos.chors.ChoreographyDeployer;
import org.ow2.choreos.chors.ChoreographyNotFoundException;
import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;

public class ChorDeployerClient implements ChoreographyDeployer {

    private String host;

    public ChorDeployerClient(String host) {
	this.host = host;
    }

    private WebClient setupClient() {

	WebClient client = WebClient.create(host);

	// remove time out
	// not proud of it!
	HTTPConduit http = (HTTPConduit) WebClient.getConfig(client).getConduit();
	HTTPClientPolicy httpClientPolicy = new HTTPClientPolicy();
	httpClientPolicy.setConnectionTimeout(0); // undefined
	httpClientPolicy.setReceiveTimeout(0); // undefined
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
    public String createChoreography(ChoreographySpec chor) {

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
    public Choreography enactChoreography(String chorId) throws EnactmentException, ChoreographyNotFoundException {

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
		throw new EnactmentException("POST /chors/" + chorId + "/enactment has failed (Error " + code + ")");
	    }
	}

	System.out.println("At Client: " + chor);
	return chor;
    }

    @Override
    public void updateChoreography(String chorId, ChoreographySpec spec) throws ChoreographyNotFoundException,
	    EnactmentException {

	WebClient client = setupClient();
	client.path("chors");
	client.path(chorId);
	client.path("update");

	try {
	    client.put(spec);
	} catch (WebApplicationException e) {
	    int code = e.getResponse().getStatus();
	    if (code == 400 || code == 404) {
		throw new ChoreographyNotFoundException(chorId);
	    } else {
		throw new EnactmentException("PUT /chors/" + chorId + "/update has failed (Error " + code + ")");
	    }
	}

    }

}
