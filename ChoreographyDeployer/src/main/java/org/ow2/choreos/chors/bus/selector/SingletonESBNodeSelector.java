package org.ow2.choreos.chors.bus.selector;

import java.util.List;

import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.client.NodesClient;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.SingletonSelector;
import org.ow2.choreos.services.datamodel.ResourceImpact;

public class SingletonESBNodeSelector implements ESBNodeSelector {

    private static final String DEPLOYMENT_MANAGER_URI_PROPERTY = "DEPLOYMENT_MANAGER_URI";
    
    private SingletonSelector<EasyESBNode, ResourceImpact> selector;

    public SingletonESBNodeSelector() {
	String npmUri = ChoreographyDeployerConfiguration.get(DEPLOYMENT_MANAGER_URI_PROPERTY);
	NodePoolManager npm = new NodesClient(npmUri);
	ESBNodeFactory nodeFac = new ESBNodeFactory(npm);
	this.selector = new SingletonSelector<EasyESBNode, ResourceImpact>(nodeFac);
    }

    @Override
    public List<EasyESBNode> select(ResourceImpact requirements, int objectsQuantity) throws NotSelectedException {
	return this.selector.select(requirements, objectsQuantity);
    }

}