/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.bus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class ServiceInstanceProxifierTest {

    private ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
    private static final String BUS_ADDRESS = "http://localhost:8180/services/adminExternalEndpoint";
    private static final String PROXIFIED_ADDRESS = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";

    private ServiceInstance getServiceInstance() {

        Service airlineService = models.getAirlineService();
        ServiceInstance instance = ((DeployableService) airlineService).getInstances().get(0);
        instance.setNativeUri("http://localhost:1234/airline/");

        return instance;
    }

    private EasyESBNode getEsbNode() throws EasyESBException {

        EasyESBNode esbNode = mock(EasyESBNodeImpl.class);
        when(esbNode.proxifyService(any(String.class), any(String.class))).thenReturn(PROXIFIED_ADDRESS);
        when(esbNode.getAdminEndpoint()).thenReturn(BUS_ADDRESS);
        return esbNode;
    }

    @Test
    public void test() throws EasyESBException {

        ServiceInstance instance = this.getServiceInstance();
        EasyESBNode esbNode = this.getEsbNode();

        ServiceInstanceProxifier proxifier = new ServiceInstanceProxifier();
        String proxifiedAddress = proxifier.proxify(instance, esbNode);
        assertEquals(PROXIFIED_ADDRESS, proxifiedAddress);
        assertEquals(PROXIFIED_ADDRESS, instance.getProxification().getBusUri(ServiceType.SOAP));
        assertEquals(BUS_ADDRESS, instance.getProxification().getEasyEsbNodeAdminEndpoint());
    }

}
