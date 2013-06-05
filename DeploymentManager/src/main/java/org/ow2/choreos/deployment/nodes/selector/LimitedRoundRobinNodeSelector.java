package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.Config;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.LimitedRoundRobinSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.ObjectFilter;
import org.ow2.choreos.selectors.ObjectFilters;

public class LimitedRoundRobinNodeSelector implements NodeSelector {

    private static final String VM_LIMIT_PROPERTY = "VM_LIMIT";
    
    private LimitedRoundRobinSelector<Node, Config> selector;
    
    public LimitedRoundRobinNodeSelector() {
	int nodeLimit = getNodeLimit();
	NodePoolManager npm = NPMImpl.getNewInstance();
	NodeFactory factory = new NodeFactory(npm);
	NodeRetriever retriever = new NodeRetriever(npm);
	ObjectFilters<Node, Config> filters = new ObjectFilters<Node, Config>();
	ObjectFilter<Node, Config> filter = filters.getNoFilter(); // TODO use real filter
	this.selector = new LimitedRoundRobinSelector<Node, Config>(nodeLimit, retriever, factory, filter);
    }

    private int getNodeLimit() {
	int nodeLimit;
	try {
	    nodeLimit = Integer.parseInt(DeploymentManagerConfiguration.get(VM_LIMIT_PROPERTY));
	} catch (NumberFormatException e) {
	    final int INFINITE_NODES = 100000000;
	    nodeLimit = INFINITE_NODES;
	}
	return nodeLimit;
    }
    
    @Override
    public List<Node> select(Config requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }

}
