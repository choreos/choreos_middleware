package org.ow2.choreos.deployment.nodes.cm;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNodeNameRetriever;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.nodes.NodeNotAccessibleException;
import org.ow2.choreos.deployment.nodes.datamodel.Node;
import org.ow2.choreos.utils.SshCommandFailed;

import com.jcraft.jsch.JSchException;

public class NodeChecker {
	
	private Logger logger = Logger.getLogger(NodeChecker.class);
	
    private static final String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
	
    /**
     * Verify if node is ready to be used.
     * If not, try to provide preconditions: chef node name valid (listed on chef server) and node bootstrapped.
     * If it is not possible to satisfy preconditions, throws an exception.  
     * 
     * @param node
     * @throws NodeNotOKException
     */
    public void checkAndPrepareNode(Node node) throws NodeNotOKException {
    	
    	if (node.getChefName() == null || node.getChefName().isEmpty()) {
    		BootstrapChecker checker = new BootstrapChecker(); 
    		if (!checker.isBootstrapped(node)) {
    			logger.debug("Node "+ node.getIp() +" not bootstrapped yet. Going to bootstrap it.");
				NodeBootstrapper bootstrapper = new NodeBootstrapper(node);
				try {
					bootstrapper.bootstrapNode();
				} catch (NodeNotAccessibleException e) {
					throw new NodeNotOKException();
				} catch (KnifeException e) {
					throw new NodeNotOKException();
				} catch (NodeNotBootstrappedException e) {
					throw new NodeNotOKException();
				}
    		} else {
    			logger.debug("Node "+ node.getIp() +" bootstrapped but without chef name. Going to retrieve chef name");
    			ChefNodeNameRetriever retriever = new ChefNodeNameRetriever();
    			String chefName = "";
				try {
					chefName = retriever.getChefNodeName(node.getIp(), node.getUser(), node.getPrivateKeyFile());
				} catch (JSchException e) {
					throw new NodeNotOKException();
				} catch (SshCommandFailed e) {
					throw new NodeNotOKException();
				}
    			logger.debug("Retrieved chef name for  " + node.getIp() + ": " + chefName);
    			node.setChefName(chefName);
    		}
    	} 

    	if (!this.checkNodeOnNodesList(node)) {
    		throw new NodeNotOKException();
    	}
    }
    
    /**
     * Verify if node chef name is listed by chef server
     * @param node
     * @return
     */
    public boolean checkNodeOnNodesList(Node node) {
    
    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
		List<String> nodes;
		try {
			nodes = knife.node().list();
		} catch (KnifeException e) {
			return false;
		}
		if (nodes == null) {
			return false;
		}
		boolean ok = nodes.contains(node.getChefName());
		if (ok) {
			return true;
		} else {
			logger.debug("Chef does not know node " + node.getChefName());
			return false;
		}
    }

}
