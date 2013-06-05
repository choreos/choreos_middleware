package org.ow2.choreos.selectors;

import java.util.List;

/**
 * Filter objects according to the requirements.
 * 
 * @author leonardo
 * 
 * @param <T>
 * @param <R>
 */
public interface ObjectFilter<T, R> {

    public List<T> filter(List<T> objects, R requirements);
}
