/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

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
