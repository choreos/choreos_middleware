package org.ow2.choreos.tracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;

public class TrackerInfoTest {

    private transient TrackerInfo trackerInfo;

    @Before
    public void setUp() {
        trackerInfo = new TrackerInfo();
    }

    @Test
    public void testTrackerName() {
        assertEquals("tracker42", trackerInfo.getName(42));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfgettingWsdlWithoutChorSet() {
        trackerInfo.getWsdl(0);
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNoServiceIsFound() {
        trackerInfo.setChoreography(new Choreography());
        trackerInfo.getWsdl(0);
    }

    @Test
    public void test5TrackerAnswer() {
        assertTrue(trackerInfo.isAnswerCorrect("0 1 2 3 4 3"));
    }

    @Test
    public void test5TrackerWrongAnswer() {
        assertFalse(trackerInfo.isAnswerCorrect("0 1 2 3 4 5"));
    }

    @Test
    public void test10TrackerAnswer() {
        assertTrue(trackerInfo.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8"));
    }

    @Test
    public void test15TrackerAnswer() {
        assertTrue(trackerInfo.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8 10 11 12 13 14 13"));
    }

}
