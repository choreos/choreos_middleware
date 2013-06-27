package org.ow2.choreos.breaker;

public class ClosedState<T> extends BaseState<T> {

    private static final String STATE_NAME = "CLOSED";

    private CircuitBreaker<T> breaker;
    private int fails = 0;

    public ClosedState(CircuitBreaker<T> breaker) {
        super(STATE_NAME);
        this.breaker = breaker;
    }

    @Override
    public void activate() {
        resetCount();
    }

    private void resetCount() {
        fails = 0;
    }

    @Override
    public T call() throws BreakerException {
        try {
            T result = breaker.passThrough();
            resetCount();
            return result;
        } catch (Exception e) {
            countFailure();
            if (isThresholdReached())
                breaker.trip();
            throw new BreakerException();
        }
    }

    private void countFailure() {
        fails++;
    }

    private boolean isThresholdReached() {
        return fails >= breaker.getThreshold();
    }

}
