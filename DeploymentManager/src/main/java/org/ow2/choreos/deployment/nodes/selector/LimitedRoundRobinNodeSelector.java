package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.deployment.services.preparer.DeploymentRequest;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.LimitedRoundRobinSelector;
import org.ow2.choreos.selectors.NotSelectedException;

public class LimitedRoundRobinNodeSelector implements NodeSelector {

    private static final String VM_LIMIT_PROPERTY = "VM_LIMIT";

    private LimitedRoundRobinSelector<CloudNode, DeploymentRequest> selector;

    public LimitedRoundRobinNodeSelector() {
        int nodeLimit = getNodeLimit();
        NodePoolManager npm = NPMFactory.getNewNPMInstance();
        NodeFactory factory = new NodeFactory(npm);
        NodeRetriever retriever = new NodeRetriever(npm);
        NodeFilter filter = new NodeFilter();
        this.selector = new LimitedRoundRobinSelector<CloudNode, DeploymentRequest>(nodeLimit, retriever, factory,
                filter);
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
    public List<CloudNode> select(DeploymentRequest requirements, int objectsQuantity) throws NotSelectedException {
        return this.selector.select(requirements, objectsQuantity);
    }

}
