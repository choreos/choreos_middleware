package org.ow2.choreos.chors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.rest.RESTClientsRetriever;
import org.ow2.choreos.services.ServiceNotCreatedException;
import org.ow2.choreos.services.ServicesManager;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class NewDeploymentPreparingTest {

    private DeployableServiceSpec airlineSpec, travelSpec;
    private Choreography chor;
    private DeployableService airlineService, travelService;

    @Before
    public void setUp() {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        this.airlineSpec = models.getAirlineSpec();
        this.travelSpec = models.getTravelSpec();
        this.chor = models.getChoreography();
        this.airlineService = models.getAirlineService();
        this.travelService = models.getTravelService();
    }

    @Test
    public void shouldProperlyConfigureAllServices() throws ServiceNotCreatedException, EnactmentException {

        ServicesManager smMock = mock(ServicesManager.class);
        when(smMock.createService(airlineSpec)).thenReturn(airlineService);
        when(smMock.createService(travelSpec)).thenReturn(travelService);
        RESTClientsRetriever.servicesManagerForTest = smMock;
        RESTClientsRetriever.testing = true;

        NewDeploymentPreparing preparer = new NewDeploymentPreparing(chor);
        List<DeployableService> configuredServices = preparer.prepare();

        assertEquals(2, configuredServices.size());
        String airlineUUID = airlineSpec.getUuid();
        DeployableService configuredAirlineService = findServiceByUUID(airlineUUID, configuredServices);
        assertEquals(airlineService, configuredAirlineService);
        String travelUUID = travelSpec.getUuid();
        DeployableService configuredTravelService = findServiceByUUID(travelUUID, configuredServices);
        assertEquals(travelService, configuredTravelService);
    }

    private DeployableService findServiceByUUID(String uuid, List<DeployableService> services) {
        for (DeployableService svc : services) {
            if (uuid.equals(svc.getSpec().getUuid())) {
                return svc;
            }
        }
        throw new NoSuchElementException();
    }
}
