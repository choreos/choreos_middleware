/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

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
