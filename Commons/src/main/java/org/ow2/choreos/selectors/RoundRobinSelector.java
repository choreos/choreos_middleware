package org.ow2.choreos.selectors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class RoundRobinSelector<T, R> implements Selector<T, R> {

	private AtomicInteger counter = new AtomicInteger();

	@Override
	public List<T> select(List<T> objects, R requirements, int objectsQuantity)
			throws NotSelectedException {

		List<T> compatibleObjects = this.filterCompatibleObjects(objects, requirements);
		
		if(compatibleObjects.size() < objectsQuantity) {
			throw new NotSelectedException();
		}

		List<T> selectedObjects = new ArrayList<T>();
		
		for (int i=0; i < objectsQuantity; i++) {
			int idx = counter.getAndIncrement();
			idx = idx % compatibleObjects.size();
			T selected = compatibleObjects.get(idx);
			selectedObjects.add(selected);
		}
		return selectedObjects;
	}

	/**
	 * This method is intended to be overridden by subclasses.
	 */
	public List<T> filterCompatibleObjects(List<T> objects, R requirements) {
		
		return objects;
	}
	
}
