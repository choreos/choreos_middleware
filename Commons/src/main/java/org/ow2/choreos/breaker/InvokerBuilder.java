package org.ow2.choreos.breaker;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * Builds an invoker.
 * 
 * Mandatory arguments: task and trialTimeout.
 * Defaults:
 *   trials = 1
 *   pauseBetweenTrials = 0
 *   timeUnit = TimeUnit.SECONDS
 * 
 * @author leonardo
 *
 * @param <T>
 */
public class InvokerBuilder<T> {

    private final Callable<T> task;
    private final int trialTimeout;
    private int trials = 1;
    private int pauseBetweenTrials = 0;
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public InvokerBuilder(Callable<T> task, int trialTimeout) {
        this.task = task;
        this.trialTimeout = trialTimeout;
    }
    
    public InvokerBuilder<T> trials(int trials) {
        this.trials = trials;
        return this;
    }
    
    public InvokerBuilder<T> pauseBetweenTrials(int pauseBetweenTrials) {
        this.pauseBetweenTrials = pauseBetweenTrials;
        return this;
    }

    public InvokerBuilder<T> timeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
        return this;
    }
    
    public Invoker<T> build() {
        return new Invoker<T>(task, trials, trialTimeout, pauseBetweenTrials, timeUnit);
    }
    
}
