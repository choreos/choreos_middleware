/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

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
