package org.ow2.choreos.tracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;

public class PropertiesTest {

    private transient TrackerProperties trackerProps;
    private transient ChorProperties chorProps;

    @Before
    public void setUp() {
        trackerProps = new TrackerProperties();
        chorProps = new ChorProperties();
    }

    @Test
    public void testTrackerName() {
        assertEquals("tracker42", trackerProps.getName(42));
    }

    @Test(expected = IllegalStateException.class)
    public void shouldThrowIllegalStateExceptionIfgettingWsdlWithoutChorSet() {
        chorProps.getWsdl(0);
        fail();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldThrowNoSuchElementExceptionIfNoServiceIsFound() {
        chorProps.setChoreography(new Choreography());
        chorProps.getWsdl(0);
    }

    @Test
    public void test5TrackerAnswer() {
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3"));
    }

    @Test
    public void test5TrackerWrongAnswer() {
        assertFalse(chorProps.isAnswerCorrect("0 1 2 3 4 5"));
    }

    @Test
    public void test10TrackerAnswer() {
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8"));
    }

    @Test
    public void test15TrackerAnswer() {
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8 10 11 12 13 14 13"));
    }

}
