/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.EnactmentException;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ServicesDeployer {

    private Choreography chor;

    private List<DeployableService> notModifiedServices;
    private List<DeployableService> servicesToDeploy;

    public ServicesDeployer(Choreography chor) {
	this.chor = chor;
    }

    /**
     * 
     * @return all the choreography deployed services (not only the just
     *         deployed)
     * @throws EnactmentException
     */
    public List<DeployableService> deployServices() throws EnactmentException {
	prepare();
	updateNodes();

	List<DeployableService> deployedServices = getDeployedServices();
	return deployedServices;
    }

    private List<DeployableService> getDeployedServices() {
	List<DeployableService> deployedServices = new ArrayList<DeployableService>(servicesToDeploy);
	deployedServices.addAll(notModifiedServices);
	return deployedServices;
    }

    private void prepare() throws EnactmentException {

	ChorDiffer differ = new ChorDiffer(chor);
	List<DeployableServiceSpec> toCreate = differ.getNewServiceSpecs();
	Map<DeployableService, DeployableServiceSpec> toUpdate = differ.getServicesToUpdate();
	notModifiedServices = differ.getNotModifiedServices();

	NewDeploymentPreparing newPreparer = new NewDeploymentPreparing(chor.getId(), toCreate);
	List<DeployableService> newServices = newPreparer.prepare();

	UpdateDeploymentPreparing preparer = new UpdateDeploymentPreparing(chor.getId(), toUpdate);
	List<DeployableService> updatedServices = preparer.prepare();

	servicesToDeploy = new ArrayList<DeployableService>(newServices);
	servicesToDeploy.addAll(updatedServices);
    }

    private void updateNodes() throws EnactmentException {
	NodesUpdater nodesUpdater = new NodesUpdater(servicesToDeploy, chor.getId());
	nodesUpdater.updateNodes();
    }

}
