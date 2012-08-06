package org.ow2.choreos.enactment;

import java.util.List;

import org.ow2.choreos.enactment.datamodel.Choreography;
import org.ow2.choreos.servicedeployer.datamodel.Service;

public interface EnactmentEngine {
	
	/**
	 * Creates a new choreography that still have to be enacted.
	 * @param services specification of choreography services
	 * @return the id of the just created choreography
	 */
	public String createChoreography(Choreography chor);
	
	/**
	 * Retrieve choreography specification.
	 * @param chorId the choreography id
	 * @return <code>null</code> if <code>chorId</code> does not exist 
	 */
	public Choreography getChorSpec(String chorId);

	/**
	 * Enacts a choreography
	 * @param chorId the choreography id
	 * @return information about deployed services. 
	 *         <code>null</code> if <code>chorId</code> does not exist
	 * @throws EnactmentException if something goes wrong 
	 */
	public List<Service> enact(String chorId) throws EnactmentException;
	
	/**
	 * 
	 * @param chorId the choreography id
	 * @return information about deployed services.
	 *         <code>null</code> if <code>chorId</code> does not exist 
	 */
	public List<Service> getEnactedServices(String chorId);
}
