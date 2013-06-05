/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes.selector;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Test;
import org.ow2.choreos.utils.Concurrency;

public class NodeSelectorFactoryTest {

    private static final String NODE_SELECTOR_TYPE = "ROUND_ROBIN";

    @Test
    public void shouldRetrieveRoundRobinNodeSelector() {

	NodeSelector nodeSelector = NodeSelectorFactory.getInstance(NODE_SELECTOR_TYPE);

	assertEquals(RoundRobinSelector.class, nodeSelector.getClass());
    }

    @Test
    public void shouldRetrieveSingletonInstance() {

	NodeSelector nodeSelector1 = NodeSelectorFactory.getInstance(NODE_SELECTOR_TYPE);
	NodeSelector nodeSelector2 = NodeSelectorFactory.getInstance(NODE_SELECTOR_TYPE);

	assertTrue(nodeSelector1 == nodeSelector2);
    }

    @Test
    public void shouldConcurrentlyRetrieveSingletonInstance() throws InterruptedException, ExecutionException {

	ExecutorService executor = Executors.newFixedThreadPool(3);
	Future<NodeSelector> f1 = executor.submit(new NodeSelectorRetriever());
	Future<NodeSelector> f2 = executor.submit(new NodeSelectorRetriever());
	Future<NodeSelector> f3 = executor.submit(new NodeSelectorRetriever());

	Concurrency.waitExecutor(executor, 1);

	NodeSelector selector1 = f1.get();
	NodeSelector selector2 = f2.get();
	NodeSelector selector3 = f3.get();

	assertTrue(selector1 == selector2);
	assertTrue(selector1 == selector3);
    }

    private class NodeSelectorRetriever implements Callable<NodeSelector> {

	@Override
	public NodeSelector call() throws Exception {
	    return NodeSelectorFactory.getInstance(NODE_SELECTOR_TYPE);
	}
    }
}
