package org.ow2.choreos.ee.services.update;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.ee.services.update.DecreaseNumberOfReplicas;
import org.ow2.choreos.ee.services.update.IncreaseNumberOfReplicas;
import org.ow2.choreos.ee.services.update.Migrate;
import org.ow2.choreos.ee.services.update.UpdateAction;
import org.ow2.choreos.ee.services.update.UpdateActionFactory;
import org.ow2.choreos.nodes.datamodel.MemoryType;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.services.datamodel.PackageType;
import org.ow2.choreos.services.datamodel.ServiceType;
import org.ow2.choreos.tests.ModelsForTest;

public class UpdateActionFactoryTest {

    private ModelsForTest models1 = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 2);
    private ModelsForTest models2 = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE, 2);
    
    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void shouldReturnIncreaseReplicas() {

        DeployableService airlineService = models1.getAirlineService();
        DeployableServiceSpec newAirlineSpec = models2.getAirlineSpec();
        newAirlineSpec.setNumberOfInstances(3);
        
        UpdateActionFactory fac = new UpdateActionFactory();
        List<UpdateAction> actions = fac.getActions(airlineService, newAirlineSpec);
        assertEquals(1, actions.size());
        UpdateAction action = actions.get(0);
        assertEquals(IncreaseNumberOfReplicas.class, action.getClass());
    }
    
    @Test
    public void shouldReturnDecreaseReplicas() {

        DeployableService airlineService = models1.getAirlineService();
        DeployableServiceSpec newAirlineSpec = models2.getAirlineSpec();
        newAirlineSpec.setNumberOfInstances(1);
        
        UpdateActionFactory fac = new UpdateActionFactory();
        List<UpdateAction> actions = fac.getActions(airlineService, newAirlineSpec);
        assertEquals(1, actions.size());
        UpdateAction action = actions.get(0);
        assertEquals(DecreaseNumberOfReplicas.class, action.getClass());
    }
    
    @Test
    public void shouldReturnMigrate() {

        DeployableService airlineService = models1.getAirlineService();
        DeployableServiceSpec currentAirlineSpec = models1.getAirlineSpec();
        ResourceImpact currentImpact = new ResourceImpact();
        currentImpact.setMemory(MemoryType.SMALL);
        currentAirlineSpec.setResourceImpact(currentImpact);
        DeployableServiceSpec newAirlineSpec = models2.getAirlineSpec();
        ResourceImpact newImpact = new ResourceImpact();
        newImpact.setMemory(MemoryType.LARGE);
        newAirlineSpec.setResourceImpact(newImpact);
        
        UpdateActionFactory fac = new UpdateActionFactory();
        List<UpdateAction> actions = fac.getActions(airlineService, newAirlineSpec);
        assertEquals(1, actions.size());
        UpdateAction action = actions.get(0);
        assertEquals(Migrate.class, action.getClass());
    }

}
