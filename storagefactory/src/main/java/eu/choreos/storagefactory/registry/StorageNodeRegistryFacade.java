package eu.choreos.storagefactory.registry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

import eu.choreos.storagefactory.Configuration;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class StorageNodeRegistryFacade {
	
	private static String NODE_NOT_FOUND ="0 items found";
	
	public StorageNodeRegistryFacade() {
	}

	public StorageNode getNode(String nodeUuid) {
		return getNode(nodeUuid, (new CommandLineInterfaceHelper()));
	}

	public StorageNode getNode(String nodeUuid, CommandLineInterfaceHelper cli) {
		String queryResult;
		String command = "knife search node storage_*_uuid:" + nodeUuid
				+ " -a storage -c "+ Configuration.get("CHEF_CONFIG_FILE");
		System.out.println(command);
		queryResult = cli.runLocalCommand(command);

		System.out.println("Query Result:>>"+queryResult+"<<");
		if(!isFoundNode(queryResult))
			return null;

		StorageNode storageNode = null;
		for (StorageNode node : processQueryResult(queryResult))
			storageNode = node;

		return storageNode;
	}

	private StorageNode setStorageNodeData(Hashtable<String, String> table) {

		StorageNode storage = new StorageNode();

		for (String key : table.keySet()) {
			System.out.println(key + " >> " + table.get(key));
		}
		storage.setUri(table.get("id"));

		storage.setPassword(table.get("dbpassword"));
		storage.setUser(table.get("dbuser"));
		storage.setUuid(table.get("uuid"));
		storage.setSchema(table.get("schema"));
		storage.setType(table.get("dbtype"));

		return storage;
	}

	public Collection<StorageNode> getNodes(CommandLineInterfaceHelper cli) {

		String queryResult;
		String command = "knife search node storage_*_uuid:* -a storage";
		queryResult = cli.runLocalCommand(command);

		System.out.println(command);
		System.out.println(">>" + queryResult + "<<");
		
		if(!isFoundNode(queryResult))
			return null;

		return processQueryResult(queryResult);
	}

	private boolean isFoundNode(String queryResult) {
		if(queryResult.trim().equals(NODE_NOT_FOUND))
			return false;
		
		return true;
	}

	private Collection<StorageNode> processQueryResult(String queryResult) {

		Collection<StorageNode> foundRecords = new ArrayList<StorageNode>();

		queryResult = queryResult.substring(queryResult.indexOf('\n') + 2);
		// queryResult = queryResult + '\r';

		String[] allLines = queryResult.split("" + '\n', -1);
		// create a string with a single record
		String singleRecord = "";
		for (String line : allLines) {
			singleRecord = singleRecord + line + '\n';
			System.out.println(line.length());

			if ((line.length() == 0) && singleRecord.contains(":")) {

				// convert the string to a hash table
				Hashtable<String, String> hashTable = processRecord(singleRecord + '\n');
				// With the aid of the hash table, create a properly set
				// StorageNode
				StorageNode node = setStorageNodeData(hashTable);

				// Add this node to the result collection
				foundRecords.add(node);

				// Start over the process for the next one.
				singleRecord = "";
			}
		}

		return foundRecords;
	}

	private Hashtable<String, String> processRecord(String currentRecord) {
		Hashtable<String, String> table = new Hashtable<String, String>();

		String lines[] = currentRecord.split("" + '\n');
		for (String line : lines) {
			if (line.contains(":")) {
				String key = line.split(":")[0].trim();
				String value = line.split(":")[1].trim();
				table.put(key, value);
			}
		}

		return table;

	}

	public Collection<StorageNode> getNodes() {
		return getNodes((new CommandLineInterfaceHelper()));
	}
}