package org.ow2.choreos.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class BurstDetectorTest {

    @Test
    public void shouldDetectBurst() {

	BurstDetector burstDetector = new BurstDetector(3, 100);
	assertFalse(burstDetector.invoke());
	assertFalse(burstDetector.invoke());
	assertTrue(burstDetector.invoke());
	assertFalse(burstDetector.invoke());
	assertFalse(burstDetector.invoke());
	assertTrue(burstDetector.invoke());
	assertFalse(burstDetector.invoke());
    }

    @Test
    public void shouldNotDetectBurst() throws InterruptedException {

	BurstDetector burstDetector = new BurstDetector(3, 10);
	assertFalse(burstDetector.invoke());
	Thread.sleep(20);
	assertFalse(burstDetector.invoke());
	Thread.sleep(20);
	assertFalse(burstDetector.invoke());
	Thread.sleep(20);
	assertFalse(burstDetector.invoke());
	Thread.sleep(20);
	assertFalse(burstDetector.invoke());
	Thread.sleep(20);
	assertFalse(burstDetector.invoke());
	Thread.sleep(20);
	assertFalse(burstDetector.invoke());
    }
}
