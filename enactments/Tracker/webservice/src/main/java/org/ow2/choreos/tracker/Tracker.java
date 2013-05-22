package org.ow2.choreos.tracker;

import java.net.MalformedURLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding
@SuppressWarnings("PMD.ShortVariable")
public interface Tracker {

	@WebMethod
	void setInvocationAddress(String role, String name, List<String> endpoints);

	@WebMethod
	void setId(int id);

	@WebMethod
	String getPathIds() throws MalformedURLException;
}
