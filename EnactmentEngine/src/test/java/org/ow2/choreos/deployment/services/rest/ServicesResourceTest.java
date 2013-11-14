/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.services.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriBuilderException;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Service;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;

public class ServicesResourceTest {

    private static final String AIRLINE = "airline";

    private ServicesResource servicesResources;
    private String expectedServiceUUID;
    private DeployableServiceSpec serviceSpec;

    @Before
    public void setUp() throws ServiceNotCreatedException {
        this.serviceSpec = getSpec();
        ServicesManager servicesManagerMock = mock(ServicesManager.class);
        when(servicesManagerMock.createService(serviceSpec)).thenReturn(getService());
        this.servicesResources = new ServicesResource(servicesManagerMock);
    }

    private DeployableServiceSpec getSpec() {
        DeployableServiceSpec serviceSpec = new DeployableServiceSpec(AIRLINE, ServiceType.SOAP,
                PackageType.COMMAND_LINE, null, null, "http://choreos.eu/airilne.jar", 1234, "airline", 1);
        return serviceSpec;
    }

    private DeployableService getService() {

        DeployableService airline = new DeployableService(serviceSpec);
        airline.generateUUID();
        expectedServiceUUID = airline.getUUID();
        ServiceInstance instance = new ServiceInstance();
        instance.setInstanceId("1");
        instance.setNativeUri("http://hostname:1234/airline");
        List<ServiceInstance> instances = Collections.singletonList(instance);
        airline.setServiceInstances(instances);
        return airline;
    }

    @Test
    public void shouldCreateService() throws IllegalArgumentException, UriBuilderException, URISyntaxException,
            ServiceInstanceNotFoundException {

        String uri = "/services/airline";
        UriBuilder uriBuilder = mock(UriBuilder.class);
        UriInfo uriInfo = mock(UriInfo.class);
        when(uriInfo.getBaseUriBuilder()).thenReturn(uriBuilder);
        when(uriBuilder.path(any(Class.class))).thenReturn(uriBuilder);
        when(uriBuilder.path(any(String.class))).thenReturn(uriBuilder);
        when(uriBuilder.build()).thenReturn(new URI(uri));

        Response response = this.servicesResources.createService(serviceSpec, uriInfo);

        assertEquals(201, response.getStatus());
        Service entity = (Service) response.getEntity();
        assertEquals(expectedServiceUUID, entity.getUUID());
        assertEquals(serviceSpec, entity.getSpec());
        assertEquals(1, ((DeployableService) entity).getInstances().size());
        assertEquals(getService().getInstanceById("1"), ((DeployableService) entity).getInstanceById("1"));
        assertEquals(uri, response.getMetadata().get("location").get(0));
    }

}
