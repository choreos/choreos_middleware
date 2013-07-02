/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors;

import java.util.List;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;

public class ServicesDeployer {

    private Choreography chor;
    
    private List<DeployableService> configuredServices;
    private List<DeployableService> deployedServices;

    public ServicesDeployer(Choreography chor) {
        this.chor = chor;
    }

    public List<DeployableService> deployServices() throws EnactmentException {
        prepare();
        updateNodes();
        retrieveServices();
        return deployedServices;
    }

    private void prepare() throws EnactmentException {
        if (isFirstDeployment(chor)) {
            NewDeploymentPreparing preparer = new NewDeploymentPreparing(chor);
            configuredServices = preparer.prepare();
        } else {
            UpdateDeploymentPreparing preparer = new UpdateDeploymentPreparing(chor);
            configuredServices = preparer.prepare();
        }
    }

    private boolean isFirstDeployment(Choreography chor) {
        return (chor.getServices() == null) || (chor.getServices().isEmpty());
    }
    
    private void updateNodes() throws EnactmentException {
        NodesUpdater nodesUpdater = new NodesUpdater(configuredServices, chor.getId());
        nodesUpdater.updateNodes();
    }    
    
    private void retrieveServices() {
        DeployedServicesRetriever retriever = new DeployedServicesRetriever(configuredServices);
        deployedServices = retriever.retrieveDeployedServicesFromDeploymentManager();
    }
    
}
