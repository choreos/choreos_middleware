/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.ow2.choreos.utils.Concurrency;

/**
 * This selector will always return a new object.
 * 
 * @author leonardo
 * 
 * @param <T>
 * @param <R>
 */
public class AlwaysCreateSelector<T, R> implements Selector<T, R> {

    private ObjectFactory<T, R> objectFactory;

    private Logger logger = Logger.getLogger(AlwaysCreateSelector.class);

    public AlwaysCreateSelector(ObjectFactory<T, R> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

        int nThreads = objectsQuantity;
        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        List<Future<T>> futures = new ArrayList<Future<T>>();
        for (int i = 0; i < objectsQuantity; i++) {
            CreatorTask<T> task = new CreatorTask<T>(requirements);
            Future<T> f = executor.submit(task);
            futures.add(f);
        }

        int timeout = objectFactory.getTimeoutInSeconds();
        Concurrency.waitExecutor(executor, timeout, TimeUnit.SECONDS, logger, "AlwaysCreateSelector could not properly create object.");

        List<T> selectedObjects = new ArrayList<T>();
        for (Future<T> f : futures) {
            T obj;
            try {
                obj = f.get();
                selectedObjects.add(obj);
            } catch (InterruptedException e) {
                logger.error("Object not created");
            } catch (ExecutionException e) {
                logger.error("Object not created");
            }
        }

        if (selectedObjects.isEmpty())
            throw new NotSelectedException();

        return selectedObjects;
    }

    private class CreatorTask<H> implements Callable<H> {

        R requirements;

        public CreatorTask(R requirements) {
            this.requirements = requirements;
        }

        @SuppressWarnings("unchecked")
        @Override
        public H call() throws Exception {
            return (H) objectFactory.createNewInstance(requirements);
        }
    }

}
