package eu.choreos.nodepoolmanager.utils;

import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.apache.log4j.Logger;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshUtil {

	private Logger logger = Logger.getLogger(SshUtil.class);
    private final String hostname, user, privateKeyFile;
    private Session session;

    public SshUtil(String hostname, String user, String privateKeyFile) throws JSchException {
        this.hostname = hostname;
        this.user = user;
        this.privateKeyFile = privateKeyFile;
    }

    private Session getSession() throws JSchException {
        if (this.session != null && this.session.isConnected()) {
            return this.session;
        }

        JSch jsch = new JSch();
        jsch.addIdentity(privateKeyFile);

        Session session = jsch.getSession(user, hostname);
        session.setConfig("StrictHostKeyChecking", "no");

        return session;
    }

    public boolean isAccessible() {
        Session session = null;

        try {
            // Once upon a time, an old session caused a lot of trouble...
            session = getSession();
            session.connect(5000);
        } catch (JSchException e) {
            return false;
        }

        // We can keep a successful session
        this.session = session;
        return true;
    }

    public String runCommand(String command) throws Exception {
        return runCommand(command, false);
    }

    public String runCommand(String command, boolean retry) throws Exception {
        String output = null;

        try {
            output = runCommandOnce(command);
        } catch (Exception e) {
            if (retry) {
                return runCommand(command, retry);
            } else {
                // Backward compatibility
                throw e;
            }
        }

        return output;
    }

    public String runCommandOnce(String command) throws JSchException, InterruptedException {
        String output = null;
        Session session = getSession();

        try {
            session.connect(5000);

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
            output = sb.toString();
        } catch (JSchException e) {
        	logger.debug("Could not connect to " + user + "@" + hostname + " with key "
                    + privateKeyFile);
            throw e;
        }

        return output;
    }
}