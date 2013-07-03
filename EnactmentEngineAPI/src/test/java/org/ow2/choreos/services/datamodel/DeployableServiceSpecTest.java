package org.ow2.choreos.services.datamodel;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.tests.ModelsForTest;

public class DeployableServiceSpecTest {

    @Test
    public void sholdClone() {
        ModelsForTest models = new ModelsForTest(ServiceType.SOAP, PackageType.COMMAND_LINE);
        DeployableServiceSpec airlineSpec = models.getAirlineSpec();
        DeployableServiceSpec cloned = airlineSpec.clone();
        assertEquals(airlineSpec, cloned);
        assertTrue(cloned != airlineSpec);
    }

}
