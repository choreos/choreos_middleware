package org.ow2.choreos.selectors;

import java.util.ArrayList;
import java.util.List;

public class AlwaysCreateSelector<T, R> implements Selector<T, R> {

    private ObjectFactory<T> objectFactory;

    public AlwaysCreateSelector(ObjectFactory<T> objectFactory) {
	this.objectFactory = objectFactory;
    }

    @Override
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException {

	List<T> selectedObjects = new ArrayList<T>();

	for (int i = 0; i < objectsQuantity; i++) {
	    try {
		T obj = this.objectFactory.createNewInstance();
		selectedObjects.add(obj);
	    } catch (ObjectCreationException e) {
		throw new NotSelectedException();
	    }
	}
	return selectedObjects;
    }

}
