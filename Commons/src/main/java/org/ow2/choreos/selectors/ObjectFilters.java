package org.ow2.choreos.selectors;

import java.util.List;

public class ObjectFilters<T, R> {

	public ObjectFilter<T, R> getNoFilter() {
		
		ObjectFilter<T, R> noFilter = new ObjectFilter<T, R>() {
			@Override
			public List<T> filter(List<T> objects, R requirements) {
				return objects;
			}
		};
		return noFilter;
	}

}
