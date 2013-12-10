package org.ow2.choreos.ee.services.preparer;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ow2.choreos.ee.nodes.selector.NodeSelector;
import org.ow2.choreos.ee.nodes.selector.NodeSelectorFactory;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.selectors.NotSelectedException;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;

public class ServiceDeploymentPreparer {

    private DeployableServiceSpec newSpec;
    private DeployableService service;
    private String serviceSpecName;
    private Set<CloudNode> nodes;

    private Logger logger = Logger.getLogger(ServiceDeploymentPreparer.class);

    public ServiceDeploymentPreparer(DeployableServiceSpec newSpec, DeployableService service) {
	this.newSpec = newSpec;
	this.service = service;
	this.serviceSpecName = newSpec.getName();
    }

    public Set<CloudNode> prepareDeployment() throws PrepareDeploymentFailedException {
	selectNodes();
	prepareInstances();
	return nodes;
    }

    private void selectNodes() throws PrepareDeploymentFailedException {
	NodeSelector selector = NodeSelectorFactory.getFactoryInstance().getNodeSelectorInstance();
	try {
	    List<CloudNode> nodesList = selector.select(newSpec, newSpec.getNumberOfInstances());
	    nodes = new HashSet<CloudNode>(nodesList);
	    logger.info("Selected nodes to " + serviceSpecName + ": " + nodes);
	} catch (NotSelectedException e) {
	    failDeployment();
	}
	if (nodes == null || nodes.isEmpty()) {
	    failDeployment();
	}
    }

    private void failDeployment() throws PrepareDeploymentFailedException {
	throw new PrepareDeploymentFailedException(serviceSpecName);
    }

    private void prepareInstances() {
	for (CloudNode node : nodes) {
	    try {
		InstanceDeploymentPreparer instanceDeploymentPreparer = new InstanceDeploymentPreparer(newSpec,
			service, node);
		instanceDeploymentPreparer.prepareDeployment();
	    } catch (PrepareDeploymentFailedException e) {
		logger.error(e.getMessage());
	    }
	}
    }

}
