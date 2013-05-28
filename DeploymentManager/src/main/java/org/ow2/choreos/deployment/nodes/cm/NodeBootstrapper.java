package org.ow2.choreos.deployment.nodes.cm;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.ow2.choreos.chef.ChefNodeNameRetriever;
import org.ow2.choreos.chef.Knife;
import org.ow2.choreos.chef.KnifeException;
import org.ow2.choreos.chef.impl.KnifeImpl;
import org.ow2.choreos.deployment.Configuration;
import org.ow2.choreos.deployment.services.recipe.BaseRecipeBuilder;
import org.ow2.choreos.nodes.NodeNotAccessibleException;
import org.ow2.choreos.nodes.datamodel.Node;
import org.ow2.choreos.utils.RemoteFileWriter;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshNotConnected;
import org.ow2.choreos.utils.SshUtil;
import org.ow2.choreos.utils.SshWaiter;

import com.jcraft.jsch.JSchException;

/**
 * 
 * @author leonardo, cadu, felps
 *
 */
public class NodeBootstrapper {
	
	public static final int SSH_TIMEOUT_IN_SECONDS = 250;
    private static final String CHEF_REPO = Configuration.get("CHEF_REPO");
    private static final String CHEF_CONFIG_FILE = Configuration.get("CHEF_CONFIG_FILE");
	private static final String BOOTSTRAP_LOG_FILE_LOCATION = "/tmp/bootstrap.log";

	private int sshTimeoutInSeconds;
	
	private Logger logger = Logger.getLogger(NodeBootstrapper.class);
    
    private Node node;
    
    public NodeBootstrapper(Node node) {
    	this.node = node;
    	this.sshTimeoutInSeconds = SSH_TIMEOUT_IN_SECONDS;
    }
    
    NodeBootstrapper(Node node, int sshTimeoutInSeconds) {
    	this.node = node;
    	this.sshTimeoutInSeconds = sshTimeoutInSeconds;
    }
    
    public void bootstrapNode() throws NodeNotAccessibleException, KnifeException, NodeNotBootstrappedException {

        SshWaiter sshWaiter = new SshWaiter();
        try {
			sshWaiter.waitSsh(this.node.getIp(), 
					this.node.getUser(), this.node.getPrivateKeyFile(), this.sshTimeoutInSeconds);
		} catch (SshNotConnected e) {
			throw new NodeNotAccessibleException(this.node.getIp() + " not accessible");
		}

    	logger.info("Bootstrapping " + this.node.getIp());
    	Knife knife = new KnifeImpl(CHEF_CONFIG_FILE, CHEF_REPO);
    	
    	
    	logger.info("Going to create harakiri for node chefnamed: " + this.node.getId());
    	configureHarakiri("http://192.168.48.115:9100/deploymentmanager/", this.node.getId());
    	List<String> defaultRecipes = DefaultRecipes.getDefaultRecipes();
    	defaultRecipes.add("harakiri" +this.node.getId().replace("/", "-"));
    	
    	for(String recipeName: defaultRecipes) {
    		logger.info("Uploading default recipe " + recipeName);    		
    		String res = knife.cookbook().upload(recipeName, CHEF_REPO+"/cookbooks");
    		logger.info("Upload recipes finished: " + res);
    	}
		String bootstrapLog = knife.bootstrap(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile(), defaultRecipes);
		logger.debug("remote Bootstrap log: " + bootstrapLog);
		
    	saveLogOnNode(bootstrapLog);

    	logger.info("Bootstrap completed at" + this.node);
		
    	this.retrieveAndSetChefName(bootstrapLog);
		
		NodeChecker checker = new NodeChecker();
		if (!checker.checkNodeOnNodesList(node)) {
			String msg = "Node " + node.getId() + " not bootstrapped";
			logger.error(msg);
			throw new NodeNotBootstrappedException(msg);
		}
    }
    
    private void configureHarakiri(String deploymentManagerURL, String nodeId) {
    	String chefRepoPath = copyTemplate(nodeId.replace("/", "-"));
    	
    	Map<String,String> replacementItems = new HashMap<String, String>();
    	// replace content of attributes/default.rb
    	replacementItems.put("$DEPLOYMENT_MANAGER_URL", deploymentManagerURL);
    	replacementItems.put("$NODE_ID", nodeId);
    	String filename = chefRepoPath + "/attributes/default.rb";
    	logger.info("Replacing file contents for file "+filename);
		changeFileContents(filename, replacementItems);
    }

	private String copyTemplate(String nodeID) {
		String  chefRepoPath = CHEF_REPO+"/cookbooks/harakiri"+nodeID;
		logger.info("Copying templates to dir: " + chefRepoPath);
    	File chefRepoFolder = new File(chefRepoPath);
    	
    	String harakiriTemplatePath = System.getProperty("user.dir")+"/src/main/resources/chef/harakiri";
    	logger.info("Destination dir: " + harakiriTemplatePath);
    	File harakiriTemplateFolder = new File(harakiriTemplatePath);
    	
    	try {
			FileUtils.copyDirectory(harakiriTemplateFolder, chefRepoFolder);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return chefRepoFolder.getAbsolutePath();
	}
    
    private void changeFileContents(String filename, Map<String, String> replacementItems) {
    	File attributesDefaultFile = new File(filename);

		String attributesDefaultFileData = null;
		synchronized (NodeBootstrapper.class) {
			try {
				attributesDefaultFileData = FileUtils.readFileToString(attributesDefaultFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		for (Map.Entry<String, String> entry : replacementItems.entrySet()) {
			attributesDefaultFileData = attributesDefaultFileData.replace(entry.getKey(), entry.getValue());
		}		
		
		synchronized (BaseRecipeBuilder.class) {
			FileUtils.deleteQuietly(attributesDefaultFile);
			try {
				FileUtils.writeStringToFile(attributesDefaultFile, attributesDefaultFileData);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
    }

	private void saveLogOnNode(String bootstrapLog) {

		SshUtil ssh = new SshUtil(this.node.getIp(), this.node.getUser(), this.node.getPrivateKeyFile());
		RemoteFileWriter remoteFileWriter = new RemoteFileWriter();
		
		try {
			remoteFileWriter.writeFile(bootstrapLog, BOOTSTRAP_LOG_FILE_LOCATION, ssh);
		} catch (SshCommandFailed e) {
			logger.error("Could not create the bootstrap log");
		}
		
		ssh.disconnect();
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
	

}
