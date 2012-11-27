package org.ow2.choreos.npm.cm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNode;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.npm.NodeNotUpgradedException;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.servicedeployer.Configuration;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, cadu, felps
 *
 */
public class NodeUpgrader {

	private Logger logger = Logger.getLogger(NodeUpgrader.class);
	
    private static final String CHEF_REPO = Configuration.get("CHEF_REPO");
	private static final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
	
    private static ConcurrentMap<Node, Boolean> updating = new ConcurrentHashMap<Node, Boolean>();
    private static ConcurrentMap<Node, Boolean> needUpdate = new ConcurrentHashMap<Node, Boolean>();

    /**
     * Runs chef-client in a given node
     * @param node
     * @throws JSchException if could not connect into the node
     * @throws NodeNotUpgradedException if chef-client ends in error
     */
    public void upgradeNodeConfiguration(Node node) throws JSchException, NodeNotUpgradedException {
        
    	List<String> newRecipes = this.findNewRecipes(node);
    	
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
        
        if (!this.checkNewRecipes(node, newRecipes)) {
        	throw new NodeNotUpgradedException(node.getId());
        }
        
    }

	/**
     * Finds which are the new recipes that are going to be installed
     * by comparing the run list and the recipes list.
     * 
     * @param node
     * @return
     * @throws KnifeException 
     */
    private List<String> findNewRecipes(Node node){

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	ChefNode chefNode = null;
		try {
			chefNode = knife.node().show(node.getChefName());
		} catch (KnifeException e) {
			logger.error("Knife exception. Should not happen", e);		
		}
    	List<String> runList = chefNode.getSimpleRunList();
    	List<String> recipes = chefNode.getRecipes();
    	List<String> newRecipes = new ArrayList<String>();
    	for (String item: runList) {
    		if (!recipes.contains(item)) {
    			newRecipes.add(item);
    		}
    	}
    	return newRecipes;
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
    	
    	String logFile = Configuration.get("CHEF_CLIENT_LOG");
    	if (logFile == null || logFile.isEmpty()) {
    		logFile = "/tmp/chef-client.log";
    	}
    	final String CHEF_CLIENT_COMMAND = "sudo chef-client --logfile " + logFile;
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
    
    /**
     * Check if the new recipes are listed in the recipe list.
     * 
     * It is a way to verify if the upgrade was OK.
     * However, this strategy works only when it is the first time we are going to apply these new recipes in this node.
     * To properly detect application of recipe updates, we need another strategy, maybe checking the chef-client log. 
     *  
     * @param node
     * @param newRecipes
     * @return
     */
    private boolean checkNewRecipes(Node node, List<String> newRecipes) {

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	ChefNode chefNode = null;
		try {
			chefNode = knife.node().show(node.getChefName());
		} catch (KnifeException e) {
			logger.error("Knife exception. Should not happen", e);
		}
    	List<String> recipes = chefNode.getRecipes();
    	for (String item: newRecipes) {
    		if (!recipes.contains(item)) {
    			return false;
    		}
    	}

    	return true;
	}


}
