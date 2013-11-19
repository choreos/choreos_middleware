package org.ow2.choreos.ee.bus.selector;

import java.util.List;

import org.ow2.choreos.ee.CloudConfiguration;
import org.ow2.choreos.ee.bus.EasyESBNode;
import org.ow2.choreos.ee.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;

public class AlwaysCreateESBNodeSelector implements ESBNodeSelector {

    private AlwaysCreateSelector<EasyESBNode, ResourceImpact> selector;

    public AlwaysCreateESBNodeSelector() {
	NodePoolManager npm = NPMFactory.getNewNPMInstance(CloudConfiguration.DEFAULT);
	ESBNodeFactory nodeFac = new ESBNodeFactory(npm);
	this.selector = new AlwaysCreateSelector<EasyESBNode, ResourceImpact>(nodeFac);
    }

    @Override
    public List<EasyESBNode> select(ResourceImpact requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }

}
