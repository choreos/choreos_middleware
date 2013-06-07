package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;

public class RoundRobinNodeSelector implements NodeSelector {

    private RoundRobinSelector<Node, DeploymentRequest> selector;

    public RoundRobinNodeSelector() {
	NodePoolManager npm = NPMImpl.getNewInstance();
	NodeRetriever retriever = new NodeRetriever(npm);
	NodeFilter filter = new NodeFilter(); 
	this.selector = new RoundRobinSelector<Node, DeploymentRequest>(retriever, filter);
    }

    @Override
    public List<Node> select(DeploymentRequest requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }
    
}
