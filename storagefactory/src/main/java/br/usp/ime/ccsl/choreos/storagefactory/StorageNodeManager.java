package br.usp.ime.ccsl.choreos.storagefactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.jclouds.compute.RunNodesException;

import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNode;
import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNodeSpec;
import br.usp.ime.choreos.nodepoolmanager.Configuration;
import br.usp.ime.choreos.nodepoolmanager.Node;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class StorageNodeManager {
	private CloudProvider infrastructure;
	public StorageNodeRegistryFacade registry;

	public StorageNodeManager(CloudProvider infrastructure) {
		this.infrastructure = infrastructure;
		this.registry = new StorageNodeRegistryFacade();
	}

	public StorageNode registerNewStorageNode(StorageNodeSpec nodeSpec, Node infraNode) throws Exception {

		StorageNode storageNode = new StorageNode();

		storageNode.setStorageNodeSpecs(nodeSpec);
		storageNode.setNode(infraNode);

		registry.registerNode(storageNode);

		System.out.println("Node created");
		return storageNode;
	}

	public Node createInfrastructureNode() throws RunNodesException {
		Node infraNode;

		// interact with the node pool manager instance
		System.out.println("Creating storage node...");

		// set the node specs for the new storage node
		infraNode = createSampleNode();
		infraNode.setCpus(1);
		infraNode.setRam(1024);
		infraNode.setSo("linux");
		infraNode.setStorage(10000);

		// create a node according to features required
		return infrastructure.createNode(infraNode);
	}

	public String setupStorageNode(StorageNode storageNode) throws Exception {
		SshUtil sshConnection = new SshUtil(storageNode.getNode().getHostname());
		
		String commandOutput = issueSshMySqlDeployerCommand(sshConnection, storageNode);
		
		return commandOutput;
	}

	public String issueSshMySqlDeployerCommand(SshUtil sshConnection, StorageNode storage)
			throws Exception, IOException {
		int tries = 10;
		
		while(!sshConnection.isAccessible()){
			tries--;
			if (tries == 0) throw new Exception("[Storage Node Manager] Could not create a new storage node");
		}
		
		String commandOutput;
		String command = getMySqlServerManagerScript(storage.getNode().getHostname());
		System.out.println(command);
		commandOutput = sshConnection.runCommand(command);
		
		return commandOutput;
	}

	public String getMySqlServerManagerScript(String hostname) throws IOException {
    	URL scriptFile = ClassLoader.getSystemResource("chef/mysql_deploy.sh");
    	String command = FileUtils.readFileToString(new File(scriptFile.getFile()));

    	String user = Configuration.get("CHEF_USER");
    	String user_key_file = Configuration.get("CHEF_USER_KEY_FILE");
    	String chef_server_url = Configuration.get("CHEF_SERVER_URL");

    	command = command.replace("$userkeyfile", user_key_file);
    	command = command.replace("$chefserverurl", chef_server_url);
    	command = command.replace("$hostname", hostname);
    	command = command.replace("$recipe", "default");
    	command = command.replace("$cookbook", "petals");
    	return command.replace("$chefuser", user);
    }
	// TODO: Create and define the thrown exception

	public void destroyNode(Long storageNodeId) {
		StorageNode storageNode;

		try {
			storageNode = registry.getNode(storageNodeId);

			String infrastructureNodeID = storageNode.getNode().getId();
			infrastructure.destroyNode(infrastructureNodeID);

			registry.unregisterNode(storageNodeId);

		} catch (Exception e) {
			System.out
					.println("[Storage Manager] Error: Could not find node with ID "
							+ storageNodeId.intValue());
			e.printStackTrace();
		}
	}

	private Node createSampleNode() throws RunNodesException {
		Node sampleNode = new Node();
		sampleNode.setImage("1");

		return infrastructure.createNode(sampleNode);
	}
}