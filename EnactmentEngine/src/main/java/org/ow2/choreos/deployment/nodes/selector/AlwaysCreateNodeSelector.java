package org.ow2.choreos.deployment.nodes.selector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.AlwaysCreateSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class AlwaysCreateNodeSelector implements NodeSelector {

    private Map<String, AlwaysCreateSelector<CloudNode, DeployableServiceSpec>> selectors = new HashMap<String, AlwaysCreateSelector<CloudNode, DeployableServiceSpec>>();

    @Override
    public List<CloudNode> select(DeployableServiceSpec spec, int objectsQuantity) throws NotSelectedException {
	AlwaysCreateSelector<CloudNode, DeployableServiceSpec> selector = retrieveSelector(spec);
	return selector.select(spec, objectsQuantity);
    }

    private AlwaysCreateSelector<CloudNode, DeployableServiceSpec> retrieveSelector(DeployableServiceSpec spec) {
	String cloudAccount = spec.getOwner();

	synchronized (this) {
	    if (!this.selectors.containsKey(cloudAccount)) {
		NodePoolManager npm = NPMFactory.getNewNPMInstance(cloudAccount);
		NodeFactory nodeFac = new NodeFactory(npm);
		this.selectors.put(cloudAccount, new AlwaysCreateSelector<CloudNode, DeployableServiceSpec>(nodeFac));
	    }
	}
	return this.selectors.get(cloudAccount);
    }
}
