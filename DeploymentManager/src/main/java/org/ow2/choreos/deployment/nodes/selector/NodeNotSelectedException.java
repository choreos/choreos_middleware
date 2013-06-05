/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

public class NodeNotSelectedException extends Exception {

    private static final long serialVersionUID = 6172403976309518959L;

    public NodeNotSelectedException() {

    }

    public NodeNotSelectedException(String msg) {

	super(msg);
    }
}
