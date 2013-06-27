package org.ow2.choreos.breaker;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;

import org.junit.Test;

public class ClosedStateTest {

    private static final int TIMEOUT_SECONDS = 1;

    @Test
    public void shouldPassThroughTheBreaker() throws Exception {

        final int THRESHOLD = 1;
        Callable<String> goodTask = Tasks.goodTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(goodTask, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> closedState = breaker.getClosedState();

        String response = closedState.call();

        assertEquals(Tasks.RESPONSE, response);
        assertEquals(closedState, breaker.getState());
    }

    @Test(expected = Exception.class)
    public void shouldNotPassTheBreaker() throws Exception {
        final int THRESHOLD = 2;
        Callable<String> task = Tasks.badTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(task, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> closedState = breaker.getClosedState();
        closedState.call();
    }

    @Test
    public void shouldOpenBreaker() {

        final int THRESHOLD = 2;
        Callable<String> task = Tasks.badTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(task, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> closedState = breaker.getClosedState();

        int failsCount = 0;
        for (int i = 0; i < THRESHOLD; i++) {
            try {
                closedState.call();
            } catch (Exception e) {
                failsCount++;
            }
        }

        assertEquals(THRESHOLD, failsCount);
        CircuitBreakerState<String> openState = breaker.getOpenState();
        assertEquals(openState, breaker.getState());
    }

    @Test
    public void shouldNotOpenBreaker() {

        final int THRESHOLD = 3;
        Callable<String> task = Tasks.badTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(task, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> closedState = breaker.getClosedState();

        int failsCount = 0;
        for (int i = 0; i < THRESHOLD - 1; i++) {
            try {
                closedState.call();
            } catch (Exception e) {
                failsCount++;
            }
        }

        assertEquals(THRESHOLD - 1, failsCount);
        assertEquals(closedState, breaker.getState());
    }

}
