/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.services;

import java.util.Set;

import org.apache.log4j.Logger;
import org.ow2.choreos.ee.services.preparer.PrepareDeploymentFailedException;
import org.ow2.choreos.ee.services.preparer.ServiceDeploymentPreparer;
import org.ow2.choreos.ee.services.preparer.ServiceDeploymentPreparerFactory;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ServiceCreator {

    private static Logger logger = Logger.getLogger(ServiceCreator.class);

    private DeployableService service = null;

    ServiceCreator() {
    }

    public DeployableService createService(DeployableServiceSpec serviceSpec) throws ServiceNotCreatedException {

	try {
	    service = new DeployableService(serviceSpec);
	    service.generateUUID();
	} catch (IllegalArgumentException e1) {
	    logger.error("Could not create service " + serviceSpec.getName());
	    throw new ServiceNotCreatedException(serviceSpec.getName());
	}

	Set<CloudNode> selectedNodes = prepareDeployment();
	service.setSelectedNodes(selectedNodes);

	logger.info("Created service " + service);
	return service;

    }

    private Set<CloudNode> prepareDeployment() throws ServiceNotCreatedException {
	ServiceDeploymentPreparer deploymentPreparer = ServiceDeploymentPreparerFactory.getNewInstance(
		service.getSpec(), service);
	try {
	    return deploymentPreparer.prepareDeployment();
	} catch (PrepareDeploymentFailedException e1) {
	    logger.error("Could not prepare the deployment of the service " + service.getUUID() + " ("
		    + service.getSpec().getName() + ")");
	    throw new ServiceNotCreatedException(service.getSpec().getName());
	}
    }
}
