package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;

public class RoundRobinNodeSelector implements NodeSelector {

    private RoundRobinSelector<Node, Config> selector;

    public RoundRobinNodeSelector() {
	NodePoolManager npm = NPMImpl.getNewInstance();
	NodeRetriever retriever = new NodeRetriever(npm);
	this.selector = new RoundRobinSelector<Node, Config>(retriever);
    }

    @Override
    public List<Node> select(Config requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }
}
