package org.ow2.choreos.deployment.nodes.cloudprovider;

import static org.junit.Assert.fail;

import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;

public class OneRequestPerSecondEnforcerTest {
    
    private OneRequestPerSecondEnforcer enforcer = new OneRequestPerSecondEnforcer();
    private AtomicInteger counter = new AtomicInteger();

    @Test
    public void shouldEnforceRule() {
	
	final int N = 4;
	for (int i=0; i<N; i++) {
	    Task task = new Task();
	    Thread trd = new Thread(task);
	    trd.start();
	}
	
	int previous = 0;
	int value = counter.get();
	while (value < N) {
	    if (value > previous + 2)
		fail();
	    waitOneSecond();
	    if (value > previous)
		previous = value;
	    value = counter.get();
	}
    }
    
    private void waitOneSecond() {
	try {
	    Thread.sleep(1000);
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
