package org.ow2.choreos.enactment;

import java.util.Map;

import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public interface EnactmentEngine {

	/**
	 * Enacts a choreography
	 * @param chor the choreogrpahy specification
	 * @return a map whose keys are service names, and
	 * values contains information about deployed services 
	 */
	public Map<String, Service> enact(Choreography chor);
}
