/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package org.ow2.choreos.deployment.nodes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.LogConfigurator;

public class IdlePoolTest {

    private NodeCreator nodeCreator;

    @BeforeClass
    public static void setUpClass() {
        LogConfigurator.configLog();
    }

    @Before
    public void setUp() throws NodeNotCreatedException {

        int N = 10;
        nodeCreator = mock(NodeCreator.class);
        OngoingStubbing<CloudNode> ongoingStubbing = when(nodeCreator.createBootstrappedNode(any(NodeSpec.class)));
        for (int i = 0; i < N; i++) {
            CloudNode node = new CloudNode();
            node.setId("node" + i);
            ongoingStubbing = ongoingStubbing.thenReturn(node);
        }
    }

    @Test
    public void shouldCreateExtraVMs() throws InterruptedException {

        int N = 3;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        int howManyVMs = N;
        pool.createExtraVMs(howManyVMs);

        Thread.sleep(300);

        Set<CloudNode> idlePool = pool.getIdleNodes();
        assertEquals(howManyVMs, idlePool.size());
    }

    @Test(timeout = 2000)
    public void shouldFillThePool() throws InterruptedException {

        int N = 3;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        pool.createExtraVMs(1);
        pool.fillPool();

        while (true) {
            if (pool.isFull())
                break; // pass the test
            Thread.sleep(50);
        }
    }

    @Test(timeout = 1000)
    public void shouldFillThePoolConcurrently() throws InterruptedException {

        int N = 5;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        pool.createExtraVMs(1);
        Thread.sleep(100);
        for (int i = 0; i < 3; i++) {
            PoolFiller filler = new PoolFiller(pool);
            Thread thrd = new Thread(filler);
            thrd.start();
        }

        while (true) {
            if (pool.isFull())
                break; // pas the test
            Thread.sleep(50);
        }
    }

    @Test
    public void multipleClientsShouldNotRetrieveTheSameNode() throws InterruptedException {

        int N = 5;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        pool.fillPool();
        Thread.sleep(100);
        List<PoolClient> clients = new ArrayList<PoolClient>();
        for (int i = 0; i < 3; i++) {
            PoolClient client = new PoolClient(pool);
            clients.add(client);
            Thread thrd = new Thread(client);
            thrd.start();
        }

        Thread.sleep(200);

        Iterator<PoolClient> it = clients.iterator();
        CloudNode previousNode = it.next().retrievedNode;
        assertNotNull(previousNode);
        while (it.hasNext()) {
            CloudNode node = it.next().retrievedNode;
            assertNotNull(node);
            assertNotSame(previousNode, node);
            previousNode = node;
        }
    }

    @Test(timeout = 2000)
    public void multipleRequestsShouldLeaveThePoolFull() throws InterruptedException {

        int N = 5;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        pool.fillPool();
        Thread.sleep(100);
        for (int i = 0; i < 3; i++) {
            PoolConsumerAndFiller client = new PoolConsumerAndFiller(pool);
            Thread thrd = new Thread(client);
            thrd.start();
        }

        while (true) {
            if (pool.isFull())
                break; // pas the test
            Thread.sleep(50);
        }
    }

    @Test
    public void shouldRetrieveANodeEvenWithAnEmptyPool() throws NodeNotCreatedException {

        int N = 5;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        CloudNode node = pool.retriveNode();
        assertNotNull(node);
        assertNotNull(node.getId());
        assertFalse(node.getId().isEmpty());
    }

    @Test
    public void shouldRetrieveANodeThatWasAlreadyInThePool() throws NodeNotCreatedException, InterruptedException {

        int N = 5;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        pool.fillPool();

        Thread.sleep(200);

        List<String> nodes = new ArrayList<String>();
        for (CloudNode n : pool.getIdleNodes()) {
            nodes.add(n.getId());
        }

        CloudNode node = pool.retriveNode();
        assertNotNull(node);
        assertNotNull(node.getId());
        assertFalse(node.getId().isEmpty());
        assertTrue(nodes.contains(node.getId()));
    }

    @Test
    public void multipleClientsShouldRetrieveNodesEvenWithAnEmptyPool() throws InterruptedException {

        int N = 5;
        IdlePool pool = IdlePool.getCleanInstance(N, nodeCreator);
        List<PoolClient> clients = new ArrayList<PoolClient>();
        for (int i = 0; i < 3; i++) {
            PoolClient client = new PoolClient(pool);
            clients.add(client);
            Thread thrd = new Thread(client);
            thrd.start();
        }

        Thread.sleep(200);

        Iterator<PoolClient> it = clients.iterator();
        CloudNode previousNode = it.next().retrievedNode;
        assertNotNull(previousNode);
        while (it.hasNext()) {
            CloudNode node = it.next().retrievedNode;
            assertNotNull(node);
            assertNotSame(previousNode, node);
            previousNode = node;
        }
    }

    private class PoolFiller implements Runnable {

        IdlePool pool;

        public PoolFiller(IdlePool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            this.pool.fillPool();
        }
    }

    private class PoolClient implements Runnable {

        IdlePool pool;
        CloudNode retrievedNode;

        public PoolClient(IdlePool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                this.retrievedNode = pool.retriveNode();
            } catch (NodeNotCreatedException e) {
                System.out.println("=(");
            }
        }
    }

    private class PoolConsumerAndFiller implements Runnable {

        IdlePool pool;

        public PoolConsumerAndFiller(IdlePool pool) {
            this.pool = pool;
        }

        @Override
        public void run() {
            try {
                pool.retriveNode();
            } catch (NodeNotCreatedException e) {
                System.out.println("=(");
            }
            pool.fillPool();
        }
    }

}
