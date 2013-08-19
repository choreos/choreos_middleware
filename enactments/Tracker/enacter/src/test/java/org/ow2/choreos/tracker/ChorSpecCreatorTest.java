package org.ow2.choreos.tracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.ServiceSpec;

public class ChorSpecCreatorTest {

    @Test
    public void shouldCreateSpecWithSize5() {
        final ChorSpecCreator creator = new ChorSpecCreator();
        final ChoreographySpec chorSpec = creator.create(5);
        verifyTracker0SpecForSize5(chorSpec.getServiceSpecByName("tracker0"));
        verifyTracker1Spec(chorSpec.getServiceSpecByName("tracker1"));
        verifyTracker2Spec(chorSpec.getServiceSpecByName("tracker2"));
        verifyTracker3Spec(chorSpec.getServiceSpecByName("tracker3"));
        verifyTracker4Spec(chorSpec.getServiceSpecByName("tracker4"));
    }

    private void verifyTracker0SpecForSize5(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker1", spec.getDependencies().get(0).getServiceSpecName());
    }

    private void verifyTracker1Spec(final ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        final String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        final String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertFalse(dep1.equals(dep2));
        assertTrue("tracker2".equals(dep1) || "tracker2".equals(dep2));
        assertTrue("tracker4".equals(dep1) || "tracker4".equals(dep2));
    }

    private void verifyTracker2Spec(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker3", spec.getDependencies().get(0).getServiceSpecName());
    }

    private void verifyTracker4Spec(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker3", spec.getDependencies().get(0).getServiceSpecName());
    }

    private void verifyTracker3Spec(final ServiceSpec spec) {
        assertNull(spec.getDependencies());
    }

    @Test
    public void shouldCreateSpecWithSize10() {
        final ChorSpecCreator creator = new ChorSpecCreator();
        final ChoreographySpec chorSpec = creator.create(10);
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

    private void verifyTracker0SpecForSize10(final ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        final String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        final String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertFalse(dep1.equals(dep2));
        assertTrue("tracker1".equals(dep1) || "tracker1".equals(dep2));
        assertTrue("tracker5".equals(dep1) || "tracker5".equals(dep2));
    }

    private void verifyTracker5Spec(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker6", spec.getDependencies().get(0).getServiceSpecName());
    }

    private void verifyTracker6Spec(final ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        final String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        final String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertFalse(dep1.equals(dep2));
        assertTrue("tracker7".equals(dep1) || "tracker7".equals(dep2));
        assertTrue("tracker9".equals(dep1) || "tracker9".equals(dep2));
    }

    private void verifyTracker7Spec(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker8", spec.getDependencies().get(0).getServiceSpecName());
    }

    private void verifyTracker9Spec(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker8", spec.getDependencies().get(0).getServiceSpecName());
    }

    private void verifyTracker8Spec(final ServiceSpec spec) {
        assertNull(spec.getDependencies());
    }

    @Test
    public void shouldCreateSpecWithSize15() {
        final ChorSpecCreator creator = new ChorSpecCreator();
        final ChoreographySpec chorSpec = creator.create(15);
        verifyTracker0SpecForSize15(chorSpec.getServiceSpecByName("tracker0"));
        verifyTracker5SpecForSize15(chorSpec.getServiceSpecByName("tracker5"));
        verifyTracker10SpecForSize15(chorSpec.getServiceSpecByName("tracker10"));
    }

    private void verifyTracker0SpecForSize15(final ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        final String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        final String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertFalse(dep1.equals(dep2));
        assertTrue("tracker1".equals(dep1) || "tracker1".equals(dep2));
        assertTrue("tracker5".equals(dep1) || "tracker5".equals(dep2));
    }

    private void verifyTracker5SpecForSize15(final ServiceSpec spec) {
        assertEquals(2, spec.getDependencies().size());
        final String dep1 = spec.getDependencies().get(0).getServiceSpecName();
        final String dep2 = spec.getDependencies().get(1).getServiceSpecName();
        assertFalse(dep1.equals(dep2));
        assertTrue("tracker6".equals(dep1) || "tracker6".equals(dep2));
        assertTrue("tracker10".equals(dep1) || "tracker10".equals(dep2));
    }

    private void verifyTracker10SpecForSize15(final ServiceSpec spec) {
        assertEquals(1, spec.getDependencies().size());
        assertEquals("tracker11", spec.getDependencies().get(0).getServiceSpecName());

    }

}
