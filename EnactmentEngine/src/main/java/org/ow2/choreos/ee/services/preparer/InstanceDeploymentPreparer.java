package org.ow2.choreos.ee.services.preparer;

import org.ow2.choreos.ee.nodes.cm.NodeNotPreparedException;
import org.ow2.choreos.ee.nodes.cm.NodePreparer;
import org.ow2.choreos.ee.nodes.cm.NodePreparers;
import org.ow2.choreos.ee.nodes.cm.NodeUpdater;
import org.ow2.choreos.ee.nodes.cm.NodeUpdaters;
import org.ow2.choreos.ee.nodes.cm.PackageTypeToCookbook;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.services.datamodel.DeployableService;
import org.ow2.choreos.services.datamodel.DeployableServiceSpec;
import org.ow2.choreos.utils.SshWaiter;

public class InstanceDeploymentPreparer {

    private DeployableService service;
    private CloudNode node;
    private DeployableServiceSpec newSpec;

    private String instanceId;

    SshWaiter sshWaiter = new SshWaiter();

    public InstanceDeploymentPreparer(DeployableServiceSpec newSpec, DeployableService service, CloudNode node) {
	this.newSpec = newSpec;
	this.service = service;
	this.node = node;
    }

    public void prepareDeployment() throws PrepareDeploymentFailedException {
	runDeploymentPrepare();
	scheduleHandler();
    }

    private void runDeploymentPrepare() throws PrepareDeploymentFailedException {
	NodePreparer nodePreparer = NodePreparers.getPreparerFor(node);
	String packageUri = newSpec.getPackageUri();
	String cookbookTemplateName = PackageTypeToCookbook.getCookbookName(newSpec.getPackageType());
	try {
	    instanceId = nodePreparer.prepareNodeForDeployment(packageUri, cookbookTemplateName);
	} catch (NodeNotPreparedException e1) {
	    throw new PrepareDeploymentFailedException(newSpec.getName(), node);
	}
    }

    private void scheduleHandler() {
	InstanceCreatorUpdateHandler handler = new InstanceCreatorUpdateHandler(service, instanceId, node);
	NodeUpdater nodeUpdater = NodeUpdaters.getUpdaterFor(node);
	nodeUpdater.addHandler(handler);
    }

}
