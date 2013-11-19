/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.nodes.datamodel.CloudNode;

public class PrepareDeploymentFailedException extends Exception {

    private static final long serialVersionUID = -3910285345563830341L;

    public PrepareDeploymentFailedException(String serviceName, CloudNode node) {
        super("Deployment of service " + serviceName + " not prepared on " + node);
    }

    public PrepareDeploymentFailedException(String serviceName) {
        super("Deployment of service " + serviceName + " not preapred on any node");
    }

}
