package org.ow2.choreos.tracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.chors.datamodel.Choreography;
import org.ow2.choreos.chors.datamodel.ChoreographySpec;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

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
        chorProps.setChoreography(null);
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
        chorProps.setChoreography(createChorWithNServices(5));
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3"));
    }

    @Test
    public void test5TrackerWrongAnswer() {
        chorProps.setChoreography(createChorWithNServices(5));
        assertFalse(chorProps.isAnswerCorrect("0 1 2 3 4 5"));
    }

    @Test
    public void test10TrackerAnswer() {
        chorProps.setChoreography(createChorWithNServices(10));
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8"));
    }

    @Test
    public void test10TrackerWrongAnswer() {
        chorProps.setChoreography(createChorWithNServices(10));
        assertFalse(chorProps.isAnswerCorrect("0 1 2 3 4 3"));
    }
    
    @Test
    public void test100TrackerAnswer() {
        chorProps.setChoreography(createChorWithNServices(100));
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8 10 11 12 13 14 13 15 16 17 18 19 18 20 21 22 23 24 23 25 26 27 28 29 28 30 31 32 33 34 33 35 36 37 38 39 38 40 41 42 43 44 43 45 46 47 48 49 48 50 51 52 53 54 53 55 56 57 58 59 58 60 61 62 63 64 63 65 66 67 68 69 68 70 71 72 73 74 73 75 76 77 78 79 78 80 81 82 83 84 83 85 86 87 88 89 88 90 91 92 93 94 93 95 96 97 98 99 98"));
    }
    
    @Test
    public void test15TrackerAnswer() {
        chorProps.setChoreography(createChorWithNServices(15));
        assertTrue(chorProps.isAnswerCorrect("0 1 2 3 4 3 5 6 7 8 9 8 10 11 12 13 14 13"));
    }

    private Choreography createChorWithNServices(final int N) {
        final Choreography chor = new Choreography();
        chor.setChoreographySpec(new ChoreographySpec());
        DeployableServiceSpec servSpec;
        DeployableService serv;

        for (int i = 0; i < N; i++) {
            servSpec = new DeployableServiceSpec();
            chor.getChoreographySpec().addServiceSpec(servSpec);
            serv = new DeployableService();
            chor.addService(serv);
        }

        return chor;
    }

}
