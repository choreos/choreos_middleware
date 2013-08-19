package org.ow2.choreos.tracker.tests.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.ow2.choreos.tracker.TrackerProperties;
import org.ow2.choreos.tracker.TrackerType;

public class TrackerPropertiesTest {

    @Test
    public void verifyTypes() {
        TrackerProperties properties = new TrackerProperties();
        assertEquals(TrackerType.WHITE, properties.getType(0));
        assertEquals(TrackerType.WHITE, properties.getType(1));
        assertEquals(TrackerType.WHITE, properties.getType(2));
        assertEquals(TrackerType.WHITE, properties.getType(3));
        assertEquals(TrackerType.BLACK, properties.getType(4));
        assertEquals(TrackerType.WHITE, properties.getType(5));
        assertEquals(TrackerType.WHITE, properties.getType(6));
        assertEquals(TrackerType.WHITE, properties.getType(7));
        assertEquals(TrackerType.WHITE, properties.getType(8));
        assertEquals(TrackerType.BLACK, properties.getType(9));
    }

}
