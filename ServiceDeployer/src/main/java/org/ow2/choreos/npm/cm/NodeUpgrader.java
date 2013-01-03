package org.ow2.choreos.npm.cm;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
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
	
	
    private static ConcurrentMap<Node, Boolean> updating = new ConcurrentHashMap<Node, Boolean>();
    private static ConcurrentMap<Node, Boolean> needUpdate = new ConcurrentHashMap<Node, Boolean>();

    /**
     * Runs chef-client in a given node
     * @param node
     * @throws JSchException if could not connect into the node
     * @throws NodeNotUpgradedException if chef-client ends in error
     */
    public void upgradeNodeConfiguration(Node node) throws JSchException, NodeNotUpgradedException {
        
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
    	
    	String logFile = Configuration.get("CHEF_CLIENT_LOG");
    	if (logFile == null || logFile.isEmpty()) {
    		logFile = "/tmp/chef-client.log";
    	}
    	
    	// TODO try to get the chef-client exit status to verify update success
//    	final String CHEF_CLIENT_COMMAND = "sudo -s 'chef-client --logfile " + logFile + "; echo *******exit status:****** $? >>" + logFile + "'";
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

}
