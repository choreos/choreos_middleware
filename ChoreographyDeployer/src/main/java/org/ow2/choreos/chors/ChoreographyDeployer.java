package org.ow2.choreos.chors;

import org.ow2.choreos.chors.datamodel.ChorSpec;
import org.ow2.choreos.chors.datamodel.Choreography;

public interface ChoreographyDeployer {
	
	/**
	 * Creates a new choreography that still have to be enacted.
	 * @param services specification of choreography services
	 * @return the id of the just created choreography
	 */
	public String createChoreography(ChorSpec chor);
	
	/**
	 * Retrieve choreography information.
	 * @param chorId the choreography id
	 * @return the choreography representation
	 * @throws ChoreographyNotFoundException if <code>chorId</code> does not exist 
	 */
	public Choreography getChoreography(String chorId) throws ChoreographyNotFoundException;

	/**
	 * Enacts a choreography
	 * @param chorId the choreography id
	 * @return choreography representation, including information about deployed services 
	 * @throws ChoreographyNotFoundException if <code>chorId</code> does not exist 
	 * @throws EnactmentException if something goes wrong 
	 */
	public Choreography enact(String chorId) throws EnactmentException, ChoreographyNotFoundException;

	/**
	 * Updates a choreography
	 * @param chorId the choreography id
	 * @return choreography representation, including information about deployed services 
	 * @throws ChoreographyNotFoundException if <code>chorId</code> does not exist 
	 * @throws EnactmentException if something goes wrong
	 */
	public void update(String chorId, ChorSpec spec) throws EnactmentException, ChoreographyNotFoundException;
	
}
