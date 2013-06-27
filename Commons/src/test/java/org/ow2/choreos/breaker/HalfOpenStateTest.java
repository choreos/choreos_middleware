package org.ow2.choreos.breaker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

import org.junit.Test;

public class HalfOpenStateTest {

    private static final int TIMEOUT_SECONDS = 1;

    @Test
    public void shouldPassThroughTheBreaker() throws Exception {

        final int THRESHOLD = 1;
        Callable<String> goodTask = Tasks.goodTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(goodTask, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> halfOpenState = breaker.getHalfOpenState();
        breaker.setState(halfOpenState);

        String response = halfOpenState.call();

        assertEquals(Tasks.RESPONSE, response);
        CircuitBreakerState<String> closedState = breaker.getClosedState();
        assertEquals(closedState, breaker.getState());
    }

    @Test(expected = BreakerException.class)
    public void shouldTripTheBreaker() throws BreakerException {

        final int THRESHOLD = 1;
        Callable<String> badTask = Tasks.badTask();
        CircuitBreaker<String> breaker = new CircuitBreaker<String>(badTask, THRESHOLD, TIMEOUT_SECONDS);
        CircuitBreakerState<String> halfOpenState = breaker.getHalfOpenState();
        breaker.setState(halfOpenState);

        try {
            halfOpenState.call();
            fail();
        } catch (BreakerException e) {
            CircuitBreakerState<String> openState = breaker.getOpenState();
            assertEquals(openState, breaker.getState());
            throw e;
        }
    }
}
