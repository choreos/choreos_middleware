package org.ow2.choreos.deployment.nodes.selector;

import java.util.List;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class AlwaysCreateNodeSelector implements NodeSelector {

    private AlwaysCreateSelector<CloudNode, DeployableServiceSpec> selector;

    public AlwaysCreateNodeSelector() {
        NodePoolManager npm = NPMFactory.getNewNPMInstance();
        NodeFactory nodeFac = new NodeFactory(npm);
        this.selector = new AlwaysCreateSelector<CloudNode, DeployableServiceSpec>(nodeFac);
    }

    @Override
    public List<CloudNode> select(DeployableServiceSpec spec, int objectsQuantity) throws NotSelectedException {
        return this.selector.select(spec, objectsQuantity);
    }

}
