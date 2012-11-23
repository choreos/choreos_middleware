package org.ow2.choreos.npm.cm;

import java.util.List;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNodeNameRetriever;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.Configuration;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, cadu, felps
 *
 */
public class NodeBootstrapper {
	
	private Logger logger = Logger.getLogger(NodeBootstrapper.class);
	
    private static String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
    
    private Node node;
    
    public NodeBootstrapper(Node node) {
    	this.node = node;
    }
    
	public boolean isNodeAlreadyBootstrapped() {

		Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
		
		List<String> nodes;
		try {
			nodes = knife.node().list();
		} catch (KnifeException e) {
			logger.error("Knife exception when verifying if node was initialized", e);
			return false;
		}
		
		if (nodes == null) { 
			logger.error("knife.node().list() returned null list; this should never happen.");
			return false;
		}
		
		return nodes.contains(this.node.getChefName());
    }
	
    public void bootstrapNode() throws JSchException, KnifeException {

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	
        waitForSSHAccess();

    	logger.info("Bootstrapping " + this.node.getHostname());
		knife.bootstrap(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile());
		logger.info("Bootstrap completed at" + this.node);
		this.retrieveAndSetChefName();
    }

	private void waitForSSHAccess() {
		
		logger.debug("Waiting for SSH...");
        SshUtil ssh = new SshUtil(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile());

        while (!ssh.isAccessible()) {
            logger.debug("Could not connect to " + this.node.getUser() + "@" + this.node.getIp()
                    + " with key " + this.node.getPrivateKeyFile() + " yet");
            logger.debug("Trying again in 5 seconds");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            ssh.disconnect();
        }
        logger.debug("Connected to " + this.node);
	}
	
	public void retrieveAndSetChefName() {
        
    	ChefNodeNameRetriever nameRetriever = new ChefNodeNameRetriever();
        String chefClientName = null;
		try {
			chefClientName = nameRetriever.getChefNodeName(this.node.getIp(), 
					this.node.getUser(), this.node.getPrivateKeyFile());
		} catch (JSchException e) {
			chefClientName = this.node.getHostname();
		}

		this.node.setChefName(chefClientName);
    }



}
