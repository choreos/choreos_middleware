/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chef;

public class KnifeException extends Exception {

    private static final long serialVersionUID = -2340631758918906343L;

    private String command;

    /**
     * 
     * @param message
     *            explanation about the exception
     * @param command
     *            the knife command that caused the exception
     */
    public KnifeException(String message, String command) {

	super(message);
	this.command = command;
    }

    /**
     * 
     * @return the knife command that caused the exception
     */
    public String getCommand() {

	return command;
    }
}
