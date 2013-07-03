/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selector;

import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;

public class StringFactory implements ObjectFactory<String, String> {

    private AtomicInteger counter = new AtomicInteger();

    @Override
    public String createNewInstance(String requirements) throws ObjectCreationException {
        return Integer.toString(counter.getAndIncrement());
    }

    @Override
    public int getTimeoutInSeconds() {
        return 10;
    }

}
