package org.ow2.choreos.npm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

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
 * Uses knife commands (from chef) to manage cloud nodes
 * 
 * @author leonardo, cadu, felps
 *
 */
public class ConfigurationManager {

	private Logger logger = Logger.getLogger(ConfigurationManager.class);
	
    private static String INITIAL_RECIPE = "getting-started";
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
    	
    	final String CHEF_CLIENT_COMMAND = "sudo chef-client >> /tmp/chef-client.log\n";
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

	public boolean isInitialized(Node node) throws JSchException {

        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        logger.debug("Going to connect to " + node);

        String createdFile = "chef-getting-started.txt";
        String returnText = null;
        returnText = ssh.runCommand("ls " + createdFile, true);
        logger.debug(">>" + returnText.trim() + "<<");
        return returnText.trim().equals(createdFile);
    }
    
    public void initializeNode(Node node) throws JSchException, KnifeException {

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	
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

    	logger.info("Bootstrapping " + node.getHostname());
		knife.bootstrap(node.getIp(), node.getUser(), node.getPrivateKeyFile());
		logger.debug("Bootstrap completed");
		this.retrieveChefName(node);
		this.installInitialRecipe(node);
    }

    /**
    *
    * @param node
    * @param cookbook
    * @return <code>true</code> for success, and <code>false</code> to failure
    * @throws Exception
    */
    public boolean installRecipe(Node node, String cookbook)  {
        return this.installRecipe(node, cookbook, "default");
    }

    /**
     *
     * @param node
     * @param cookbook
     * @param recipe
     * @return <code>true</code> for success, and <code>false</code> to failure
     * @throws Exception
     */
    public boolean installRecipe(Node node, String cookbook, String recipe)  {

        try {
			if (!isInitialized(node)) {
				logger.debug("Node not initialized yet. Going to initialize it.");
			    this.initializeNode(node);
			}
		} catch (JSchException e) {
			logger.error(e);
			return false;
		} catch (KnifeException e) {
			logger.error(e);
			return false;
		}

		if (node.getChefName() == null)
			this.retrieveChefName(node);
        
        Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);

        logger.debug("node name = " + node.getChefName());

        // Chef fails silently when adding multiple recipes concurrently
        synchronized(ConfigurationManager.class) {
        	try {
				knife.node().runListAdd(node.getChefName(), cookbook, recipe);
			} catch (KnifeException e) {
				logger.error(e);
				return false;
			}
        }

        // TODO we should verify if the recipe install was OK
        // but it is very awkward make this without using the chef API!
        return true;
    }

    private void retrieveChefName(Node node) {
        
    	ChefNodeNameRetriever nameRetriever = new ChefNodeNameRetriever();
        String chefClientName = null;
		try {
			chefClientName = nameRetriever.getChefNodeName(node.getIp(), node.getUser(), node.getPrivateKeyFile());
		} catch (JSchException e) {
			chefClientName = node.getHostname();
		}

        node.setChefName(chefClientName);
    }
    
    private boolean installInitialRecipe(Node node) {

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO, true);
    	try {
			knife.node().runListAdd(node.getChefName(), INITIAL_RECIPE, "default");
		} catch (KnifeException e) {
			logger.error(
					"Could not add " + INITIAL_RECIPE + " to the run list of node "
							+ node.getChefName(), e);
			return false;
		}

        try {
			this.updateNodeConfiguration(node);
		} catch (JSchException e) {
			logger.error("Could not update node "+ node.getChefName(), e);
			return false;
		}
        
        return true;
    }
}
