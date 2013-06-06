package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;

class AlwaysCreateNodeSelector implements NodeSelector {

    private AlwaysCreateSelector<Node, DeploymentRequest> selector;

    public AlwaysCreateNodeSelector() {
	NodePoolManager npm = NPMImpl.getNewInstance();
	NodeFactory nodeFac = new NodeFactory(npm);
	this.selector = new AlwaysCreateSelector<Node, DeploymentRequest>(nodeFac);
    }

    @Override
    public List<Node> select(DeploymentRequest requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }
    
}
