package org.ow2.choreos.invoker;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class InvokerHistoryTest {

    @Before
    public void setUp() {
        InvokerHistory.getInstance().clear();
    }
    
    @Test
    public void shouldRecordInHistory() throws InvokerException {
        String taskName = "TakesTwoSecondsTask";
        int timeout = 3;
        int trials = 1;
        int pause = 0;
        TakesTwoSecondsTask task = new TakesTwoSecondsTask();
        Invoker<String> invoker = new Invoker<String>(taskName, task, trials, timeout, pause, TimeUnit.SECONDS);
        invoker.invoke();
        List<Long> history = InvokerHistory.getInstance().getHistory(taskName);
        assertEquals(1, history.size());
        assertTrue(history.get(0) >= 2);
    }
    
    @Test
    public void shouldNotRecordInHistoryIfFailed() {
        String taskName = "TakesOneSecondsAndFailsTask";
        int timeout = 3;
        int trials = 1;
        int pause = 0;
        TakesOneSecondsAndFailsTask task = new TakesOneSecondsAndFailsTask();
        Invoker<String> invoker = new Invoker<String>(taskName, task, trials, timeout, pause, TimeUnit.SECONDS);
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            // expected
        }
        List<Long> history = InvokerHistory.getInstance().getHistory(taskName);
        assertTrue(history.isEmpty());
    }
    
    //@Test ???
    public void shouldRecordInHistoryTimeoutedTaskIfSuccessfull() throws InterruptedException {
        String taskName = "TakesTwoSecondsTask";
        int timeout = 1;
        int trials = 1;
        int pause = 0;
        TakesTwoSecondsTask task = new TakesTwoSecondsTask();
        Invoker<String> invoker = new Invoker<String>(taskName, task, trials, timeout, pause, TimeUnit.SECONDS);
        try {
            invoker.invoke();
        } catch (InvokerException e) {
            // expected because timeout
        }
        Thread.sleep(1500);
        List<Long> history = InvokerHistory.getInstance().getHistory(taskName);
        assertEquals(1, history.size());
        assertTrue(history.get(0) >= 2);
    }
    
    private class TakesTwoSecondsTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(2000);
            return "Hello world";
        }
    }
    
    private class TakesOneSecondsAndFailsTask implements Callable<String> {
        @Override
        public String call() throws Exception {
            Thread.sleep(1000);
            throw new Exception("Bye world!");
        }
    }
}
