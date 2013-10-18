package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class DelayedRequestsEnforcerTest {

    private static final int WAIT_TIME = 100;

    private DelayedRequestEnforcer enforcer = new DelayedRequestEnforcer(WAIT_TIME);
    private AtomicInteger counter = new AtomicInteger();

    @Test
    public void shouldEnforceRule() {

        final int N = 30;
        for (int i = 0; i < N; i++) {
            Task task = new Task();
            Thread trd = new Thread(task);
            trd.start();
        }

        int previous = 0;
        int value = counter.get();
        while (value < N - 1) { // FIXME: if enforcer was a short circuit, test
                                // would pass because this condition.
            if (value > previous + 2)
                fail();
            waitTimeBetweenRequests();
            if (value > previous)
                previous = value;
            value = counter.get();
        }
    }

    private void waitTimeBetweenRequests() {
        try {
            Thread.sleep(WAIT_TIME);
        } catch (InterruptedException e) {
            ;
        }
    }

    private class Task implements Runnable {
        @Override
        public void run() {
            enforcer.enforceRule();
            counter.getAndIncrement();
        }
    }

}
