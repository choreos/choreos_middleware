/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes;

/**
 * Used if it is not possible to connect in a node using SSH
 * 
 * @author leonardo
 * 
 */
public class NodeNotAccessibleException extends Exception {

    private static final long serialVersionUID = -6491291627017563771L;

    public NodeNotAccessibleException(String nodeId) {
	super("Cannot connect to node " + nodeId);
    }

}
