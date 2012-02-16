package eu.choreos.storagefactory.registry;

import static org.junit.Assert.assertEquals;
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

		cliHelper = mock(CommandLineInterfaceHelper.class);

		when(cliHelper.runLocalCommand("knife search node storage_*_id:12345"))
				.thenReturn(
						"Node Name:   choreos-node" + '\n'
								+ "Environment: _default" + '\n'
								+ "FQDN:       choreos-node" + '\n'
								+ "IP:          10.0.3.15" + '\n'
								+ "Run List:    recipe[storage12345]" + '\n'
								+ "Roles:       " + '\n'
								+ "Recipes:     storage12345" + '\n'
								+ "Platform:    ubuntu 11.10" + '\n' + '\n');

		when(cliHelper.runLocalCommand("knife search node storage_*_id:*"))
				.thenReturn(
						"Node Name:   choreos-node" + '\n'
								+ "Environment: _default" + '\n'
								+ "FQDN:        choreos-node" + '\n'
								+ "IP:          10.0.3.15" + '\n'
								+ "Run List:    recipe[storage12345]" + '\n'
								+ "Roles:       " + '\n'
								+ "Recipes:     storage12345" + '\n'
								+ "Platform:    ubuntu 11.10" + '\n' + '\n');

	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetNode() {
		StorageNode node = registry.getNode("12345", cliHelper);
		assertEquals("choreos-node", node.getUri());

	}

	@Test
	public void testGetNodes() {
		Collection<StorageNode> nodes = registry.getNodes(cliHelper);
		assertEquals("There should be only one node registered", 1,
				nodes.size());
		for (StorageNode currentNode : nodes) {
			assertEquals("The only registered node should be \"choreos-node\"",
					"choreos-node", currentNode.getUri());
		}
	}

}
