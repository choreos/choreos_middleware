/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.cm;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class NodeChecker {

    /**
     * Verify if node is ready to be used. If not, try to provide preconditions:
     * chef node name valid (listed on chef server) and node bootstrapped. If it
     * is not possible to satisfy preconditions, throws an exception.
     * 
     * @param node
     * @throws NodeNotOKException
     */
    public void checkAndPrepareNode(CloudNode node) throws NodeNotOKException {
	;
    }

    /**
     * Verify if node chef name is listed by chef server
     * 
     * @param node
     * @return
     */
    public boolean checkNodeOnNodesList(CloudNode node) {
	return true;
    }

}
