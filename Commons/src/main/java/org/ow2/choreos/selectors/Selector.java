package org.ow2.choreos.selectors;

import java.util.List;

public interface Selector<T,R> {

	/**
	 * Selects objects from a given source according to the requirements.
	 * If necessary, creates new objects using a given factory.
	 * 
	 * @param requirements
	 * @param objectsQuantity
	 * @return the selected objects
	 * @throws NotSelectedException
	 */
	public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException;

}
