/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

public class NodeNotBootstrappedException extends Exception {

    private static final long serialVersionUID = -5145339552519586119L;

    public NodeNotBootstrappedException() {

    }

    public NodeNotBootstrappedException(String nodeId) {
	super("Node " + nodeId + " not bootstrapped!");
    }

    public NodeNotBootstrappedException(String nodeId, Throwable cause) {
	super("Node " + nodeId + " not bootstrapped!", cause);
    }
}
