package org.ow2.choreos.deployment.nodes.cm;

import org.ow2.choreos.deployment.nodes.NodeCreator;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProvider;
import org.ow2.choreos.deployment.nodes.cloudprovider.CloudProviderFactory;
import org.ow2.choreos.nodes.NodeNotCreatedException;
import org.ow2.choreos.nodes.NodeNotUpdatedException;
import org.ow2.choreos.nodes.datamodel.CloudNode;
import org.ow2.choreos.nodes.datamodel.NodeSpec;
import org.ow2.choreos.utils.SshCommandFailed;
import org.ow2.choreos.utils.SshUtil;

import com.jcraft.jsch.JSchException;

/**
 * Creates a VM in a suitable state to image generation. This image is intended
 * to be set on configuration file to provide a "faster bootstrap".
 * 
 * @author leonardo
 * 
 */
public class VMForImageCreator {
    
    private static final String CLOUD = "AWS";
    public static final String CHEF_SOLO_FOLDER = "chef-solo";
    public static final String EASY_ESB_PACKAGE_URL = "http://valinhos.ime.usp.br:54080/easyesb/easyesb-cd-08.10.13.tar.gz";
    public static final String EASY_ESB_CLI_PACKAGE_URL = "http://valinhos.ime.usp.br:54080/easyesb/easyesb-cli-08.10.13.tar.gz";
    
    private CloudNode node;

    public static void main(String[] args) throws Exception {
        VMForImageCreator creator = new VMForImageCreator();
        creator.run();
    }

    public void run() throws Exception {
        createNode();
        installTomcat();
        clearChefFolder();
        downloadEasyESB();
    }
    
    private void createNode() throws NodeNotCreatedException {
        CloudProvider cp = CloudProviderFactory.getInstance(CLOUD);
        NodeCreator nodeCreator = new NodeCreator(cp);
        node = nodeCreator.createBootstrappedNode(new NodeSpec());
    }

    private void installTomcat() throws JSchException, SshCommandFailed, NodeNotUpdatedException {
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        ssh.runCommand("chmod +x " + CHEF_SOLO_FOLDER + "/add_recipe_to_node.sh");
        ssh.runCommand("./" + CHEF_SOLO_FOLDER + "/add_recipe_to_node.sh tomcat::choreos");
        NodeUpdater updater = NodeUpdaters.getUpdaterFor(node);
        updater.update();
    }

    private void clearChefFolder() throws JSchException, SshCommandFailed {
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        ssh.runCommand("rm -rf " + CHEF_SOLO_FOLDER);
    }

    private void downloadEasyESB() throws JSchException, SshCommandFailed {
        SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        ssh.runCommand("wget " + EASY_ESB_PACKAGE_URL);
        ssh.runCommand("wget " + EASY_ESB_CLI_PACKAGE_URL);
    }
}
