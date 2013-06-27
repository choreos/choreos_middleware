/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.registry;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.ow2.choreos.services.datamodel.DeployableService;

public class DeployedServicesRegistry {

    private ConcurrentMap<String, DeployableService> deployedServices = new ConcurrentHashMap<String, DeployableService>();

    public void addService(String serviceId, DeployableService service) {
        deployedServices.put(serviceId, service);
    }

    public DeployableService getService(String serviceId) {
        return deployedServices.get(serviceId);
    }

    public Collection<DeployableService> getServices() {
        return deployedServices.values();
    }

    public void deleteService(String serviceId) {
        if (deployedServices.remove(serviceId) == null)
            throw new IllegalArgumentException("Service " + serviceId + " not registered");
    }

    private DeployedServicesRegistry() {

    }

    private static DeployedServicesRegistry INSTANCE = null;

    public static DeployedServicesRegistry getInstance() {
        if (INSTANCE == null)
            INSTANCE = new DeployedServicesRegistry();
        return INSTANCE;
    }

}