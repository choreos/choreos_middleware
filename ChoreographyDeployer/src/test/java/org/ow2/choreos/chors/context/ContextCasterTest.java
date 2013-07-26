/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.chors.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.ow2.choreos.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceInstance;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;
import org.ow2.choreos.utils.LogConfigurator;

/**
 * 
 * @author leonardo
 * 
 */
public class ContextCasterTest {

    private static final String AIRLINE = ModelsForTest.AIRLINE;
    private static final String TRAVEL_AGENCY = ModelsForTest.TRAVEL_AGENCY;
    private static final String AIRLINE_URI = ModelsForTest.AIRLINE_URI;
    private static final String AIRLINE_PROXIFIED_URI = "http://localhost:8180/services/AirlineServicePortClientProxyEndpoint";
    private static final String TRAVEL_AGENCY_URI = ModelsForTest.TRAVEL_AGENCY_URI;
    private static final String TRAVEL_AGENCY_PROXIFIED_URI = "http://localhost:8180/services/TravelAgencyServicePortClientProxyEndpoint";

    private Map<String, DeployableService> deployedServices;

    private ModelsForTest models;
    private DeployableService travelService;
    private DeployableService airlineService;

    @BeforeClass
    public static void configLog() {
        LogConfigurator.configLog();
    }

    @Before
    public void setUp() {
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        travelService = models.getTravelService();
        airlineService = models.getAirlineService();

        this.deployedServices = new HashMap<String, DeployableService>();
        this.deployedServices.put(AIRLINE, airlineService);
        this.deployedServices.put(TRAVEL_AGENCY, travelService);
    }
    
    private void setUpBusUris() throws ServiceInstanceNotFoundException {

        ServiceInstance airlineInstance = ((DeployableService) (deployedServices.get(AIRLINE))).getInstances().get(0);
        airlineInstance.setBusUri(ServiceType.SOAP, AIRLINE_PROXIFIED_URI);
        ServiceInstance travelInstance = deployedServices.get(TRAVEL_AGENCY).getInstances().get(0);
        travelInstance.setBusUri(ServiceType.SOAP, TRAVEL_AGENCY_PROXIFIED_URI);
    }

    @Test
    public void shouldPassAirlineProxifiedAddressToTravelAgency() throws ContextNotSentException,
            ServiceInstanceNotFoundException {

        this.setUpBusUris();

        ContextSender sender = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = sender;
        ContextCaster caster = new ContextCaster();
        caster.cast(models.getChoreography());

        List<String> expectedAirlineUrisList = new ArrayList<String>();
        expectedAirlineUrisList.add(AIRLINE_PROXIFIED_URI);

        verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
    }

    @Test
    public void shouldPassAirlineNativeUriToTravelAgency() throws ContextNotSentException {

        ContextSender sender = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = sender;
        ContextCaster caster = new ContextCaster();
        caster.cast(models.getChoreography());

        List<String> expectedAirlineUrisList = new ArrayList<String>();
        expectedAirlineUrisList.add(AIRLINE_URI);
        verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
    }

    @After
    public void tearDown() {
        ContextSenderFactory.testing = false;
        System.out.println("Cleaning fixtures!"); // By Léo: ???
        models = null; // By Léo: ???
    }

}
