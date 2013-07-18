package org.ow2.choreos.chors.bus.selector;

import org.ow2.choreos.chors.ChoreographyDeployerConfiguration;
import org.ow2.choreos.chors.bus.ESBRegister;
import org.ow2.choreos.chors.bus.EasyESBException;
import org.ow2.choreos.chors.bus.EasyESBNode;
import org.ow2.choreos.chors.bus.EasyESBNodeImpl;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotFoundException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.NodePoolManager;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.nodes.datamodel.ResourceImpact;
import org.ow2.choreos.selectors.ObjectCreationException;
import org.ow2.choreos.selectors.ObjectFactory;

/**
 * EasyESB nodes are always created on new dedicated nodes.
 * @author leonardo
 *
 */
public class ESBNodeFactory implements ObjectFactory<EasyESBNode, ResourceImpact> {

    private static final int TIMEOUT_SECONDS = 5 * 60;
    private static final String EASIER_BSM_ADMIN_ENDPOINT_PROPERTY = "EASIER_BSM_ADMIN_ENDPOINT";

    private NodePoolManager npm;
    private CloudNode node;
    private EasyESBNode esbNode;

    public ESBNodeFactory(NodePoolManager npm) {
        this.npm = npm;
    }

    @Override
    public EasyESBNode createNewInstance(ResourceImpact requirements) throws ObjectCreationException {
        createNewCloudNode();
        createNewESBNode();
        notifyBSM();
        ESBRegister.addEsbNode(esbNode);
        return esbNode;
    }
    
    private void createNewCloudNode() throws ObjectCreationException {
        try {
            node = npm.createNode(new NodeSpec());
        } catch (NodeNotCreatedException e) {
            throw new ObjectCreationException();
        }
    }

    private void createNewESBNode() throws ObjectCreationException {
        ESBDeploymentPreparer preparer = new ESBDeploymentPreparer(node);
        try {
            preparer.prepareESBDeployment();
        } catch (BusNotPreparedException e) {
            throw new ObjectCreationException();
        }
        try {
            this.npm.updateNode(node.getId());
        } catch (NodeNotUpdatedException e) {
            throw new ObjectCreationException();
        } catch (NodeNotFoundException e) {
            throw new ObjectCreationException();
        }
        String endpoint = this.getEndpoint();
        esbNode = new EasyESBNodeImpl(endpoint);
    }
    
    private void notifyBSM() {
        String bsmAdminEndpoint = ChoreographyDeployerConfiguration.get(EASIER_BSM_ADMIN_ENDPOINT_PROPERTY);
        if (bsmAdminEndpoint != null && !bsmAdminEndpoint.isEmpty()) {
            try {
                this.esbNode.notifyEasierBSM(bsmAdminEndpoint);
            } catch (EasyESBException e) {
                ; // log will be printed within EasyESBNodeImpl class
            }
        }
    }    
    
    private String getEndpoint() {
        return "http://" + node.getIp() + ":8180/services/adminExternalEndpoint";
    }

    @Override
    public int getTimeoutInSeconds() {
        return TIMEOUT_SECONDS;
    }

}
