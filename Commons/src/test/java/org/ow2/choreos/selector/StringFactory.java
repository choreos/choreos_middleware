package org.ow2.choreos.selector;

import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;

public class StringFactory implements ObjectFactory<String> {

	private static AtomicInteger counter = new AtomicInteger();
	
	@Override
	public String createNewInstance() throws ObjectCreationException {
		
		return Integer.toString(counter.getAndIncrement());
	}
}

