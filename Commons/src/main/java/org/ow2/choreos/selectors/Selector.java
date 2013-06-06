/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

import java.util.List;

/**
 * Selects objects from a given source according to the requirements. 
 * If necessary, creates new objects using a given factory.
 * 
 * @author leonardo
 * 
 * @param <T> the class of the selected resource
 * @param <R> the class of the requirements
 */
public interface Selector<T, R> {

    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException;

}
