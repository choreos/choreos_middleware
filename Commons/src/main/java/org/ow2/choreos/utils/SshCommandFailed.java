package org.ow2.choreos.utils;

public class SshCommandFailed extends Exception {

    private static final long serialVersionUID = 3097969583524886274L;

    private final String command;

    public SshCommandFailed(String command) {
	this.command = command;
    }

    public String getCommand() {
	return this.command;
    }

}
