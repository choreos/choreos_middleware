package org.ow2.choreos.chors;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.ChorDiffer;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;


public class ChorDifferTest {

    private static final String NEW_SPEC_NAME = "Airline2";
    private static final int NEW_NUMBER_OF_INSTANCES = 2;
    private Choreography chor;
    private ChoreographySpec newChorSpec;
    
    @Before
    public void setUp() throws Exception {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        ModelsForTest models2 = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        chor = models.getChoreography();
        newChorSpec = models2.getChorSpec();
        includesOneMoreSpec();
        changesOneSpec();
        ChorRegistry reg = ChorRegistry.getInstance();
        reg.addChoreography(chor);
        reg.getContext(chor.getId()).setRequestedChoreographySpec(newChorSpec);
    }
    
    private void changesOneSpec() {
        DeployableServiceSpec spec = (DeployableServiceSpec) newChorSpec.getServiceSpecByName(ModelsForTest.AIRLINE);
        spec.setNumberOfInstances(NEW_NUMBER_OF_INSTANCES);
    }
    
    private void includesOneMoreSpec() {
        DeployableServiceSpec spec = (DeployableServiceSpec) chor.getChoreographySpec().getServiceSpecByName(ModelsForTest.AIRLINE);
        DeployableServiceSpec newSpec = new DeployableServiceSpec();
        newSpec.setName(NEW_SPEC_NAME);
        newSpec.setEndpointName(spec.getEndpointName());
        newSpec.setServiceType(spec.getServiceType());
        newSpec.setPackageType(spec.getPackageType());
        newSpec.setNumberOfInstances(spec.getNumberOfInstances());
        newSpec.setPackageUri(spec.getPackageUri());
        newSpec.setPort(spec.getPort());
        newChorSpec.addServiceSpec(newSpec);
    }

    @Test
    public void test() {
        ChorDiffer differ = new ChorDiffer(chor);
        checkNotModifiedSpec(differ);
        checkNewSpec(differ);
        checkModifiedSpec(differ);
    }
    
    private void checkNotModifiedSpec(ChorDiffer differ) {
        List<DeployableService> notModifiedServices = differ.getNotModifiedServices();
        assertEquals(1, notModifiedServices.size());
        assertEquals(ModelsForTest.TRAVEL_AGENCY, notModifiedServices.get(0).getSpec().getName());
    }
    
    private void checkNewSpec(ChorDiffer differ) {
        List<DeployableServiceSpec> newServices = differ.getNewServiceSpecs();
        assertEquals(1, newServices.size());
        assertEquals(NEW_SPEC_NAME, newServices.get(0).getName());
    }

    private void checkModifiedSpec(ChorDiffer differ) {
        Map<DeployableService, DeployableServiceSpec> servicesToUpdate = differ.getServicesToUpdate();
        assertEquals(1, servicesToUpdate.size());
        DeployableService retrievedService = null;
        DeployableServiceSpec retrievedSpec = null;
        for (Entry<DeployableService, DeployableServiceSpec> entry: servicesToUpdate.entrySet()) {
            retrievedService = entry.getKey();
            retrievedSpec = entry.getValue();
        }
        DeployableService airlineService = chor.getDeployableServiceBySpecName(ModelsForTest.AIRLINE);
        assertEquals(retrievedService, airlineService);
        assertEquals(NEW_NUMBER_OF_INSTANCES, retrievedSpec.getNumberOfInstances());
    }

}
