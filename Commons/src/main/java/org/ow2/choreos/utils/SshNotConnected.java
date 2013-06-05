/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

public class SshNotConnected extends Exception {

    private static final long serialVersionUID = 705301802394825599L;

    public SshNotConnected() {

    }

    public SshNotConnected(String msg) {
	super(msg);
    }

}
