import static org.junit.Assert.assertEquals;

import org.apache.geronimo.mail.util.StringBufferOutputStream;
import org.junit.Test;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

public class SshExecTest {

    @Test
    public void accessHostViaSSH() throws Exception {
        JSch jsch = new JSch();

        String host = "ec2-50-16-158-242.compute-1.amazonaws.com";
        String user = "ubuntu";

        Session session = jsch.getSession(user, host);
        jsch.setKnownHosts("/Users/danicuki/.ssh/known_hosts"); //TODO put these things in a config file
        jsch.addIdentity("/Users/danicuki/.ssh/amazon.pem");

        session.connect();

        Channel channel = session.openChannel("exec");
        ((ChannelExec) channel).setCommand("mkdir tmp1\ncd tmp1\ntouch a69\nls\ncd ..\nrm -rf tmp1\n");
        StringBuffer sb = new StringBuffer();
        channel.setOutputStream(new StringBufferOutputStream(sb));

        channel.connect();
        
        while (!channel.isClosed()) {
            Thread.sleep(10);
        }

        channel.disconnect();
        session.disconnect();

        assertEquals("a69\n", sb.toString());

    }
}
