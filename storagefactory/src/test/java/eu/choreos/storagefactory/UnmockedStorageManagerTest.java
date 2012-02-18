package eu.choreos.storagefactory;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import eu.choreos.nodepoolmanager.utils.SshUtil;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class UnmockedStorageManagerTest {
	protected static StorageNodeManager storageManager;
	protected static StorageNode node1;
	protected static StorageNode node2;
	protected static StorageNodeSpec spec1 = new StorageNodeSpec();
	protected static StorageNodeSpec spec2 = new StorageNodeSpec();
	protected static SshUtil connection;

	// @BeforeClass
	public static void initialSetUp() throws Exception {
		System.out
				.println(new CommandLineInterfaceHelper()
						.runLocalCommand("knife node run_list remove choreos-node 'recipe[mysql::server]' -s http://aguia1.ime.usp.br:4000"));
		System.out
				.println(new CommandLineInterfaceHelper()
						.runLocalCommand("knife node show choreos-node -s http://aguia1.ime.usp.br:4000"));

		// connection = new SshUtil("choreos-node");

		System.out
				.println(connection
						.runCommand("sudo apt-get remove --purge mysql-server mysql-client-core-5.1 mysql-server-5.1 mysql-server -y"));

		spec1.setUuid("1");
		spec1.setType("mysql");

		node1 = new StorageNode(spec1);

		node2.setUuid("2");
		node2.setType("mysql");

		node2 = new StorageNode(spec2);
	}

	// @Before
	public void setUp() {
		storageManager = new StorageNodeManager();
	}

	// @Test
	public void shouldInstallMySqlRecipeOnNode() throws Exception {
		fail("Not yet implemented...");
		storageManager = new StorageNodeManager();

		String commandReturn = new CommandLineInterfaceHelper()
				.runLocalCommand("knife node show choreos-node");

		assertTrue(commandReturn.contains("recipe[mysql::server]"));
	}

	// @Test
	public void shouldInstallMySqlDatabaseOnNode() throws Exception {
		fail("Not yet implemented...");
		storageManager = new StorageNodeManager();

		StorageNode storageNode = new StorageNode();

		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();

		String deployedNode = "pending";

		// SshUtil ssh = new SshUtil(deployedNode);

		// String commandReturn = ssh.runCommand("mysql");

		// assertEquals(
		// "ERROR 1045 (28000): Access denied for user 'root'@'localhost' (using password: NO)",
		// commandReturn);
	}
}