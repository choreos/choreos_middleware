package org.ow2.choreos.npm;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.ChefNodeNameRetriever;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.Configuration;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

/**
 * Uses knife commands (from chef) to manage cloud nodes
 * 
 * @author leonardo, cadu, felps
 *
 */
public class ConfigurationManager {

	private Logger logger = Logger.getLogger(ConfigurationManager.class);
	
    private static String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");

    private static ConcurrentMap<Node, Boolean> updating = new ConcurrentHashMap<Node, Boolean>();
    private static ConcurrentMap<Node, Boolean> needUpdate = new ConcurrentHashMap<Node, Boolean>();

    public void updateNodeConfiguration(Node node) throws JSchException {
        
    	needUpdate.put(node, true);

        if (updating.containsKey(node) && updating.get(node)) {
            return;
        }

        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (needUpdate.get(node)) {
            needUpdate.put(node, false);

            updating.put(node, true);
            logger.debug("upgrading node " + node);
            this.runChefClient(ssh);
            updating.put(node, false);
        }
    }

    /**
     * Try to run chef client 5 times
     * 
     * This strategy is carried out since sometimes 
     * we try to ssh the VM when it is not ready yet.
     * 
     * @param ssh
     * @throws JSchException 
     */
    private void runChefClient(SshUtil ssh) throws JSchException {
    	
    	final String CHEF_CLIENT_LOG = Configuration.get("CHEF_CLIENT_LOG");
    	final String CHEF_CLIENT_COMMAND = "sudo chef-client --logfile " + CHEF_CLIENT_LOG;
    	final int MAX_TRIALS = 5;
    	final int SLEEPING_TIME = 5000;
    	int trials = 0;
    	boolean ok = false;
    	
    	while (!ok) {
	    	try {
	    		trials++;
				ssh.runCommand(CHEF_CLIENT_COMMAND);
				ok = true;
			} catch (JSchException e) {
				if (trials >= MAX_TRIALS) {
					throw e;
				}
				try {
					Thread.sleep(SLEEPING_TIME);
				} catch (InterruptedException e1) {
				}
			}
    	}
	}

	public boolean isInitialized(Node node) {

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
		
		return nodes.contains(node.getChefName());
    }
    
    public void initializeNode(Node node) throws JSchException, KnifeException {

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	
        waitForSSHAccess(node);

    	logger.info("Bootstrapping " + node.getHostname());
		knife.bootstrap(node.getIp(), node.getUser(), node.getPrivateKeyFile());
		logger.info("Bootstrap completed at" + node);
		this.retrieveAndSetChefName(node);
    }

	private void waitForSSHAccess(Node node) {
		
		logger.debug("Waiting for SSH...");
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());

        while (!ssh.isAccessible()) {
            logger.debug("Could not connect to " + node.getUser() + "@" + node.getIp()
                    + " with key " + node.getPrivateKeyFile() + " yet");
            logger.debug("Trying again in 5 seconds");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
            ssh.disconnect();
        }
        logger.debug("Connected to " + node);
	}

	/**
	 * Uses the default recipe
	 * @param node
	 * @param cookbook
	 * @return
	 * @throws ConfigNotAppliedException
	 */
    public void installRecipe(Node node, String cookbook) throws ConfigNotAppliedException {
        this.applyRecipe(node, cookbook, "default");
    }

    public void applyRecipe(Node node, String cookbook, String recipe) throws ConfigNotAppliedException {

        try {
			if (!isInitialized(node)) {
				logger.debug("Node not initialized yet. Going to initialize it.");
			    this.initializeNode(node);
			}
		} catch (JSchException e) {
			throw new ConfigNotAppliedException(cookbook + "::" + recipe, node.getId());
		} catch (KnifeException e) {
			throw new ConfigNotAppliedException(cookbook + "::" + recipe, node.getId());
		}

		if (node.getChefName() == null) {
			this.retrieveAndSetChefName(node);
		}
        
        Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);

        logger.debug("node name = " + node.getChefName());

        // Chef fails silently when adding multiple recipes concurrently
        synchronized(ConfigurationManager.class) {
        	try {
				knife.node().runListAdd(node.getChefName(), cookbook, recipe);
			} catch (KnifeException e) {
				throw new ConfigNotAppliedException(cookbook + "::" + recipe, node.getId());
			}
        }

        boolean ok = verifyRecipeInRunList(knife, node.getChefName(), cookbook, recipe);
        if (!ok) {
			throw new ConfigNotAppliedException(cookbook + "::" + recipe, node.getId());
        }
    }

    private boolean verifyRecipeInRunList(Knife knife, String chefName,
			String cookbook, String recipe) {

    	try {
			ChefNode chefNode = knife.node().show(chefName);
			return chefNode.hasRecipeOnRunlist(cookbook + "::" + recipe);
		} catch (KnifeException e) {
			return false;
		}
	}

	private void retrieveAndSetChefName(Node node) {
        
    	ChefNodeNameRetriever nameRetriever = new ChefNodeNameRetriever();
        String chefClientName = null;
		try {
			chefClientName = nameRetriever.getChefNodeName(node.getIp(), node.getUser(), node.getPrivateKeyFile());
		} catch (JSchException e) {
			chefClientName = node.getHostname();
		}

        node.setChefName(chefClientName);
    }
    
}
