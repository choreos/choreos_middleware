/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes;

public class NodeNotUpgradedException extends Exception {

    private static final long serialVersionUID = 4566523788212445328L;

    public NodeNotUpgradedException(String nodeId) {
	super("Could not upgrade node " + nodeId);
    }

}
