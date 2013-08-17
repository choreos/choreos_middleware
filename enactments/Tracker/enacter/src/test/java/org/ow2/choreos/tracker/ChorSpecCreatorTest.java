package org.ow2.choreos.tracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.ServiceSpec;

public class ChorSpecCreatorTest {

    @Test
    public void shouldCreateSpecWithSize5() {
        ChorSpecCreator creator = new ChorSpecCreator();
        ChoreographySpec chorSpec = creator.create(10);
        verifyTracker0SpecForSize5(chorSpec.getServiceSpecByName("tracker0"));
        verifyTracker1Spec(chorSpec.getServiceSpecByName("tracker1"));
        verifyTracker2Spec(chorSpec.getServiceSpecByName("tracker2"));
        verifyTracker3Spec(chorSpec.getServiceSpecByName("tracker3"));
        verifyTracker4Spec(chorSpec.getServiceSpecByName("tracker4"));
    }
    
    private void verifyTracker0SpecForSize5(ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker1", spec.getDependencies().get(0).getServiceSpecName());
    }
    
    private void verifyTracker1Spec(ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertTrue(!dep1.equals(dep2));
        assertTrue(dep1.equals("tracker2") || dep2.equals("tracker2"));
        assertTrue(dep1.equals("tracker4") || dep2.equals("tracker4"));
    }
    
    private void verifyTracker2Spec(ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker3", spec.getDependencies().get(0).getServiceSpecName());
    }
    
    private void verifyTracker4Spec(ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker3", spec.getDependencies().get(0).getServiceSpecName());
    }
    
    private void verifyTracker3Spec(ServiceSpec spec) {
        assertTrue(spec.getDependencies() == null);
    }
    
    @Test
    public void shouldCreateSpecWithSize10() {
        ChorSpecCreator creator = new ChorSpecCreator();
        ChoreographySpec chorSpec = creator.create(10);
        verifyTracker0SpecForSize10(chorSpec.getServiceSpecByName("tracker0"));
        verifyTracker1Spec(chorSpec.getServiceSpecByName("tracker1"));
        verifyTracker2Spec(chorSpec.getServiceSpecByName("tracker2"));
        verifyTracker3Spec(chorSpec.getServiceSpecByName("tracker3"));
        verifyTracker4Spec(chorSpec.getServiceSpecByName("tracker4"));
        verifyTracker5Spec(chorSpec.getServiceSpecByName("tracker5"));
        verifyTracker6Spec(chorSpec.getServiceSpecByName("tracker6"));
        verifyTracker7Spec(chorSpec.getServiceSpecByName("tracker7"));
        verifyTracker8Spec(chorSpec.getServiceSpecByName("tracker8"));
        verifyTracker9Spec(chorSpec.getServiceSpecByName("tracker9"));        
    }
    
    private void verifyTracker0SpecForSize10(ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertTrue(!dep1.equals(dep2));
        assertTrue(dep1.equals("tracker1") || dep2.equals("tracker1"));
        assertTrue(dep1.equals("tracker5") || dep2.equals("tracker5"));
    }
    
    private void verifyTracker5Spec(ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker6", spec.getDependencies().get(0).getServiceSpecName());
    }
    
    private void verifyTracker6Spec(ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertTrue(!dep1.equals(dep2));
        assertTrue(dep1.equals("tracker7") || dep2.equals("tracker7"));
        assertTrue(dep1.equals("tracker9") || dep2.equals("tracker9"));
    }
    
    private void verifyTracker7Spec(ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker8", spec.getDependencies().get(0).getServiceSpecName());
    }
    
    private void verifyTracker9Spec(ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker8", spec.getDependencies().get(0).getServiceSpecName());
    }
    
    private void verifyTracker8Spec(ServiceSpec spec) {
        assertTrue(spec.getDependencies() == null);
    }


}
