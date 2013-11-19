/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.bus;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.LegacyService;
import org.ow2.choreos.chors.datamodel.LegacyServiceInstance;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

/**
 * Defines what should be and what should not be proxified through EasyESB
 * 
 * @author leonardo
 *
 */
public class InstancesFilter {

    public List<ServiceInstance> filterDeployableServiceInstances(List<DeployableService> list) {
        List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
        for (DeployableService svc : list) {
            DeployableServiceSpec spec = svc.getSpec();
            if (spec.getServiceType().equals(ServiceType.SOAP)) {
                instances.addAll(svc.getInstances());
            }
        }
        return instances;
    }
    
    public List<LegacyServiceInstance> filterLegacyInstances(List<LegacyService> list) {
        List<LegacyServiceInstance> instances = new ArrayList<LegacyServiceInstance>();
        for (LegacyService svc : list) {
            if (svc.getSpec().getServiceType() == ServiceType.SOAP) {
                instances.addAll(svc.getLegacyServiceInstances());
            }
        }
        return instances;
    }
}
