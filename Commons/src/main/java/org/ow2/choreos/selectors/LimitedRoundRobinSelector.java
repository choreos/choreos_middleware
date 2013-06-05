/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class LimitedRoundRobinSelector<T, R> implements Selector<T, R> {

    private int limit = 1;
    private ObjectRetriever<T> objectRetriever;
    private AtomicInteger objectsBeenCreated = new AtomicInteger();

    private Selector<T, R> alwaysCreatorSelector, roundRobinSelector;

    private enum State {
	CREATING, ROUND_ROBIN
    };

    public LimitedRoundRobinSelector(int limit, ObjectRetriever<T> objectRetriever, ObjectFactory<T> objectFactory) {
	ObjectFilters<T, R> filters = new ObjectFilters<T, R>();
	ObjectFilter<T, R> filter = filters.getNoFilter();
	this.limit = limit;
	this.alwaysCreatorSelector = new AlwaysCreateSelector<T, R>(objectFactory);
	this.roundRobinSelector = new RoundRobinSelector<T, R>(objectRetriever, filter);
	this.objectRetriever = objectRetriever;
    }

    public LimitedRoundRobinSelector(int limit, ObjectRetriever<T> objectRetriever, ObjectFactory<T> objectFactory,
	    ObjectFilter<T, R> objectFilter) {
	this.limit = limit;
	this.alwaysCreatorSelector = new AlwaysCreateSelector<T, R>(objectFactory);
	this.roundRobinSelector = new RoundRobinSelector<T, R>(objectRetriever);
	this.objectRetriever = objectRetriever;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

	List<T> objects = this.objectRetriever.retrieveObjects();
	int newQty = 0;
	State state = State.ROUND_ROBIN;
	synchronized (this) {
	    state = this.defineState(objects);
	    if (state == State.CREATING) {
		newQty = this.defineNewQty(objects, objectsQuantity);
		objectsBeenCreated.addAndGet(newQty);
	    }
	}

	switch (state) {

	case CREATING:
	    return selectInCreatingState(objects, requirements, objectsQuantity, newQty);

	case ROUND_ROBIN:
	    return selectInRoundRobinState(objects, requirements, objectsQuantity);

	default:
	    throw new IllegalStateException("Invalid state " + state);
	}
    }

    private State defineState(List<T> objects) {

	if (objects.size() + objectsBeenCreated.get() < limit)
	    return State.CREATING;
	else
	    return State.ROUND_ROBIN;
    }

    private int defineNewQty(List<T> objects, int objectsQuantity) {

	int maximumNewAllowed = limit - objects.size();
	int newQty = objectsQuantity;
	if (newQty > maximumNewAllowed) {
	    newQty = maximumNewAllowed;
	}
	return newQty;
    }

    private List<T> selectInCreatingState(List<T> objects, R requirements, int objectsQuantity, int newQty)
	    throws NotSelectedException {

	List<T> result = this.alwaysCreatorSelector.select(requirements, newQty);
	synchronized (this) {
	    objectsBeenCreated.set(objectsBeenCreated.get() - newQty);
	}

	if (result.size() < objectsQuantity) {
	    int diff = objectsQuantity = result.size();
	    List<T> moreNodes = this.roundRobinSelector.select(requirements, diff);
	    result.addAll(moreNodes);
	}
	return result;
    }

    private List<T> selectInRoundRobinState(List<T> objects, R requirements, int objectsQuantity)
	    throws NotSelectedException {

	this.waitNodesCreation();
	return this.roundRobinSelector.select(requirements, objectsQuantity);
    }

    private void waitNodesCreation() {

	int c = 0;
	while (objectsBeenCreated.get() > 0) {
	    try {
		if (c < 10)
		    Thread.sleep(100);
		else
		    Thread.sleep(1000);
	    } catch (InterruptedException e) {
	    }
	    c++;
	}
    }
}
