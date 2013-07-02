/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import java.util.Map;

import org.apache.log4j.Logger;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

import esstar.petalslink.com.service.management._1_0.ManagementException;

public class ServiceInstancesProxifier {

    private Logger logger = Logger.getLogger(ServiceInstancesProxifier.class);

    /**
     * Proxifies all the instances.
     * 
     * Side effect: the proxified address is set on the esbUris(SOAP) property
     * on the instance object. If a instance can be not proxified, an error
     * message is logged.
     * 
     * @param instancesNodesMap
     */
    public void proxify(Map<ServiceInstance, EasyESBNode> instancesNodesMap) {

        for (ServiceInstance instance : instancesNodesMap.keySet()) {

            EasyESBNode esbNode = instancesNodesMap.get(instance);
            ServiceInstanceProxifier proxifier = new ServiceInstanceProxifier();
            String svcName = instance.getServiceSpec().getName();
            try {
                String proxifiedAddress = proxifier.proxify(instance, esbNode);
                instance.setBusUri(ServiceType.SOAP, proxifiedAddress);
                logger.info(svcName + " instance proxified");
            } catch (ManagementException e) {
                logger.error(svcName + " could not be proxified");
            }
        }
    }
}
