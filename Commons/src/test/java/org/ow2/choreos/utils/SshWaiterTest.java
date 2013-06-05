package org.ow2.choreos.utils;

import org.junit.BeforeClass;
import org.junit.Test;

public class SshWaiterTest {

    @BeforeClass
    public static void setUpClass() {

	LogConfigurator.configLog();
    }

    @Test(expected = SshNotConnected.class)
    public void shouldThrowExepctionToInvalidNode() throws SshNotConnected {

	String ip = "invalid_ip";
	String user = "Invalid user";
	String key = "Invalid key";

	SshWaiter sshWaiter = new SshWaiter();
	sshWaiter.waitSsh(ip, user, key, 10);
    }

}
