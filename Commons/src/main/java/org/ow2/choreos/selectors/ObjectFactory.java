package org.ow2.choreos.selectors;

public interface ObjectFactory<T> {

    public T createNewInstance() throws ObjectCreationException;

}
