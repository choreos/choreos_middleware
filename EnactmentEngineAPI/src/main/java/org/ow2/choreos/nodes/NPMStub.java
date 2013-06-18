package org.ow2.choreos.nodes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;

public class NPMStub implements NodePoolManager {

    AtomicInteger counter = new AtomicInteger();
    List<CloudNode> nodes = new ArrayList<CloudNode>();

    @Override
    public CloudNode createNode(NodeSpec NodeSpec) throws NodeNotCreatedException {
	CloudNode n = new CloudNode();
	takesALong();
	String id = Integer.toString(counter.getAndIncrement());
	n.setId(id);
	nodes.add(n);
	return n;
    }

    private void takesALong() {
	// this delay improves tests reliability, since creating a node is a
	// time consuming task
	Random randomGenerator = new Random();
	int delta = randomGenerator.nextInt(20);
	int waitTime = 200 + delta;
	try {
	    Thread.sleep(waitTime);
	} catch (InterruptedException e) {
	    System.out.println("Exception at sleeping");
	}
    }

    @Override
    public List<CloudNode> getNodes() {
	return Collections.unmodifiableList(nodes);
    }

    @Override
    public CloudNode getNode(String nodeId) throws NodeNotFoundException {
	for (CloudNode node : nodes) {
	    if (nodeId.equals(node.getId())) {
		return node;
	    }
	}
	throw new NoSuchElementException();
    }

    @Override
    public void destroyNodes() throws NodeNotDestroyed {
	throw new UnsupportedOperationException();
    }

    @Override
    public void destroyNode(String nodeId) throws NodeNotDestroyed, NodeNotFoundException {
	throw new UnsupportedOperationException();
    }

    @Override
    public void updateNode(String nodeId) throws NodeNotUpdatedException, NodeNotFoundException {
	throw new UnsupportedOperationException();
    }

}
