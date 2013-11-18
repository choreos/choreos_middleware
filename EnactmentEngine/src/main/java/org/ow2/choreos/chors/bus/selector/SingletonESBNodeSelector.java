package org.ow2.choreos.chors.bus.selector;

import java.util.List;

import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.deployment.CloudConfiguration;
import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.SingletonSelector;

public class SingletonESBNodeSelector implements ESBNodeSelector {

    private SingletonSelector<EasyESBNode, ResourceImpact> selector;

    public SingletonESBNodeSelector() {
	NodePoolManager npm = NPMFactory.getNewNPMInstance(CloudConfiguration.DEFAULT);
	ESBNodeFactory nodeFac = new ESBNodeFactory(npm);
	this.selector = new SingletonSelector<EasyESBNode, ResourceImpact>(nodeFac);
    }

    @Override
    public List<EasyESBNode> select(ResourceImpact requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }

}
