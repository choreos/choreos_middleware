package org.ow2.choreos.npm;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.log4j.Logger;
import org.ow2.choreos.npm.chef.ChefScripts;
import org.ow2.choreos.npm.datamodel.Node;
import org.ow2.choreos.npm.utils.CommandLine;
import org.ow2.choreos.npm.utils.SshUtil;

import com.jcraft.jsch.JSchException;


public class ConfigurationManager {

	private Logger logger = Logger.getLogger(ConfigurationManager.class);
	
    private static String INITIAL_RECIPE = "getting-started";
    private static String CHEF_REPO = Configuration.get("CHEF_REPO");

    private static ConcurrentMap<Node, Boolean> updating = new ConcurrentHashMap<Node, Boolean>();
    private static ConcurrentMap<Node, Boolean> needUpdate = new ConcurrentHashMap<Node, Boolean>();

    public void updateNodeConfiguration(final Node node) throws Exception {
        
    	needUpdate.put(node, true);

        if (updating.containsKey(node) && updating.get(node)) {
            return;
        }

        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (needUpdate.get(node)) {
            needUpdate.put(node, false);

            updating.put(node, true);
            ssh.runCommand("sudo chef-client >> /tmp/chef-client.log\n");
            updating.put(node, false);
        }
    }

    public void initializeNode(Node node) throws JSchException {

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

        logger.debug("Connected");

        String command;
        try {
            // bootstrap node
        	logger.info("Bootstraping " + node.getHostname());
            command = ChefScripts.getChefBootstrapScript(node.getPrivateKeyFile(), node.getIp(),
                    node.getUser());
            logger.debug(command);
            CommandLine.runLocalCommand(command, CHEF_REPO, true);
            logger.debug("Bootstrap completed");

            this.retrieveChefName(node);
            this.installInitialRecipe(node);

        } catch (Exception e) {
        	logger.error("Could not bootstrap node " + node, e);
        }
    }

    private void retrieveChefName(Node node) throws Exception {
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        String command = ChefScripts.getChefName();
        String chefClientName = ssh.runCommand(command, true);

        if (chefClientName == null || chefClientName.isEmpty())
            chefClientName = node.getHostname();
        chefClientName = chefClientName.replace("\n", "").trim();

        node.setChefName(chefClientName);
    }

    public boolean isInitialized(Node node) throws NodeNotAccessible, Exception {

        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        logger.debug("Going to connect to " + node);

        String createdFile = "chef-getting-started.txt";
        String returnText = null;
        returnText = ssh.runCommand("ls " + createdFile, true);
        logger.debug(">>" + returnText.trim() + "<<");

        return returnText.trim().equals(createdFile);
    }

    public void installRecipe(Node node, String cookbook) throws IOException, Exception {
        this.installRecipe(node, cookbook, "default");
    }

    /**
     *
     * @param node
     * @param cookbook
     * @param recipe
     * @return false if recipe not applied
     * @throws Exception
     * @throws IOException
     */
    public void installRecipe(Node node, String cookbook, String recipe) throws Exception {

        if (!isInitialized(node)) {
        	logger.debug("Node not initialized yet. Going to initialize it.");
            this.initializeNode(node);
        }

        if (node.getChefName() == null)
            this.retrieveChefName(node);
        logger.debug("node name = " + node.getChefName());
        String command = ChefScripts.getChefAddCookbook(node.getChefName(), cookbook, recipe);
        logger.debug("Install recipe command = [" + command + "]");

        // Chef fails silently when adding multiple recipes concurrently
        synchronized(ConfigurationManager.class) {
            CommandLine.runLocalCommand(command);
        }

        // TODO we should verify if the recipe install was OK
        // but it is very awkward make this without using the chef API!
    }

    private void installInitialRecipe(Node node) {

        String command = ChefScripts.getChefAddCookbook(node.getChefName(), INITIAL_RECIPE,
                "default");
        logger.debug("Install recipe command = [" + command + "]");
        CommandLine.runLocalCommand(command);

        try {
            this.updateNodeConfiguration(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
