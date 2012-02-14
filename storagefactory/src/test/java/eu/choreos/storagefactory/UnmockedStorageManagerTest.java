package eu.choreos.storagefactory;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;

public class UnmockedStorageManagerTest {
	protected static StorageNodeManager storageManager;
	protected static StorageNodeSpec spec1 = new StorageNodeSpec();
	protected static StorageNodeSpec spec2 = new StorageNodeSpec();
	protected static SshUtil connection;

	@BeforeClass
	public static void initialSetUp() throws Exception {
		System.out
		.println(new SshUtil("localhost")
				.runCommand("knife node run_list remove choreos-node 'recipe[mysql::server]' -s http://aguia1.ime.usp.br:4000"));
		System.out
				.println(new SshUtil("localhost")
						.runCommand("knife node show choreos-node -s http://aguia1.ime.usp.br:4000"));

		connection = new SshUtil("choreos-node");

		System.out
				.println(connection
						.runCommand("sudo apt-get remove --purge mysql-server mysql-client-core-5.1 mysql-server-5.1 mysql-server -y"));

		spec1.setUuid("1");
		spec1.setType("mysql");

		spec2.setUuid("2");
		spec2.setType("mysql");

	}

	@Before
	public void setUp() {
		storageManager = new StorageNodeManager();
	}

	@Test
	public void shouldCreateAndStoreNodeDescription() throws Exception {

		StorageNode instantiatedNode = storageManager
				.registerNewStorageNode(spec1);

		assertEquals(spec1.getUuid(), instantiatedNode.getUuid());
		assertEquals(spec1.getType(), instantiatedNode.getType());
	}

	@Test
	public void shouldGetAnStorageNodeByItsID() throws Exception {

		storageManager.registerNewStorageNode(spec1);
		storageManager.registerNewStorageNode(spec2);

		assertSame(spec2.getUuid(), storageManager.registry.getNode("2").getUuid());
		assertSame(spec1.getUuid(), storageManager.registry.getNode("1").getUuid());
	}

	@Test
	public void shouldGetAllStorageNodes() throws Exception {

		storageManager.registerNewStorageNode(spec1);
		storageManager.registerNewStorageNode(spec2);

		assertEquals(2, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddRemoveAndKeepCountOfNodes() throws Exception {

		storageManager.registerNewStorageNode(spec1);
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(spec2);
		assertEquals(2, storageManager.registry.getNodes().size());

		storageManager.destroyNode("2");
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.destroyNode("1");
		assertEquals(0, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddAndRemoveSingleNode() throws Exception {

		assertEquals(0, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(spec1);
		assertEquals(1, storageManager.registry.getNodes().size());
		assertSame(spec1.getUuid(), storageManager.registry.getNode("1").getUuid());

		storageManager.destroyNode("1");

		assertEquals(0, storageManager.registry.getNodes().size());

	}

	@Test
	public void shouldInstallMySqlRecipeOnNode() throws Exception {
		fail("Not yet implemented...");
		storageManager = new StorageNodeManager();

		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();

		storageManager.registerNewStorageNode(storageNodeSpecs);
		
		String commandReturn = (new SshUtil("localhost"))
				.runCommand("knife node show choreos-node");

		assertTrue(commandReturn.contains("recipe[mysql::server]"));
	}

	@Test
	public void shouldInstallMySqlDatabaseOnNode() throws Exception {
		fail("Not yet implemented...");
		storageManager = new StorageNodeManager();

		StorageNode storageNode = new StorageNode();

		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();

		String deployedNode = "pending";

		SshUtil ssh = new SshUtil(deployedNode);

		String commandReturn = ssh.runCommand("mysql");

		assertEquals(
				"ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)",
				commandReturn);
	}
}