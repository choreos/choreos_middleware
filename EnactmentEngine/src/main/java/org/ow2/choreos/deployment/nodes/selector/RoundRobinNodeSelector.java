package org.ow2.choreos.deployment.nodes.selector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.selectors.RoundRobinSelector;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class RoundRobinNodeSelector implements NodeSelector {

    private Map<String, RoundRobinSelector<CloudNode, DeployableServiceSpec>> selectors = new HashMap<String, RoundRobinSelector<CloudNode, DeployableServiceSpec>>();

    public RoundRobinNodeSelector() {
    }

    public RoundRobinSelector<CloudNode, DeployableServiceSpec> retrieveSelector(DeployableServiceSpec spec) {
	String cloudAccount = spec.getOwner();

	synchronized (this) {
	    if (!this.selectors.containsKey(cloudAccount)) {
		NodePoolManager npm = NPMFactory.getNewNPMInstance(cloudAccount);
		NodeRetriever retriever = new NodeRetriever(npm);
		NodeFilter filter = new NodeFilter();
		this.selectors.put(cloudAccount, new RoundRobinSelector<CloudNode, DeployableServiceSpec>(retriever,
			filter));
	    }
	}
	return this.selectors.get(cloudAccount);
    }

    @Override
    public List<CloudNode> select(DeployableServiceSpec spec, int objectsQuantity) throws NotSelectedException {
	RoundRobinSelector<CloudNode, DeployableServiceSpec> selector = retrieveSelector(spec);
	return selector.select(spec, objectsQuantity);
    }

}
