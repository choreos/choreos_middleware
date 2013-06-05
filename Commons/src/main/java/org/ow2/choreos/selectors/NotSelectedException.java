/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

public class NotSelectedException extends Exception {

    private static final long serialVersionUID = -3828919903306146926L;

    public NotSelectedException() {

    }

    public NotSelectedException(String message) {
	super(message);
    }

}
