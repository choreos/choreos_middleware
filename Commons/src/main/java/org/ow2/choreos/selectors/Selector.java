/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selectors;

import java.util.List;

public interface Selector<T, R> {

    /**
     * Selects objects from a given source according to the requirements. If
     * necessary, creates new objects using a given factory.
     * 
     * @param requirements
     * @param objectsQuantity
     * @return the selected objects
     * @throws NotSelectedException
     */
    public List<T> select(R requirements, int objectsQuantity) throws NotSelectedException;

}
