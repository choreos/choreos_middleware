package org.ow2.choreos.breaker;

public interface CircuitBreakerState<T> {

    public void activate();

    public T call() throws BreakerException;

}
