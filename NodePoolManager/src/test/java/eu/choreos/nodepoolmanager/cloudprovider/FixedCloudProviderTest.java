package eu.choreos.nodepoolmanager.cloudprovider;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import eu.choreos.nodepoolmanager.Configuration;
import eu.choreos.nodepoolmanager.ConfigurationManager;
import eu.choreos.nodepoolmanager.chef.ScriptsProvider;
import eu.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import eu.choreos.nodepoolmanager.cloudprovider.FixedCloudProvider;
import eu.choreos.nodepoolmanager.datamodel.Node;
import eu.choreos.nodepoolmanager.utils.CommandLine;
import eu.choreos.nodepoolmanager.utils.SshUtil;

/**
 * Test requirements:
 * A virtual machine must be accessible through the FIXED_VM_IP
 * This machine must be not yet bootstrapped
 * The machine must have a "choreos" user 
 * This user must have sudo powers without entering password
 * (type $visudo and add the line "choreos ALL = NOPASSWD: ALL")
 * The public key must be registered on the authorized_keys2 file in the VM
 * This public key corresponds to the FIXED_VM_PRIVATE_SSH_KEY 
 * FIXED_VM_IP and FIXED_VM_PRIVATE_SSH_KEY are set on the nodepoolmanager.properties
 * Finally, don't forget also to synchronize the machine time!
 * ($ntpdate ccsl.ime.usp.br)
 *  
 * @author leonardo
 *
 */
public class FixedCloudProviderTest {
	
	@Test
	public void shouldReturnTheExpectedIdAndIp() throws Exception {

		cleanChefServer();
		
		CloudProvider cp = new FixedCloudProvider();
		Node node = cp.createNode(new Node());

        waitSsh(node);

        ConfigurationManager configurationManager = new ConfigurationManager();
        configurationManager.initializeNode(node);
        assertTrue(configurationManager.isInitialized(node));
	}

	private void waitSsh(Node node) {
		
		SshUtil ssh = new SshUtil(node.getIp(), node.getUser(), node.getPrivateKeyFile());
        while (!ssh.isAccessible())
            ;
	}

	private void cleanChefServer() {
		
		String workdir = Configuration.get("CHEF_REPO");
		String deleteChoreosNode = ScriptsProvider.getDeleteNode("choreos-node");
		String deleteChoreosClient = ScriptsProvider.getDeleteClient("choreos-node");
		CommandLine.runLocalCommand(deleteChoreosClient, workdir, true);
		CommandLine.runLocalCommand(deleteChoreosNode, workdir, true);
	}
}
