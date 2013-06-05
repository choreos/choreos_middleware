package org.ow2.choreos.selectors;

import java.util.List;

public interface ObjectRetriever<T> {

    public List<T> retrieveObjects();
}
