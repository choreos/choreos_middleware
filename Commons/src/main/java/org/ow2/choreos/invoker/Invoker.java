package org.ow2.choreos.invoker;

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
 * @author leonardo
 * 
 */
public class Invoker<T> {

    private static Logger logger = Logger.getLogger(Invoker.class);

    private final String taskName;
    private final Callable<T> task;
    private final int trials;
    private final int trialTimeout;
    private final int pauseBetweenTrials;
    private final TimeUnit timeUnit;
    private final InvokerHistory history = InvokerHistory.getInstance();

    Invoker(String taskName, Callable<T> task, int trials, int trialTimeout, int pauseBetweenTrials, TimeUnit timeUnit) {
        this.taskName = taskName;
        this.task = task;
        this.trials = trials;
        this.trialTimeout = trialTimeout;
        this.pauseBetweenTrials = pauseBetweenTrials;
        this.timeUnit = timeUnit;
    }

    public T invoke() throws InvokerException {
        Throwable cause = null;
        for (int tried = 0; tried < trials; tried++) {
            try {
                T result = tryToInvoke();
                return result;
            } catch (Exception e) {
                cause = e;
                pause();
            }
        }
        throw new InvokerException(cause);
    }

    private T tryToInvoke() throws Exception {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<T> future = executor.submit(task);
        String errorMessage = "Invoker could not properly invoke " + taskName;
        long t0 = System.nanoTime();
        Concurrency.waitExecutor(executor, trialTimeout, timeUnit, logger, errorMessage);
        long tf = System.nanoTime();
        logHistory(t0, tf);
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

    private void logHistory(long t0, long tf) {
        long delta = (tf - t0) / 1000000000;
        history.addTime(taskName, delta);
    }

    private void pause() throws InvokerException {
        try {
            timeUnit.sleep(pauseBetweenTrials);
        } catch (InterruptedException e) {
            throw new InvokerException(e);
        }
    }

}
