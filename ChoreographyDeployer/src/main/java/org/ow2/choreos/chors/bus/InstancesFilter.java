/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import java.util.ArrayList;
import java.util.List;

import org.ow2.choreos.chors.datamodel.ChoreographyService;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;

public class InstancesFilter {

    /**
     * Filter services to be proxified
     * 
     * @param list
     * @return
     */
    public List<ServiceInstance> filter(List<ChoreographyService> list) {

	List<ServiceInstance> instances = new ArrayList<ServiceInstance>();
	for (ChoreographyService svc : list) {
	    if (((DeployableServiceSpec) svc.getChoreographyServiceSpec().getServiceSpec()).getPackageType() != PackageType.EASY_ESB) {
		instances.addAll(((DeployableService) svc.getService()).getInstances());
	    }
	}
	return instances;
    }
}
