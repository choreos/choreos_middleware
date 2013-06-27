/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This selector will always use existing objects, it will never create a new
 * object. The existing objects are selected in a round robin order.
 * 
 * @author leonardo
 * 
 * @param <T>
 * @param <R>
 */
public class RoundRobinSelector<T, R> implements Selector<T, R> {

    private ObjectRetriever<T> objectRetriever;
    private ObjectFilter<T, R> objectFilter;
    private AtomicInteger counter = new AtomicInteger();

    public RoundRobinSelector(ObjectRetriever<T> objectRetriever) {
        ObjectFilters<T, R> filters = new ObjectFilters<T, R>();
        this.objectFilter = filters.getNoFilter();
        this.objectRetriever = objectRetriever;
    }

    public RoundRobinSelector(ObjectRetriever<T> objectRetriever, ObjectFilter<T, R> objectFilter) {
        this.objectRetriever = objectRetriever;
        this.objectFilter = objectFilter;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

        List<T> objects = this.objectRetriever.retrieveObjects();
        List<T> compatibleObjects = this.objectFilter.filter(objects, requirements);

        if (compatibleObjects.size() < objectsQuantity) {
            throw new NotSelectedException();
        }

        List<T> selectedObjects = new ArrayList<T>();

        for (int i = 0; i < objectsQuantity; i++) {
            int idx = counter.getAndIncrement();
            idx = idx % compatibleObjects.size();
            T selected = compatibleObjects.get(idx);
            selectedObjects.add(selected);
        }
        return selectedObjects;
    }

}
