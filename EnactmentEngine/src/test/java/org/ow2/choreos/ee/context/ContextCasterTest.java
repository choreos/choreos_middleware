/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.ee.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.ee.context.ContextCaster;
import org.ow2.choreos.ee.context.ContextNotSentException;
import org.ow2.choreos.ee.context.ContextSender;
import org.ow2.choreos.ee.context.ContextSenderFactory;
import org.ow2.choreos.services.ServiceInstanceNotFoundException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.Proxification;
import org.ow2.choreos.services.datamodel.ServiceDependency;
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

    @Before
    public void setUp() {
        LogConfigurator.configLog();
        models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        travelService = models.getTravelService();
        airlineService = models.getAirlineService();
        this.deployedServices = new HashMap<String, DeployableService>();
        this.deployedServices.put(AIRLINE, airlineService);
        this.deployedServices.put(TRAVEL_AGENCY, travelService);
    }

    private void setUpBusUris() throws ServiceInstanceNotFoundException {

        ServiceInstance airlineInstance = deployedServices.get(AIRLINE).getInstances().get(0);
        Proxification prox = new Proxification();
        prox.setBusUri(ServiceType.SOAP, AIRLINE_PROXIFIED_URI);
        airlineInstance.setProxification(prox);

        ServiceInstance travelInstance = deployedServices.get(TRAVEL_AGENCY).getInstances().get(0);
        prox = new Proxification();
        prox.setBusUri(ServiceType.SOAP, TRAVEL_AGENCY_PROXIFIED_URI);
        travelInstance.setProxification(prox);
    }

    @Test
    public void shouldPassAirlineProxifiedAddressToTravelAgency() throws ContextNotSentException,
            ServiceInstanceNotFoundException {

        this.setUpBusUris();

        ContextSender sender = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = sender;
        ContextCaster caster = new ContextCaster(models.getChoreography());
        caster.cast();

        List<String> expectedAirlineUrisList = new ArrayList<String>();
        expectedAirlineUrisList.add(AIRLINE_PROXIFIED_URI);

        verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
    }

    @Test
    public void shouldPassAirlineNativeUriToTravelAgency() throws ContextNotSentException {

        ContextSender sender = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = sender;
        ContextCaster caster = new ContextCaster(models.getChoreography());
        caster.cast();

        List<String> expectedAirlineUrisList = new ArrayList<String>();
        expectedAirlineUrisList.add(AIRLINE_URI);
        verify(sender).sendContext(TRAVEL_AGENCY_URI, AIRLINE, AIRLINE, expectedAirlineUrisList);
    }

    @Test
    public void shouldCastContextToSampleChor() throws ContextNotSentException {

        SampleChoreography sample = new SampleChoreography();
        Choreography chor = sample.getChoreography();
        ContextSender sender = mock(ContextSender.class);
        ContextSenderFactory.testing = true;
        ContextSenderFactory.senderForTesting = sender;
        ContextCaster caster = new ContextCaster(chor);
        caster.cast();

        for (DeployableServiceSpec spec : chor.getChoreographySpec().getDeployableServiceSpecs()) {
            String consumerUri = sample.getUri(spec.getName());
            if (spec.getDependencies() != null) {
                for (ServiceDependency dep : spec.getDependencies()) {
                    String providerName = dep.getServiceSpecName();
                    String providerUri = sample.getUri(providerName);
                    verify(sender).sendContext(consumerUri, sample.getRole(), providerName,
                            Collections.singletonList(providerUri));
                }
            }
        }
    }

    @After
    public void tearDown() {
        ContextSenderFactory.testing = false;
    }

}
