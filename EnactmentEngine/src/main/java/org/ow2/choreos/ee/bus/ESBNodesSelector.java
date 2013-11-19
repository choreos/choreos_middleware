/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.bus;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.chors.datamodel.LegacyServiceInstance;
import org.ow2.choreos.ee.bus.selector.ESBNodeSelector;
import org.ow2.choreos.ee.bus.selector.ESBNodeSelectorFactory;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.ServiceInstance;

/**
 * 
 * @author leonardo
 * 
 */
public class ESBNodesSelector {

    private static Logger logger = Logger.getLogger(ESBNodesSelector.class);

    private List<ProxificationTask> proxificationTasks = new ArrayList<ProxificationTask>();
    private ESBNodeSelector selector;
    

    void setSelector(ESBNodeSelector selector) {
        this.selector = selector;
    }
    
    /**
     * Selects EasyESB nodes to each one of the service instances. EASY_ESB
     * services (CDs) will be not proxified.
     * 
     * @param choreography
     * @return a list of proxification tasks, where nativeUri and easyEsbNode
     *         are inputs, and the result of the task must be saved on the
     *         provided proxification object; if it was not possible to select
     *         an esb node to a specific instance, there will be not a key to
     *         this instance.
     */
    public List<ProxificationTask> selectESBNodes(Choreography choreography) {
	if (selector == null)
	    selector = ESBNodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
        List<DeployableService> deployableServices = choreography.getDeployableServices();
        if (deployableServices != null && !deployableServices.isEmpty())
            createTasksForDeployableServices(choreography);
        List<LegacyService> legacyServices = choreography.getLegacyServices();
        if (legacyServices != null && !legacyServices.isEmpty())
            createTasksForLegacyServices(choreography);
        return proxificationTasks;
    }
    
    private void createTasksForDeployableServices(Choreography choreography) {
        InstancesFilter filter = new InstancesFilter();
        List<ServiceInstance> instancesToBeProxified = filter.filterDeployableServiceInstances(choreography.getDeployableServices());
        for (ServiceInstance serviceInstance : instancesToBeProxified) {
            ResourceImpact resourceImpact = serviceInstance.getServiceSpec().getResourceImpact();
            try {
                EasyESBNode esbNode = selector.select(resourceImpact, 1).get(0);
                ProxificationTask proxTask = createProxificationTask(serviceInstance, esbNode);
                proxificationTasks.add(proxTask);
            } catch (NotSelectedException e) {
                logger.error("Could not select ESB node to an instance of "
                        + serviceInstance.getServiceSpec().getName());
            }
        }        
    }
    
    private ProxificationTask createProxificationTask(ServiceInstance serviceInstance, EasyESBNode esbNode) {
        String svcName = serviceInstance.getServiceSpec().getName();
        String nativeUri = serviceInstance.getNativeUri();
        Proxification prox = new Proxification();
        serviceInstance.setProxification(prox);
        ProxificationTask proxTask = new ProxificationTask(svcName, nativeUri, prox, esbNode);
        return proxTask;
    }

    private void createTasksForLegacyServices(Choreography choreography) {
        InstancesFilter filter = new InstancesFilter();
        List<LegacyServiceInstance> instancesToBeProxified = filter.filterLegacyInstances(choreography.getLegacyServices());
        for (LegacyServiceInstance legacyInstance : instancesToBeProxified) {
            try {
                EasyESBNode esbNode = selector.select(new ResourceImpact(), 1).get(0);
                ProxificationTask proxTask = createProxificationTaskForLegacy(legacyInstance, esbNode);
                proxificationTasks.add(proxTask);
            } catch (NotSelectedException e) {
                logger.error("Could not select ESB node to an instance of "
                        + legacyInstance.getSpec().getName());
            }
        }         
    }
    
    private ProxificationTask createProxificationTaskForLegacy(LegacyServiceInstance legacyInstance, EasyESBNode esbNode) {
        String svcName = legacyInstance.getSpec().getName();
        String nativeUri = legacyInstance.getUri();
        Proxification prox = new Proxification();
        legacyInstance.setProxification(prox);
        ProxificationTask proxTask = new ProxificationTask(svcName, nativeUri, prox, esbNode);
        return proxTask;
    }
    
}
