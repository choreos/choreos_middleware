package org.ow2.choreos.breaker;

import java.util.concurrent.Callable;

/**
 * Invocations to external systems should be done through the CircuitBreaker[1].
 * 
 * This helps in protecting the invoked system if multiple clients try to
 * perform several invocations that are failing.
 * 
 * [1] Michael T. Nygard. "Release it!". Pragmatic Bookshelf, 2009.
 * 
 * @author leonardo
 * 
 * @param <T>
 */
public class CircuitBreaker<T> {

    private Callable<T> task;
    private int threshold;
    private int timeoutSeconds;

    private CircuitBreakerState<T> closedState;
    private CircuitBreakerState<T> openState;
    private CircuitBreakerState<T> halfOpenState;
    private CircuitBreakerState<T> currentState;

    /**
     * 
     * @param task
     * @param threshold
     *            how many failed trials to open the breaker
     * @param timeoutSeconds
     *            how many time to remain opened
     */
    public CircuitBreaker(Callable<T> task, int threshold, int timeoutSeconds) {
        this.task = task;
        this.threshold = threshold;
        this.timeoutSeconds = timeoutSeconds;
        this.closedState = new ClosedState<T>(this);
        this.openState = new OpenState<T>(this);
        this.halfOpenState = new HalfOpenState<T>(this);
        this.currentState = closedState;
    }

    public T call() throws BreakerException {
        try {
            return this.currentState.call();
        } catch (Exception e) {
            throw new BreakerException(e);
        }
    }

    public int getThreshold() {
        return this.threshold;
    }

    public int getTimeoutSeconds() {
        return this.timeoutSeconds;
    }

    T passThrough() throws Exception {
        return this.task.call();
    }

    void trip() {
        setState(openState);
    }

    void attemptReset() {
        setState(halfOpenState);
    }

    void reset() {
        setState(closedState);
    }

    void setState(CircuitBreakerState<T> state) {
        this.currentState = state;
        this.currentState.activate();
    }

    CircuitBreakerState<T> getState() {
        return this.currentState;
    }

    CircuitBreakerState<T> getClosedState() {
        return closedState;
    }

    CircuitBreakerState<T> getOpenState() {
        return openState;
    }

    CircuitBreakerState<T> getHalfOpenState() {
        return halfOpenState;
    }

}
