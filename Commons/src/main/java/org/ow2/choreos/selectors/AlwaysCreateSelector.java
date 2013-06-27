/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

import java.util.ArrayList;
import java.util.List;

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

    public AlwaysCreateSelector(ObjectFactory<T, R> objectFactory) {
        this.objectFactory = objectFactory;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

        List<T> selectedObjects = new ArrayList<T>();

        for (int i = 0; i < objectsQuantity; i++) {
            try {
                T obj = this.objectFactory.createNewInstance(requirements);
                selectedObjects.add(obj);
            } catch (ObjectCreationException e) {
                throw new NotSelectedException();
            }
        }
        return selectedObjects;
    }

}
