package org.ow2.choreos.selector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ow2.choreos.selectors.ObjectRetriever;

public class StringRetriever implements ObjectRetriever<String> {

    private List<String> objects = new ArrayList<String>();

    @Override
    public List<String> retrieveObjects() {

	return Collections.unmodifiableList(this.objects);
    }

    public void addObject(String obj) {

	if (!this.objects.contains(obj))
	    this.objects.add(obj);
    }

}
