package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class RoundRobinNodeSelector implements NodeSelector {

    private RoundRobinSelector<CloudNode, DeployableServiceSpec> selector;

    public RoundRobinNodeSelector() {
        NodePoolManager npm = NPMFactory.getNewNPMInstance();
        NodeRetriever retriever = new NodeRetriever(npm);
        NodeFilter filter = new NodeFilter();
        this.selector = new RoundRobinSelector<CloudNode, DeployableServiceSpec>(retriever, filter);
    }

    @Override
    public List<CloudNode> select(DeployableServiceSpec spec, int objectsQuantity) throws NotSelectedException {
        return this.selector.select(spec, objectsQuantity);
    }

}
