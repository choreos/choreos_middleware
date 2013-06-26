package org.ow2.choreos.chors.bus.selector;

import org.ow2.choreos.chors.bus.ESBRegister;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.bus.EasyESBNodeImpl;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;

public class ESBNodeFactory implements ObjectFactory<EasyESBNode, ResourceImpact> {
    
    private static final String EASY_ESB_RECIPE = "easyesb";
    private static final int TIMEOUT_SECONDS = 5*60;
    
    private NodePoolManager npm;

    public ESBNodeFactory(NodePoolManager npm) {
	this.npm = npm;
    }

    @Override
    public EasyESBNode createNewInstance(ResourceImpact requirements) throws ObjectCreationException {
	EasyESBNode esbNode = createNewESBNode();
	ESBRegister.addEsbNode(esbNode);
	return esbNode;
    }
    
    private EasyESBNode createNewESBNode() {
	// TODO
//	    List<CloudNode> nodes = this.npm.prepareDeployment(new DeploymentRequest(EASY_ESB_RECIPE));
//	    this.npm.updateNode(nodes.get(0).getId()); // TODO set time out
//	    String endpoint = this.getEndpoint(nodes.get(0).getIp());
//	    return new EasyESBNodeImpl(endpoint);
	    return new EasyESBNodeImpl("");
    }
    
    private String getEndpoint(String nodeIp) {
	return "http://" + nodeIp + ":8180/services/adminExternalEndpoint";
    }

    @Override
    public int getTimeouInSeconds() {
	return TIMEOUT_SECONDS;
    }

}
