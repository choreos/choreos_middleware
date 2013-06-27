package org.ow2.choreos.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;

public class SingletonsFactoryTest {

    @Test
    public void shouldInstantiateAnObjectFromAGivenClass() {

        final String type = "STRING";
        Configuration conf = new Configuration("No file");
        conf.set(type, "java.lang.String");

        SingletonsFactory<String> factory = new SingletonsFactory<String>(conf);
        String singleton = factory.getInstance(type);
        assertEquals(String.class, singleton.getClass());
    }

    @Test
    public void shouldAlwaysReturnTheSameObjectToTheSameType() {

        final String type = "STRING";
        Configuration conf = new Configuration("No file");
        conf.set(type, "java.lang.String");

        SingletonsFactory<String> factory = new SingletonsFactory<String>(conf);
        String singleton1 = factory.getInstance(type);
        String singleton2 = factory.getInstance(type);
        assertTrue(singleton1 == singleton2);
    }

    @Test
    public void shouldAlwaysReturnDifferentObjectsToDifferentTypes() {

        final String type1 = "STRING1";
        final String type2 = "STRING2";
        Configuration conf = new Configuration("No file");
        conf.set(type1, "java.lang.String");
        conf.set(type2, "java.lang.String");

        SingletonsFactory<String> factory = new SingletonsFactory<String>(conf);
        String singleton1 = factory.getInstance(type1);
        String singleton2 = factory.getInstance(type2);
        assertTrue(singleton1 != singleton2);
    }

    @Test
    public void shouldConcurrentlyAlwaysReturnTheSameObject() throws InterruptedException, ExecutionException {

        final String type = "STRING";
        Configuration conf = new Configuration("No file");
        conf.set(type, "java.lang.String");
        SingletonsFactory<String> factory = new SingletonsFactory<String>(conf);

        final int THREADS_NUM = 3;
        ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);

        List<Future<String>> futures = new ArrayList<Future<String>>();
        for (int i = 0; i < THREADS_NUM; i++) {
            FactoryTask task = new FactoryTask(factory, type);
            Future<String> f = executor.submit(task);
            futures.add(f);
        }
        Concurrency.waitExecutor(executor, 1);

        String previousObject = futures.get(0).get();
        for (Future<String> f : futures) {
            String object = f.get();
            assertEquals(previousObject, object);
            previousObject = object;
        }
    }

    private class FactoryTask implements Callable<String> {

        SingletonsFactory<String> factory;
        String type;

        FactoryTask(SingletonsFactory<String> factory, String type) {
            this.factory = factory;
            this.type = type;
        }

        @Override
        public String call() throws Exception {
            return factory.getInstance(type);
        }

    }

}
