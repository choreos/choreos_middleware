/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes;

public class NodeNotCreatedException extends Exception {

    private static final long serialVersionUID = -3018510520455978938L;

    public NodeNotCreatedException() {
	super();
    }

    public NodeNotCreatedException(String message) {
	super(message);
    }

}
