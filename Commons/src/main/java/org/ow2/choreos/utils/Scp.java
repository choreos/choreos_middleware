package org.ow2.choreos.utils;


public class Scp {

    private final String hostname, user, privateKeyFile;

    public Scp(String hostname, String user, String privateKeyFile) {
	this.hostname = hostname;
	this.user = user;
	this.privateKeyFile = privateKeyFile;
    }

    public void sendFile(String filePath) throws ScpFailed {
	sendFile(filePath, "");
    }

    public void sendFile(String filePath, String targetPath) throws ScpFailed {
	OSCommand command = new OSCommand("scp -i " + privateKeyFile
		+ " -o StrictHostKeyChecking=no -o UserKnownHostsFile=/dev/null " + filePath + " " + user + "@"
		+ hostname + ":" + targetPath);
	try {
	    command.execute();
	} catch (CommandLineException e) {
	    throw new ScpFailed();
	}
    }
    
}
