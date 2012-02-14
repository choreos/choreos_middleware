package eu.choreos.storagefactory;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class StorageNodeManager {
	public StorageNodeRegistryFacade registry;
	private NodePoolManagerHandler npm;
	
	public StorageNodeManager() {
		this.registry = new StorageNodeRegistryFacade();
		this.setNodePoolManagerHandler(new NodePoolManagerHandler());
	}

	public NodePoolManagerHandler getNodePoolManagerHandler() {
		return npm;
	}

	public void setNodePoolManagerHandler(NodePoolManagerHandler npm) {
		this.npm = npm;
	}

	public StorageNode registerNewStorageNode(StorageNodeSpec nodeSpec) {

		StorageNode storageNode = new StorageNode();
		storageNode.setType(nodeSpec.getType());
		storageNode.setUuid(nodeSpec.getUuid());
		
		registry.registerNode(storageNode);

		System.out.println("Node created");
		return storageNode;
	}

	public static void uploadRecipe(String recipeName) {
		CommandLineInterfaceHelper.runLocalCommand("knife cookbook upload " + recipeName);
	}
	
	/*
	public String setupStorageNode(StorageNode storageNode) throws Exception {
		SshUtil sshConnection = new SshUtil(storageNode.getNode().getHostname());
		
		String commandOutput = issueSshMySqlDeployerCommand(sshConnection, storageNode);
		
		return commandOutput;
	}*/

	/*
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
	}*/

	/*
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
    */
	// TODO: Create and define the thrown exception

	public void destroyNode(String storageNodeId) {
		StorageNode storageNode;

		try {
			storageNode = registry.getNode(storageNodeId);

			String id = storageNode.getUuid();
			
			getNodePoolManagerHandler().destroyNode(id);

			registry.unregisterNode(storageNodeId);

		} catch (Exception e) {
			System.out
					.println("[Storage Manager] Error: Could not find node with ID >"
							+ storageNodeId + "<");
			e.printStackTrace();
		}
	}

	/*
	private Node createSampleNode() throws RunNodesException {
		Node sampleNode = new Node();
		sampleNode.setImage("1");

		return infrastructure.createNode(sampleNode);
	}
	*/
}
