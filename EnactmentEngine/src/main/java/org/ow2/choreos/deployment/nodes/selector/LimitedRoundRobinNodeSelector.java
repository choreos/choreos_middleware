package org.ow2.choreos.deployment.nodes.selector;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.choreos.deployment.DeploymentManagerConfiguration;
import org.ow2.choreos.deployment.nodes.NPMFactory;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.LimitedRoundRobinSelector;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class LimitedRoundRobinNodeSelector implements NodeSelector {

    private static final String VM_LIMIT_PROPERTY = "VM_LIMIT";

    private Map<String, LimitedRoundRobinSelector<CloudNode, DeployableServiceSpec>> selectors = new HashMap<String, LimitedRoundRobinSelector<CloudNode, DeployableServiceSpec>>();

    @Override
    public List<CloudNode> select(DeployableServiceSpec spec, int objectsQuantity) throws NotSelectedException {
	LimitedRoundRobinSelector<CloudNode, DeployableServiceSpec> selector = retrieveSelector(spec);
	return selector.select(spec, objectsQuantity);
    }

    private LimitedRoundRobinSelector<CloudNode, DeployableServiceSpec> retrieveSelector(
	    DeployableServiceSpec serviceSpec) {
	String cloudAccount = serviceSpec.getOwner();

	synchronized (this) {
	    if (!this.selectors.containsKey(cloudAccount)) {
		int nodeLimit = getNodeLimit();
		NodePoolManager npm = NPMFactory.getNewNPMInstance(cloudAccount);
		NodeFactory factory = new NodeFactory(npm);
		NodeRetriever retriever = new NodeRetriever(npm);
		NodeFilter filter = new NodeFilter();
		this.selectors.put(cloudAccount, new LimitedRoundRobinSelector<CloudNode, DeployableServiceSpec>(
			nodeLimit, retriever, factory, filter));
	    }
	}

	return this.selectors.get(cloudAccount);
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
}
