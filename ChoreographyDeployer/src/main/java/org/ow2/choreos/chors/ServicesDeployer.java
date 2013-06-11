/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;

public class ServicesDeployer {

    private Choreography chor;
    
    private Logger logger = Logger.getLogger(ServicesDeployer.class);

    public ServicesDeployer(Choreography chor) {
	this.chor = chor;
    }

    public List<DeployableService> deployServices() throws EnactmentException {
	List<DeployableService> configuredServices = null;
	if (isFirstDeployment(chor)) {
	    NewDeploymentPreparing preparer = new NewDeploymentPreparing(chor);
	    configuredServices = preparer.prepare();
	} else {
	    UpdateDeploymentPreparing preparer = new UpdateDeploymentPreparing(chor);
	    configuredServices = preparer.prepare();
	}
	NodesUpdater nodesUpdater = new NodesUpdater(configuredServices, chor.getId());
	List<DeployableService> deployedServices = nodesUpdater.updateNodes();
	logger.info("Deployement finished chorId=" + chor.getId());
	return deployedServices;
    }
    
    private boolean isFirstDeployment(Choreography chor) {
	return (chor.getServices() == null) || (chor.getServices().isEmpty());
    }
}
