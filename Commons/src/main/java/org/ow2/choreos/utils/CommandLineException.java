/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.utils;

/**
 * Used if exit status is > 0 (error situation)
 * 
 * @author leonardo
 * 
 */
public class CommandLineException extends Exception {

    private static final long serialVersionUID = -5893337300004636570L;

    public CommandLineException(String message) {
	super(message);
    }
}
