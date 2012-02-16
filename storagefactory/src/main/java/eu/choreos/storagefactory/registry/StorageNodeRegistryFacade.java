package eu.choreos.storagefactory.registry;

import java.util.ArrayList;
import java.util.Collection;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class StorageNodeRegistryFacade {

	public StorageNodeRegistryFacade() {
	}

	public StorageNode getNode(String nodeId) {
		return getNode(nodeId, (new CommandLineInterfaceHelper()));
	}

	public StorageNode getNode(String nodeId, CommandLineInterfaceHelper cli) {
		String queryResult;
		queryResult = cli.runLocalCommand("knife search node storage_*_id:"
				+ nodeId);
		System.out.println("knife search node 'storage_*_id:" + nodeId + "'");
		System.out.println(">>" + queryResult + "<<");

		// queryResult = CommandLineInterfaceHelper
		// .runLocalCommand("knife search node \"attributes:storage/?????/id\"");

		if (queryResult.length() > 0) {

			StorageNode storage = new StorageNode();
			storage.setUri(getFullyQualifiedDomainName(queryResult));

			return storage;

		} else
			return null;
	}

	private String getFullyQualifiedDomainName(String queryResult) {
		String[] responseLines = queryResult.split("" + '\n');
		for (int i = 0; i < responseLines.length; i++) {
			if (responseLines[i].contains("FQDN")) {
				String[] parts = responseLines[2].split(":   ");
				return parts[1].trim();
			}
		}
		return null;
	}

	public Collection<StorageNode> getNodes(CommandLineInterfaceHelper cli) {

		Collection<StorageNode> allNodes = new ArrayList<StorageNode>();

		String queryResult;
		String command = "knife search node storage_*_id:*";
		queryResult = cli.runLocalCommand(command);

		System.out.println(command);
		System.out.println(">>" + queryResult + "<<");

		if (queryResult.length() > 0) {

			StorageNode storage = new StorageNode();
			storage.setUri(getFullyQualifiedDomainName(queryResult));
			allNodes.add(storage);

			return allNodes;

		} else
			return null;
	}

	public Collection<StorageNode> getNodes() {
		return getNodes((new CommandLineInterfaceHelper()));
	}
}