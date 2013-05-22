package eu.choreos.enactment.utils;

import org.apache.geronimo.mail.util.StringBufferOutputStream;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshUtil {

    private final String hostname, user, privateKeyFile;

    public SshUtil(String hostname, String user, String privateKeyFile) {
        
    	this.hostname = hostname;
    	this.user = user;
    	this.privateKeyFile = privateKeyFile;
    }
    
    public boolean isAccessible() {
        try{
        	Session session = getSshSession();
        	session.connect(5000);
        } catch (JSchException e) {
            return false;
        }
        return true;
    }

    public String runCommand(String command) throws Exception {
        Session session = getSshSession();

        try {
        	session.connect(5000);
        }
        catch(JSchException e) {
        	System.out.println("Could not connect " + user + "@" + hostname + " with " + privateKeyFile);
        }
        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand(command);
        StringBuffer sb = new StringBuffer();
        channel.setOutputStream(new StringBufferOutputStream(sb));

        channel.connect();

        while (!channel.isClosed()) {
            Thread.sleep(10);
        }

        channel.disconnect();
        session.disconnect();
        return sb.toString();
    }

	private Session getSshSession() throws JSchException {
		
        JSch jsch = new JSch();       
        jsch.addIdentity(privateKeyFile);
        
        Session session = jsch.getSession(user, hostname);
        session.setConfig("StrictHostKeyChecking", "no");
        
		return session;
	}

}
