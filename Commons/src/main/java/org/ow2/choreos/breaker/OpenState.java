package org.ow2.choreos.breaker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OpenState<T> extends BaseState<T> {

    private static final String STATE_NAME = "OPEN";

    private CircuitBreaker<T> breaker;

    public OpenState(CircuitBreaker<T> breaker) {
        super(STATE_NAME);
        this.breaker = breaker;
    }

    @Override
    public void activate() {
        triggerTimeout();
    }

    private void triggerTimeout() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        OpenStateTimer openStateTimer = new OpenStateTimer();
        executor.submit(openStateTimer);
    }

    private class OpenStateTimer implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(breaker.getTimeoutSeconds() * 1000);
            } catch (InterruptedException e) {
                ;
            }
            breaker.attemptReset();
        }
    }

    @Override
    public T call() throws BreakerException {
        return fail();
    }

    private T fail() throws BreakerException {
        throw new BreakerException();
    }

}
