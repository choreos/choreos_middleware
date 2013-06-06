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
    private ObjectFactory<T> factory;
    private ExecutorService executor = Executors.newSingleThreadExecutor();
    boolean creating = false;

    public SingletonSelector(ObjectFactory<T> factory) {
	this.factory = factory;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

	if (objectsQuantity != 1)
	    throw new IllegalArgumentException("Current implementation supports only objectsQuantity=1");

	createObjectIfNecessary();

	if (objectIsBeenCreated())
	    waitObjectCreation();

	if (objectIsNotCreated())
	    throw new NotSelectedException();

	return Collections.singletonList(theObject);
    }

    private void createObjectIfNecessary() {
	if (objectIsNotCreated() && !objectIsBeenCreated()) {
	    synchronized (this) {
		if (objectIsNotCreated() && !objectIsBeenCreated()) {
		    createTheObject();
		}
	    }
	}
    }

    private void createTheObject() {
	creating = true;
	CreationTask task = new CreationTask();
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
	    ok = executor.awaitTermination(factory.getTimeouInSeconds(), TimeUnit.SECONDS);
	} catch (InterruptedException e) {
	    throw new NotSelectedException();
	}
	if (!ok) {
	    throw new NotSelectedException();
	}
    }

    private class CreationTask implements Runnable {
	@Override
	public void run() {
	    try {
		theObject = factory.createNewInstance();
	    } catch (ObjectCreationException e) {
		theObject = null;
	    }
	    creating = false;
	}
    }

}
