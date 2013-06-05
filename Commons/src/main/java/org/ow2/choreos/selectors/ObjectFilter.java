/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

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
