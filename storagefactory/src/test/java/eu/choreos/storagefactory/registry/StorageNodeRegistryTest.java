package eu.choreos.storagefactory.registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.utils.CommandLineInterfaceHelper;

public class StorageNodeRegistryTest {

	protected StorageNodeRegistryFacade registry = new StorageNodeRegistryFacade();
	protected static CommandLineInterfaceHelper cliHelper;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {

		// cliHelper = new CommandLineInterfaceHelper();
		cliHelper = mock(CommandLineInterfaceHelper.class);

		when(
				cliHelper
						.runLocalCommand("knife search node storage_*_uuid:987654321 -a storage"))
				.thenReturn(
						"1 node found"
								+ '\n'
								+ '\n'
								+ "id:   test-single-record"
								+ '\n'
								+ "storage: "
								+ '\n'
								+ "  987654321:        "
								+ '\n'
								+ "    dbpassword:          CODE"
								+ '\n'
								+ "    dbuser:    UZER"
								+ '\n'
								+ "    uuid:       987654321"
								+ '\n'
								+ "    schema:     SKEMA"
								+ '\n'
								+ "    sqlscript:    /tmp/987654321-sql-script.sql"
								+ '\n' + "    dbtype:   MySQL" + '\n' + '\n');

		when(
				cliHelper
						.runLocalCommand("knife search node storage_*_uuid:* -a storage"))
				.thenReturn(
						"2 nodes found"
								+ '\n'
								+ '\n'
								+ "id:   test-multiple-records"
								+ '\n'
								+ "storage: "
								+ '\n'
								+ "  987654321:        "
								+ '\n'
								+ "    dbpassword:          CODE"
								+ '\n'
								+ "    dbuser:    UZER"
								+ '\n'
								+ "    uuid:       987654321"
								+ '\n'
								+ "    schema:     SKEMA"
								+ '\n'
								+ "    sqlscript:    /tmp/987654321-sql-script.sql"
								+ '\n'
								+ "    dbtype:   MySQL"
								+ '\n'
								+ '\n'
								+ "id:   choreos-node"
								+ '\n'
								+ "storage: "
								+ '\n'
								+ "  123456789:        "
								+ '\n'
								+ "    dbpassword:          CODE"
								+ '\n'
								+ "    dbuser:    UZER"
								+ '\n'
								+ "    uuid:       123456789"
								+ '\n'
								+ "    schema:     SKEMA"
								+ '\n'
								+ "    sqlscript:    /tmp/123456789-sql-script.sql"
								+ '\n' + "    dbtype:   MySQL" + '\n' + '\n');

	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetNode() {
		StorageNode node = registry.getNode("987654321", cliHelper);
		assertTrue("Returned Node was null", node != null);
		assertEquals("test-single-record", node.getUri());
		assertEquals("987654321", node.getUuid());
		assertEquals("MySQL", node.getType());
		assertEquals("UZER", node.getUser());
		assertEquals("CODE", node.getPassword());
		assertEquals("SKEMA", node.getSchema());

	}

	@Test
	public void testGetNodes() {
		Collection<StorageNode> nodes = registry.getNodes(cliHelper);
		assertTrue("Returned Nodes was null", nodes != null);
		assertEquals("There should be only one node registered", 2,
				nodes.size());

		for (StorageNode currentNode : nodes) {
			assertTrue("The node id should be \"test-multiple-records\"",
					"test-multiple-records".equals(currentNode.getUri())
							|| "choreos-node".equals(currentNode.getUri()));
		}
		assertEquals("There should be two nodes", 2, nodes.size());
	}

	@Test
	public void shouldGetNodeFromUUIDForReal() {

		assertEquals("choreos-node", registry.getNode("314159265").getUri());
	}
}
