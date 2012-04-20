package it.cnr.isti.labse.glimpse.utils;

/**
 * This class is used to get the Monitoring Engine status.
 * 
 * @author Antonello Calabr&ograve;
 * @version 0.1
 *
 */
public enum Status {
		/**
		 * STARTING - The monitoring is booting up
		 * ACTIVE - The monitoring is ready to accept incoming requests
		 * STOPPING - The monitoring is shutting down
		 * UNAVAILABLE - The monitoring is not available for evaluations
		 */
		STARTING, ACTIVE, STOPPING, UNAVAILABLE
}
