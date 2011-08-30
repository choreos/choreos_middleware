package br.usp.ime.choreos.nodepoolmanager.utils;

import org.apache.geronimo.mail.util.StringBufferOutputStream;

import br.usp.ime.choreos.nodepoolmanager.Configuration;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class SshUtil {

    private final String hostname;

    public SshUtil(String hostname) {
        this.hostname = hostname;
    }

    public boolean isAccessible() {
        JSch jsch = new JSch();
        try {
            jsch.addIdentity(Configuration.get("AMAZON_SSH_IDENTITY"));
            // jsch.setKnownHosts(Configuration.get("SSH_KNOWN_HOSTS"));
        } catch (JSchException e) {
            e.printStackTrace();
        }
        Session session;
        try {
            session = jsch.getSession("ubuntu", hostname);
            session.setConfig("StrictHostKeyChecking", "no");
            session.connect(5000);
        } catch (JSchException e) {
            return false;
        }
        return true;
    }

    public String runCommand(String command) throws Exception {
        String user = "ubuntu";

        JSch jsch = new JSch();
        jsch.addIdentity(Configuration.get("AMAZON_SSH_IDENTITY"));
        // jsch.setKnownHosts(Configuration.get("SSH_KNOWN_HOSTS"));
        Session session = jsch.getSession(user, hostname);
        session.setConfig("StrictHostKeyChecking", "no");

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
        return sb.toString();
    }

}
