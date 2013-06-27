/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.Selector;

public class AlwaysCreateSelectorTest {

    @Test
    public void shouldAlwaysCreateDifferentObjects() throws NotSelectedException {

        String requirements = "requirements";
        StringFactory fac = new StringFactory();

        Selector<String, String> selector = new AlwaysCreateSelector<String, String>(fac);
        List<String> selected1 = selector.select(requirements, 2);
        List<String> selected2 = selector.select(requirements, 2);

        assertEquals(2, selected1.size());
        assertEquals(2, selected2.size());
        assertTrue(selected1.get(0) != selected1.get(1));
        assertTrue(selected2.get(0) != selected2.get(1));
        assertTrue(selected1.get(0) != selected2.get(0));
    }

}
