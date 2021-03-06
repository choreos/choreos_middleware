package org.ow2.choreos.invoker;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Test;
import org.ow2.choreos.invoker.Invoker;
import org.ow2.choreos.invoker.InvokerException;

public class InvokerTest {

    private final int pauseBetweenTrials = 0;

    @Test
    public void shouldInvoke() throws InvokerException {

        Callable<String> task = new AlwaysWork();
        int trials = 1;
        int timeout = 1;

        Invoker<String> invoker = new Invoker<String>("AlwaysWork", task, trials, timeout, pauseBetweenTrials,
                TimeUnit.SECONDS);
        String result = invoker.invoke();

        assertEquals("0", result);
    }

    @Test
    public void invocationShouldWorkInTheSecondTrial() throws InvokerException {

        Callable<String> task = new WorksInTheSecond();
        int trials = 2;
        int timeout = 1;

        Invoker<String> invoker = new Invoker<String>("WorksInTheSecond", task, trials, timeout, pauseBetweenTrials,
                TimeUnit.SECONDS);
        String result = invoker.invoke();

        assertEquals("0", result);
    }

    @Test(expected = InvokerException.class)
    public void invocationShouldNotWork() throws InvokerException {

        Callable<String> task = new WorksInTheSecond();
        int trials = 1;
        int timeout = 1;

        Invoker<String> invoker = new Invoker<String>("WorksInTheSecond", task, trials, timeout, pauseBetweenTrials,
                TimeUnit.SECONDS);
        invoker.invoke();
    }

    @Test
    public void shouldInvokeAndNotTimeout() throws InvokerException {

        Callable<String> task = new TakesHundredMilliSecToWork();
        int trials = 1;
        int timeout = 500;

        Invoker<String> invoker = new Invoker<String>("TakesHundredMilliSecToWork", task, trials, timeout,
                pauseBetweenTrials, TimeUnit.MILLISECONDS);
        String result = invoker.invoke();

        assertEquals("0", result);
    }

    @Test(expected = InvokerException.class)
    public void shouldInvokeAndTimeout() throws InvokerException {

        Callable<String> task = new TakesHundredMilliSecToWork();
        int trials = 1;
        int timeout = 5;

        Invoker<String> invoker = new Invoker<String>("TakesHundredMilliSecToWork", task, trials, timeout,
                pauseBetweenTrials, TimeUnit.MILLISECONDS);
        invoker.invoke();
    }

    @Test
    public void shouldExecuteVoidTask() throws InvokerException {

        VoidTask task = new VoidTask();
        int trials = 1;
        int timeout = 100;

        Invoker<Void> invoker = new Invoker<Void>("VoidTask", task, trials, timeout, pauseBetweenTrials,
                TimeUnit.MILLISECONDS);
        invoker.invoke();

        boolean done = task.done;
        assertEquals(true, done);
    }

    private class AlwaysWork implements Callable<String> {

        AtomicInteger counter = new AtomicInteger();

        @Override
        public String call() throws Exception {
            return Integer.toString(counter.getAndIncrement());
        }

    }

    private class WorksInTheSecond implements Callable<String> {

        boolean first = true;
        AtomicInteger counter = new AtomicInteger();

        @Override
        public String call() throws Exception {
            if (first) {
                first = false;
                throw new Exception();
            } else {
                return Integer.toString(counter.getAndIncrement());
            }
        }
    }

    private class TakesHundredMilliSecToWork implements Callable<String> {

        AtomicInteger counter = new AtomicInteger();

        @Override
        public String call() throws Exception {
            Thread.sleep(100);
            return Integer.toString(counter.getAndIncrement());
        }

    }

    private class VoidTask implements Callable<Void> {

        boolean done = false;

        @Override
        public Void call() throws Exception {
            Thread.sleep(10);
            done = true;
            return null;
        }

    }
}
