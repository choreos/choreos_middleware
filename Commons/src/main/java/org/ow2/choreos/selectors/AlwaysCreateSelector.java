package org.ow2.choreos.selectors;

import java.util.ArrayList;
import java.util.List;


public class AlwaysCreateSelector<T, R> implements Selector<T, R> {

	@Override
	public List<T> select(List<T> objects, ObjectFactory<T> objectFactory,
			R requirements, int objectsQuantity) throws NotSelectedException {

		List<T> selectedObjects = new ArrayList<T>();

		for(int i = 0; i < objectsQuantity; i++) {
			try {
				T obj = objectFactory.createNewInstance();
				selectedObjects.add(obj);
			} catch (ObjectCreationException e) {
				throw new NotSelectedException();
			}
		}
		return selectedObjects;
	}

}
