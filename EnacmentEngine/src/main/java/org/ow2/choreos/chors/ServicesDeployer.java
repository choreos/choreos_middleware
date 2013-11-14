/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ServicesDeployer {

    private Choreography chor;
    
    private List<DeployableService> configuredServices;
    private List<DeployableService> notModifiedServices;
    private List<DeployableService> deployedServices;

    public ServicesDeployer(Choreography chor) {
        this.chor = chor;
    }

    /**
     * 
     * @return all the choreography deployed services (not only the just deployed)
     * @throws EnactmentException
     */
    public List<DeployableService> deployServices() throws EnactmentException {
        prepare();
        updateNodes();
        retrieveModifiedServices();
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
        
        configuredServices = new ArrayList<DeployableService>(newServices);
        configuredServices.addAll(updatedServices);
    }

    private void updateNodes() throws EnactmentException {
        NodesUpdater nodesUpdater = new NodesUpdater(configuredServices, chor.getId());
        nodesUpdater.updateNodes();
    }    
    
    private void retrieveModifiedServices() {
        DeployedServicesRetriever retriever = new DeployedServicesRetriever(configuredServices);
        deployedServices = retriever.retrieveDeployedServicesFromDeploymentManager();
    }
    
}
