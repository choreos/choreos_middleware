/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.nodes;

public class NPMException extends Exception {

    private static final long serialVersionUID = -4401668770096601392L;
    private final String nodeId;

    public NPMException(String nodeId) {
	this.nodeId = nodeId;
    }

    public NPMException(String nodeId, String message) {
	super(message);
	this.nodeId = nodeId;
    }

    public String getNodeId() {
	return this.nodeId;
    }
}
