/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.services;

public class ServiceInstanceNotFoundException extends ServiceDeployerException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1175784736408532584L;

    public ServiceInstanceNotFoundException(String serviceName, String instanceId) {
        super(instanceId + " of " + serviceName);
    }

    public ServiceInstanceNotFoundException(String serviceName, String instanceId, String message) {
        super(instanceId + " of " + serviceName, message);
    }

    @Override
    public String toString() {
        return "Could not find service instance in " + super.getServiceName();
    }

}
