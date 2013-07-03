package org.ow2.choreos.selectors;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Return always the same object.
 * 
 * @author leonardo
 * 
 * @param <T>
 * @param <R>
 */
public class SingletonSelector<T, R> implements Selector<T, R> {

    private T theObject;
    private ObjectFactory<T, R> factory;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    boolean creating = false;

    public SingletonSelector(ObjectFactory<T, R> factory) {
        this.factory = factory;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

        if (objectsQuantity != 1)
            throw new IllegalArgumentException("Current implementation supports only objectsQuantity=1");

        createObjectIfNecessary(requirements);

        if (objectIsBeenCreated())
            waitObjectCreation();

        if (objectIsNotCreated())
            throw new NotSelectedException();

        return Collections.singletonList(theObject);
    }

    private void createObjectIfNecessary(R requirements) {
        if (objectIsNotCreated() && !objectIsBeenCreated()) {
            synchronized (this) {
                if (objectIsNotCreated() && !objectIsBeenCreated()) {
                    createTheObject(requirements);
                }
            }
        }
    }

    private void createTheObject(R requirements) {
        creating = true;
        CreationTask task = new CreationTask(requirements);
        executor.submit(task);
        executor.shutdown();
    }

    private boolean objectIsNotCreated() {
        return theObject == null;
    }

    private boolean objectIsBeenCreated() {
        return creating;
    }

    private void waitObjectCreation() throws NotSelectedException {
        boolean ok = false;
        try {
            ok = executor.awaitTermination(factory.getTimeoutInSeconds(), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            throw new NotSelectedException();
        }
        if (!ok) {
            throw new NotSelectedException();
        }
    }

    private class CreationTask implements Runnable {

        R requirements;

        CreationTask(R requirements) {
            this.requirements = requirements;
        }

        @Override
        public void run() {
            try {
                theObject = factory.createNewInstance(requirements);
            } catch (ObjectCreationException e) {
                theObject = null;
            }
            creating = false;
        }
    }

}
