package org.ow2.choreos.breaker;

import static org.junit.Assert.*;

import java.util.concurrent.Callable;

import org.junit.Test;

public class OpenStateTest {

    @Test(expected = BreakerException.class)
    public void shouldNotPassTheBreakerEvenWithGoodTask() throws Exception {
        final int THRESHOLD = 1;
        final int TIMEOUT_SECONDS = 1;
        Callable<String> task = Tasks.goodTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(task, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> openState = breaker.getOpenState();
        breaker.setState(openState);
        try {
            openState.call();
        } catch (BreakerException e) {
            assertEquals(openState, breaker.getState());
            throw e;
        }
    }

    @Test
    public void shouldPassToHalfStateAfterTimeout() throws Exception {

        final int THRESHOLD = 1;
        final int TIMEOUT_SECONDS = 2;
        Callable<String> task = Tasks.goodTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(task, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> openState = breaker.getOpenState();
        breaker.setState(openState);

        Thread.sleep(TIMEOUT_SECONDS / 2 * 1000);
        try {
            openState.call();
            fail();
        } catch (BreakerException e) {
            assertEquals(openState, breaker.getState());
        }

        Thread.sleep(TIMEOUT_SECONDS / 2 * 1000 + 10);
        try {
            openState.call();
            fail();
        } catch (BreakerException e) {
            CircuitBreakerState<String> halfOpenState = breaker.getHalfOpenState();
            assertEquals(halfOpenState, breaker.getState());
        }
    }
}
