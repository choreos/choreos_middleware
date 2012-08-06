package org.ow2.choreos.enactment;

import org.ow2.choreos.enactment.datamodel.ChorSpec;
import org.ow2.choreos.enactment.datamodel.Choreography;

public interface EnactmentEngine {
	
	/**
	 * Creates a new choreography that still have to be enacted.
	 * @param services specification of choreography services
	 * @return the id of the just created choreography
	 */
	public String createChoreography(ChorSpec chor);
	
	/**
	 * Retrieve choreography information.
	 * @param chorId the choreography id
	 * @return <code>null</code> if <code>chorId</code> does not exist 
	 */
	public Choreography getChoreography(String chorId);

	/**
	 * Enacts a choreography
	 * @param chorId the choreography id
	 * @return information about deployed services. 
	 *         <code>null</code> if <code>chorId</code> does not exist
	 * @throws EnactmentException if something goes wrong 
	 */
	public Choreography enact(String chorId) throws EnactmentException;
	
}
