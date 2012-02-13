package br.usp.ime.ccsl.choreos.storagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNode;
import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNodeSpec;
import br.usp.ime.choreos.nodepoolmanager.Node;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.FixedCloudProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class UnmockedStorageManagerTest {
	protected StorageNodeManager storageManager;
	protected static StorageNode sampleStorageNode;
	protected static Node infraNode = new Node();
	protected static StorageNodeSpec spec1 = new StorageNodeSpec();
	protected static StorageNodeSpec spec2 = new StorageNodeSpec();
	protected static SshUtil connection;

	private static CloudProvider infrastructure;

	@BeforeClass
	public static void initialSetUp() throws Exception {
		System.out.println(new SshUtil("localhost").runCommand("knife node run_list remove choreos-node 'recipe[mysql::server]' -s http://aguia1.ime.usp.br:4000"));
		System.out.println(new SshUtil("localhost").runCommand("knife node show choreos-node -s http://aguia1.ime.usp.br:4000"));
		
		System.out.println(new SshUtil("choreos-node").runCommand("sudo apt-get remove --purge mysql-server mysql-client-core-5.1 mysql-server-5.1 mysql-server -y"));
		
		infrastructure = new FixedCloudProvider();
		
		connection = new SshUtil("choreos-node");
		
		
		spec1.setStorageId(new Long(1));
		spec1.setStorageType("mysql");
		
		spec2.setStorageId(new Long(2));
		spec2.setStorageType("mysql");
		
		infraNode.setHostname("localhost");

	}

	@Test
	public void shouldCreateAndStoreNodeDescription() throws Exception {
		storageManager = new StorageNodeManager(infrastructure);
		
		StorageNode instantiatedNode = storageManager.registerNewStorageNode(spec1, infraNode);

		assertEquals(spec1.getStorageId(), instantiatedNode
				.getStorageNodeSpecs().getStorageId());
		assertEquals(spec1.getStorageType(), instantiatedNode
				.getStorageNodeSpecs().getStorageType());
	}

	@Test
	public void shouldGetAnStorageNodeByItsID() throws Exception {

		storageManager = new StorageNodeManager(infrastructure);
		
		storageManager.registerNewStorageNode(spec1, infraNode);
		storageManager.registerNewStorageNode(spec2, infraNode);

		assertSame(spec2, storageManager.registry.getNode(new Long(2))
				.getStorageNodeSpecs());
		assertSame(spec1, storageManager.registry.getNode(new Long(1))
				.getStorageNodeSpecs());
	}

	@Test
	public void shouldGetAllStorageNodes() throws Exception {

		storageManager = new StorageNodeManager(infrastructure);
		
		storageManager.registerNewStorageNode(spec1, infraNode);
		storageManager.registerNewStorageNode(spec2, infraNode);

		assertEquals(2, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddRemoveAndKeepCountOfNodes() throws Exception {
		storageManager = new StorageNodeManager(infrastructure);
		
		storageManager.registerNewStorageNode(spec1, infraNode);
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(spec2, infraNode);
		assertEquals(2, storageManager.registry.getNodes().size());

		storageManager.destroyNode(new Long(2));
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.destroyNode(new Long(1));
		assertEquals(0, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddAndRemoveSingleNode() throws Exception {

		storageManager = new StorageNodeManager(infrastructure);
		
		assertEquals(0, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(spec1, infraNode);
		assertEquals(1, storageManager.registry.getNodes().size());
		assertSame(spec1, storageManager.registry.getNode(new Long(1))
				.getStorageNodeSpecs());

		storageManager.destroyNode(new Long(1));

		assertEquals(0, storageManager.registry.getNodes().size());

	}
	
	@Test
	public void shouldGetNodeFromNodePoolManager() throws Exception {
		storageManager = new StorageNodeManager(infrastructure);
		
		Node infra = storageManager.createInfrastructureNode();
		assertNotNull("NodePoolManager returned null pointer", infra);
	}
	
	@Test
	public void shouldInstallMySqlRecipeOnNode() throws Exception {
		storageManager = new StorageNodeManager(infrastructure);
		
		StorageNode storageNode = new StorageNode();
		
		storageNode.setNode(storageManager.createInfrastructureNode());
		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();
		storageNode.setStorageNodeSpecs(storageNodeSpecs );
		
		String commandReturn = storageManager.issueSshMySqlDeployerCommand(new SshUtil("localhost"), storageNode);
		
		assertEquals("run_list: \n" +
				"    recipe[getting-started]\n" +
				"    recipe[mysql::server]\n" +
				"", commandReturn);
		
	}
	
	@Test
	public void shouldInstallMySqlDatabaseOnNode() throws Exception {
		storageManager = new StorageNodeManager(infrastructure);
		
		StorageNode storageNode = new StorageNode();
		
		storageNode.setNode(storageManager.createInfrastructureNode());
		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();
		storageNode.setStorageNodeSpecs(storageNodeSpecs );
		
		String commandReturn = storageManager.issueSshMySqlDeployerCommand(new SshUtil("localhost"), storageNode);
		
		assertEquals("run_list: \n" +
				"    recipe[getting-started]\n" +
				"    recipe[mysql::server]\n" +
				"", commandReturn);
		
		SshUtil ssh = new SshUtil("choreos-node");

		commandReturn = ssh.runCommand("chef-client");
		System.out.println(commandReturn);
		
		System.out.println(ssh.runCommand("mysql"));
		commandReturn = ssh.runCommand("mysql");
		assertEquals("ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)", commandReturn);
	}
	
}