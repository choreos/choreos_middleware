package org.ow2.choreos.breaker;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.Concurrency;

/**
 * You should use this this class to invoke external systems.
 * 
 * TODO: use a received circuit breaker
 * 
 * @author leonardo
 * 
 */
public class Invoker<T> {

    private Callable<T> task;
    private int trials;
    private int trialTimeout;
    private TimeUnit timeoutUnit;

    private Logger logger = Logger.getLogger(Invoker.class);

    public Invoker(Callable<T> task, int trials, int trialTimeout, TimeUnit timeoutUnit) {
	this.task = task;
	this.trials = trials;
	this.trialTimeout = trialTimeout;
	this.timeoutUnit = timeoutUnit;
    }

    public T invoke() throws InvokerException {
	Throwable cause = null;
	for (int tried = 0; tried < trials; tried++) {
	    try {
		T result = tryToInvoke();
		return result;
	    } catch (Exception e) {
		cause = e;
	    }
	}
	throw new InvokerException(cause);
    }

    private T tryToInvoke() throws Exception {
	ExecutorService executor = Executors.newSingleThreadExecutor();
	Future<T> future = executor.submit(task);
	Concurrency.waitExecutor(executor, trialTimeout, timeoutUnit, logger);
	try {
	    return Concurrency.checkAndGetFromFuture(future);
	} catch (ExecutionException e) {
	    Throwable cause = e.getCause();
	    if (cause instanceof Exception)
		throw (Exception) e;
	    else
		throw e;
	}
    }

}
