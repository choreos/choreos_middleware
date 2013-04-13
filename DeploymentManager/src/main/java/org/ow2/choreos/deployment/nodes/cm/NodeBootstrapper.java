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
import org.ow2.choreos.utils.RemoteFileWriter;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, cadu, felps
 *
 */
public class NodeBootstrapper {
	
	private Logger logger = Logger.getLogger(NodeBootstrapper.class);
	
    private static final String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
	private static final String BOOTSTRAP_LOG_FILE_LOCATION = "/tmp/bootstrap.log";
    private static final int MAX_TIME_TO_CONNECT = 250000;
    
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
	
    public void bootstrapNode() throws NodeNotAccessibleException, KnifeException {

    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	
        waitForSSHAccess();

    	logger.info("Bootstrapping " + this.node.getIp());
		String bootstrapLog = knife.bootstrap(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile(), DefaultRecipes.getDefaultRecipes());
		saveLogOnNode(bootstrapLog);
		logger.info("Bootstrap completed at" + this.node);
		this.retrieveAndSetChefName(bootstrapLog);
    }

	private void saveLogOnNode(String bootstrapLog) {

		SshUtil ssh = new SshUtil(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile());
		RemoteFileWriter remoteFileWriter = new RemoteFileWriter();
		
		try {
			remoteFileWriter.writeFile(bootstrapLog, BOOTSTRAP_LOG_FILE_LOCATION, ssh);
		} catch (SshCommandFailed e) {
			logger.error("Could not create the bootstrap");
		}
		
		ssh.disconnect();
	}

	private void waitForSSHAccess() throws NodeNotAccessibleException {
        
        final int DELAY = 5000;
        Timer timer = new Timer(MAX_TIME_TO_CONNECT, DELAY);
        Thread t = new Thread(timer);
        t.start();
        
        logger.debug("Waiting for SSH...");
        SshUtil ssh = new SshUtil(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile());
        while (!ssh.isAccessible()) {

            if (timer.timeouted()) {
            	logger.warn("VM not accessible!");
            	throw new NodeNotAccessibleException(this.node.getId());
            }
            
            logger.debug("Trying SSH into " + this.node.getIp() + " again in 5 seconds");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            	logger.error("Exception at sleeping!", e);
            }
        }
        ssh.disconnect();
        logger.debug("Connected to " + this.node);
	}
	
	public void retrieveAndSetChefName(String bootstrapLog) {
        
    	ChefNodeNameRetriever nameRetriever = new ChefNodeNameRetriever();
        String chefNodeName = null;
        
        try {
        	chefNodeName = nameRetriever.retrieveChefNodeNameFromBootstrapLog(bootstrapLog);
        } catch (IllegalArgumentException e1) {
			try {
				logger.debug("Going to retrieve chef name by running retriever script on node...");
				chefNodeName = nameRetriever.getChefNodeName(this.node.getIp(), 
						this.node.getUser(), this.node.getPrivateKeyFile());
			} catch (JSchException e) {
				chefNodeName = this.node.getHostname();
			} catch (SshCommandFailed e) {
				chefNodeName = this.node.getHostname();
			}
        }
		
		logger.debug("Retrieved chef name: " + chefNodeName);
		this.node.setChefName(chefNodeName);
    }
	
	private class Timer implements Runnable {

		int time, maxTime, delay;

		public Timer(int maxTime, int delay) {
			this.maxTime = maxTime;
			this.delay = delay;
			this.time = 0;
		}
		
		@Override
		public void run() {

			while (this.time < this.maxTime) {
				try {
					Thread.sleep(this.delay);
					this.time += this.delay;
				} catch (InterruptedException e) {
					logger.error("Error at sleeping. Should not happen.");
				}
			}
		}
		
		public boolean timeouted() {
			return this.time >= this.maxTime;
		}
	}



}
