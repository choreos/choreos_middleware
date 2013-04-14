package org.ow2.choreos.utils;

public class SshNotConnected extends Exception {

	private static final long serialVersionUID = 705301802394825599L;
	
	public SshNotConnected() {
		
	}
	
	public SshNotConnected(String msg) {
		super(msg);
	}

}
