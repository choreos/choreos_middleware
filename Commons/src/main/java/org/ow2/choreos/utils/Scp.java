package org.ow2.choreos.utils;


public class Scp {

    public static Scp scpForTest;
    public static boolean testing = false;
    
    private final String hostname, user, privateKeyFile;

    public static Scp getInstance(String hostname, String user, String privateKeyFile) {
	if (!testing)
	    return new Scp(hostname, user, privateKeyFile);
	else
	    return scpForTest;
    }

    private Scp(String hostname, String user, String privateKeyFile) {
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
