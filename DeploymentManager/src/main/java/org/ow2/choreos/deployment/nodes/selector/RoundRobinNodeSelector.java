package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.deployment.services.preparer.DeploymentRequest;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;

public class RoundRobinNodeSelector implements NodeSelector {

    private RoundRobinSelector<CloudNode, DeploymentRequest> selector;

    public RoundRobinNodeSelector() {
        NodePoolManager npm = NPMFactory.getNewNPMInstance();
        NodeRetriever retriever = new NodeRetriever(npm);
        NodeFilter filter = new NodeFilter();
        this.selector = new RoundRobinSelector<CloudNode, DeploymentRequest>(retriever, filter);
    }

    @Override
    public List<CloudNode> select(DeploymentRequest requirements, int objectsQuantity) throws NotSelectedException {
        return this.selector.select(requirements, objectsQuantity);
    }

}
