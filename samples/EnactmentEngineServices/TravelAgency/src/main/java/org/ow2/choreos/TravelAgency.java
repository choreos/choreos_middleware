package org.ow2.choreos;

import java.util.List;

public interface TravelAgency {

	/**
	 * 
	 * @return 'xx-yy', where xx is the flight ticket number, 
	 * and yy is the hotel reservation number
	 */
	public String buyTrip();
	
	public void setInvocationAddress(String role, String name, List<String> endpoint);
}
