package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.DeploymentRequest;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;

public class AlwaysCreateNodeSelector implements NodeSelector {

    private AlwaysCreateSelector<CloudNode, DeploymentRequest> selector;

    public AlwaysCreateNodeSelector() {
	NodePoolManager npm = NPMFactory.getNewNPMInstance();
	NodeFactory nodeFac = new NodeFactory(npm);
	this.selector = new AlwaysCreateSelector<CloudNode, DeploymentRequest>(nodeFac);
    }

    @Override
    public List<CloudNode> select(DeploymentRequest requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }

}
