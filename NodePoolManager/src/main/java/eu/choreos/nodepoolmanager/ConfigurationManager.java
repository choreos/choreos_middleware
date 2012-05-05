package eu.choreos.nodepoolmanager;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.jcraft.jsch.JSchException;

import eu.choreos.nodepoolmanager.chef.ChefScripts;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.CommandLine;
import eu.choreos.nodepoolmanager.utils.SshUtil;

public class ConfigurationManager {

    private static String INITIAL_RECIPE = "getting-started";
    private static String CHEF_REPO = Configuration.get("CHEF_REPO");

    private static ConcurrentMap<Node, Boolean> updating = new ConcurrentHashMap<Node, Boolean>();
    private static ConcurrentMap<Node, Boolean> needUpdate = new ConcurrentHashMap<Node, Boolean>();

    private void updateNodeConfiguration(final Node node) throws Exception {
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

        System.out.println("Waiting for SSH...");
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());

        while (!ssh.isAccessible()) {
            System.out.println("Could not connect to " + node.getUser() + "@" + node.getIp()
                    + " with key " + node.getPrivateKeyFile() + " yet");
            System.out.println("Trying again in 5 seconds");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

        System.out.println("Connected");

        String command;
        try {
            // bootstrap node
            System.out.println("Bootstraping " + node.getHostname());
            command = ChefScripts.getChefBootstrapScript(node.getPrivateKeyFile(), node.getIp(),
                    node.getUser());
            System.out.println(command);
            CommandLine.runLocalCommand(command, CHEF_REPO);
            System.out.println("Bootstrap completed");

            this.retrieveChefName(node);
            this.installInitialRecipe(node);

        } catch (Exception e) {
            e.printStackTrace();
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

        String createdFile = "chef-getting-started.txt";
        String returnText = null;
        returnText = ssh.runCommand("ls " + createdFile, true);
        System.out.println(">>" + returnText.trim() + "<<");

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
            System.out.println("Node not initialized yet. Going to initialize it.");
            this.initializeNode(node);
        }

        if (node.getChefName() == null)
            this.retrieveChefName(node);
        System.out.println("node name = " + node.getChefName());
        String command = ChefScripts.getChefAddCookbook(node.getChefName(), cookbook, recipe);
        System.out.println("Install recipe command = [" + command + "]");
        CommandLine.runLocalCommand(command);
        // TODO we should verify if the recipe install was OK
        // but it is very awkward make this without using the chef API!

        this.updateNodeConfiguration(node);
    }

    private void installInitialRecipe(Node node) {

        String command = ChefScripts.getChefAddCookbook(node.getChefName(), INITIAL_RECIPE,
                "default");
        System.out.println("Install recipe command = [" + command + "]");
        CommandLine.runLocalCommand(command);

        try {
            this.updateNodeConfiguration(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
