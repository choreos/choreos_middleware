package org.ow2.choreos.chors.bus.selector;

import java.util.List;

import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.rest.Owners;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.LimitedRoundRobinSelector;
import org.ow2.choreos.selectors.NotSelectedException;

public class LimitedRoundRobinESBNodeSelector implements ESBNodeSelector {

    private static final String ESB_NODES_LIMIT_PROPERTY = "ESB_NODES_LIMIT";

    private LimitedRoundRobinSelector<EasyESBNode, ResourceImpact> selector;

    public LimitedRoundRobinESBNodeSelector() {
        int nodeLimit = this.getNodeLimit();
        String npmUri = Owners.getDefault();
        NodePoolManager npm = new NodesClient(npmUri);
        ESBNodeFactory factory = new ESBNodeFactory(npm);
        ESBNodeRetriever retriever = new ESBNodeRetriever();
        this.selector = new LimitedRoundRobinSelector<EasyESBNode, ResourceImpact>(nodeLimit, retriever, factory);
    }

    private int getNodeLimit() {
        int nodeLimit;
        try {
            nodeLimit = Integer.parseInt(ChoreographyDeployerConfiguration.get(ESB_NODES_LIMIT_PROPERTY));
        } catch (NumberFormatException e) {
            final int INFINITE_NODES = 100000000;
            nodeLimit = INFINITE_NODES;
        }
        return nodeLimit;
    }

    @Override
    public List<EasyESBNode> select(ResourceImpact requirements, int objectsQuantity) throws NotSelectedException {
        return this.selector.select(requirements, objectsQuantity);
    }

}
