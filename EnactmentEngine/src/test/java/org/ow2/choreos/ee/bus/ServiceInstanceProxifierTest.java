/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.bus;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.ow2.choreos.ee.bus.EasyESBException;
import org.ow2.choreos.ee.bus.EasyESBNode;
import org.ow2.choreos.ee.bus.EasyESBNodeImpl;
import org.ow2.choreos.ee.bus.ProxificationTask;
import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ServiceInstanceProxifierTest {

    private static final String SERVICE_NAME = "airline";
    private static final String NATIVE_URI = "http://localhost:1234/airline/";
    private static final String BUS_ADDRESS = "http://localhost:8180/services/adminExternalEndpoint";
    private static final String PROXIFIED_ADDRESS = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";

    private EasyESBNode getEsbNode() throws EasyESBException {
        EasyESBNode esbNode = mock(EasyESBNodeImpl.class);
        when(esbNode.proxifyService(any(String.class), any(String.class))).thenReturn(PROXIFIED_ADDRESS);
        when(esbNode.getAdminEndpoint()).thenReturn(BUS_ADDRESS);
        return esbNode;
    }

    @Test
    public void test() throws EasyESBException {
        EasyESBNode esbNode = this.getEsbNode();
        Proxification proxification = new Proxification();
        ProxificationTask task = new ProxificationTask(SERVICE_NAME, NATIVE_URI, proxification, esbNode);
        String proxifiedAddress = task.call();
        assertEquals(PROXIFIED_ADDRESS, proxifiedAddress);
        assertEquals(PROXIFIED_ADDRESS, proxification.getBusUri(ServiceType.SOAP));
        assertEquals(BUS_ADDRESS, proxification.getEasyEsbNodeAdminEndpoint());
    }

}
