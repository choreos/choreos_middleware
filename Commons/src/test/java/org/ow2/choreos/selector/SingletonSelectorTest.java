package org.ow2.choreos.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.SingletonSelector;
import org.ow2.choreos.utils.Concurrency;

public class SingletonSelectorTest {

    private SingletonSelector<String, String> selector;
    private StringFactory factory = new StringFactory();
    private String requirements = "requirements";
    
    private Logger logger = Logger.getLogger(SingletonSelectorTest.class);
    
    @Test
    public void shouldAlwaysReturnTheSameObject() throws NotSelectedException  {
	selector = new SingletonSelector<String, String>(factory);
	String object1 = selector.select(requirements, 1).get(0);
	String object2 = selector.select(requirements, 1).get(0);
	assertTrue(object1 == object2);
    }
    
    @Test
    public void shouldConcurrentlyAlwaysReturnTheSameObject() throws InterruptedException, ExecutionException {

	final int THREADS_NUM = 3;
	ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);
	selector = new SingletonSelector<String, String>(factory);

	List<Future<String>> futures = new ArrayList<Future<String>>();
	for (int i = 0; i < THREADS_NUM; i++) {
	    SelectorTask task = new SelectorTask();
	    Future<String> f = executor.submit(task);
	    futures.add(f);
	}
	Concurrency.waitExecutor(executor, factory.getTimeouInSeconds(), TimeUnit.SECONDS, logger);

	String previousObject = futures.get(0).get();
	for (Future<String> f : futures) {
	    String object = f.get();
	    assertEquals(previousObject, object);
	    previousObject = object;
	}
    }
    
    private class SelectorTask implements Callable<String> {
	@Override
	public String call() throws Exception {
	    return selector.select(requirements, 1).get(0);
	}
    }
}
