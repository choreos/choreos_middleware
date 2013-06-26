/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.preparer;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class PrepareDeploymentFailedException extends Exception {

    private static final long serialVersionUID = -3910285345563830341L;

    public PrepareDeploymentFailedException(String serviceName, CloudNode node) {
	super("Service " + serviceName + " not applied on " + node);
    }
    
    public PrepareDeploymentFailedException(String serviceName) {
	super("Service " + serviceName + " not applied on any node");
    }
    
}
