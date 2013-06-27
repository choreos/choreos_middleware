package org.ow2.choreos.breaker;

public class HalfOpenState<T> extends BaseState<T> {

    private static final String STATE_NAME = "HALF_OPEN";

    private CircuitBreaker<T> breaker;

    public HalfOpenState(CircuitBreaker<T> breaker) {
        super(STATE_NAME);
        this.breaker = breaker;
    }

    @Override
    public void activate() {

    }

    @Override
    public T call() throws BreakerException {
        try {
            T result = breaker.passThrough(); // TODO set timeout
            breaker.reset();
            return result;
        } catch (Exception e) {
            breaker.trip();
            throw new BreakerException(e);
        }
    }

}
