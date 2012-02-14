package eu.choreos.storagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.FixedCloudProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;
import eu.choreos.storagefactory.datamodel.InfrastructureNodeData;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;

public class UnmockedStorageManagerTest {
	protected StorageNodeManager storageManager;
	protected static StorageNode sampleStorageNode;
	protected static InfrastructureNodeData infraNode = new InfrastructureNodeData();
	protected static StorageNodeSpec spec1 = new StorageNodeSpec();
	protected static StorageNodeSpec spec2 = new StorageNodeSpec();
	protected static SshUtil connection;

	private static CloudProvider infrastructure;

	@BeforeClass
	public static void initialSetUp() throws Exception {
		System.out.println(new SshUtil("localhost").runCommand("knife node run_list remove choreos-node 'recipe[mysql::server]' -s http://aguia1.ime.usp.br:4000"));
		System.out.println(new SshUtil("localhost").runCommand("knife node show choreos-node -s http://aguia1.ime.usp.br:4000"));
		
		connection = new SshUtil("choreos-node");

		System.out.println(connection.runCommand("sudo apt-get remove --purge mysql-server mysql-client-core-5.1 mysql-server-5.1 mysql-server -y"));
		
		infrastructure = new FixedCloudProvider();
		
		spec1.setCorrelationID("1");
		spec1.setType("mysql");
		
		spec2.setCorrelationID("2");
		spec2.setType("mysql");
		
		infraNode.setHostname("choreos-node");
	}

	@Test
	public void shouldCreateAndStoreNodeDescription() throws Exception {
		StorageNode instantiatedNode = storageManager.registerNewStorageNode(spec1, infraNode);

		assertEquals(spec1.getCorrelationID(), instantiatedNode
				.getStorageNodeSpec().getCorrelationID());
		assertEquals(spec1.getType(), instantiatedNode
				.getStorageNodeSpec().getType());
	}

	@Test
	public void shouldGetAnStorageNodeByItsID() throws Exception {

		storageManager.registerNewStorageNode(spec1, infraNode);
		storageManager.registerNewStorageNode(spec2, infraNode);

		assertSame(spec2, storageManager.registry.getNode("2")
				.getStorageNodeSpec());
		assertSame(spec1, storageManager.registry.getNode("1")
				.getStorageNodeSpec());
	}

	@Test
	public void shouldGetAllStorageNodes() throws Exception {

		storageManager.registerNewStorageNode(spec1, infraNode);
		storageManager.registerNewStorageNode(spec2, infraNode);

		assertEquals(2, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddRemoveAndKeepCountOfNodes() throws Exception {

		storageManager.registerNewStorageNode(spec1, infraNode);
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(spec2, infraNode);
		assertEquals(2, storageManager.registry.getNodes().size());

		storageManager.destroyNode("2");
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.destroyNode("1");
		assertEquals(0, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddAndRemoveSingleNode() throws Exception {

		assertEquals(0, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(spec1, infraNode);
		assertEquals(1, storageManager.registry.getNodes().size());
		assertSame(spec1, storageManager.registry.getNode("1")
				.getStorageNodeSpec());

		storageManager.destroyNode("1");

		assertEquals(0, storageManager.registry.getNodes().size());

	}
	
	@Test
	public void shouldGetNodeFromNodePoolManager() throws Exception {
		InfrastructureNodeData infra = storageManager.createInfrastructureNode();
		assertTrue("NodePoolManager returned no hostname", infra.getHostname().length() > 0);
	}
	
	
	@Test
	public void shouldInstallMySqlRecipeOnNode() throws Exception {
		storageManager = new StorageNodeManager();
		
		StorageNode storageNode = new StorageNode();
		
		storageNode.setInfrastructureNodeData(storageManager.createInfrastructureNode());
		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();
		storageNode.setStorageNodeSpec(storageNodeSpecs );
		
		String commandReturn = (new SshUtil("localhost")).runCommand("knife node show choreos-node");
		
		assertTrue(commandReturn.contains("recipe[mysql::server]"));
	}
	
	@Test
	public void shouldInstallMySqlDatabaseOnNode() throws Exception {
		storageManager = new StorageNodeManager();
		
		StorageNode storageNode = new StorageNode();
		
		storageNode.setInfrastructureNodeData(storageManager.createInfrastructureNode());
		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();
		storageNode.setStorageNodeSpec(storageNodeSpecs );
		
		String deployedNode = storageNode.getInfrastructureNodeData().getHostname();
		
		SshUtil ssh = new SshUtil(deployedNode);
		
		String commandReturn = ssh.runCommand("mysql");

		assertEquals("ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)", commandReturn);
	}
}