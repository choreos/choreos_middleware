package org.ow2.choreos.chef;

public class KnifeException extends Exception {

	private static final long serialVersionUID = -2340631758918906343L;

	private String command;
	
	/**
	 * 
	 * @return the knife command that caused the exception
	 */
	public String getCommand() {
		
		return command;
	}
}
