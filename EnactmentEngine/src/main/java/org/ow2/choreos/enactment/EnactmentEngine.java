package org.ow2.choreos.enactment;

import org.ow2.choreos.enactment.datamodel.Choreography;

public interface EnactmentEngine {

	/**
	 * Enacts a choreography
	 * @param chor
	 * TODO: think about an adequate return type
	 */
	public void enact(Choreography chor);
}
