/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;

public class RoundRobinSelectorTest {

    private StringRetriever retriever;

    @Before
    public void setup() {

        this.retriever = new StringRetriever();
        this.retriever.addObject("Object1");
        this.retriever.addObject("Object2");
        this.retriever.addObject("Object3");
    }

    @Test
    public void shouldApplyRoundRobin() throws NotSelectedException {

        RoundRobinSelector<String, String> rr = new RoundRobinSelector<String, String>(retriever);

        String requirements = "requirements";

        int quantity = 1;
        List<String> a = rr.select(requirements, quantity);
        List<String> b = rr.select(requirements, quantity);
        List<String> c = rr.select(requirements, quantity);
        List<String> d = rr.select(requirements, quantity);

        assertEquals(quantity, a.size());
        assertEquals(quantity, b.size());
        assertEquals(quantity, c.size());
        assertEquals(quantity, d.size());

        assertFalse(a.get(0).equals(b.get(0)));
        assertFalse(a.get(0).equals(c.get(0)));
        assertFalse(c.get(0).equals(b.get(0)));

        assertTrue(a.get(0).equals(d.get(0)));

        quantity = 2;
        List<String> aa = rr.select(requirements, quantity);
        List<String> bb = rr.select(requirements, quantity);

        assertEquals(quantity, aa.size());
        assertEquals(quantity, bb.size());

        assertFalse(aa.get(0).equals(aa.get(1)));
        assertFalse(aa.get(1).equals(bb.get(0)));
        assertTrue(aa.get(0).equals(bb.get(1)));

    }

    @Test(expected = NotSelectedException.class)
    public void shouldNotSelect() throws NotSelectedException {

        RoundRobinSelector<String, String> rr = new RoundRobinSelector<String, String>(retriever);

        String requirements = "requirements";
        int tooLargeQuantity = 5;
        rr.select(requirements, tooLargeQuantity);
    }

}
