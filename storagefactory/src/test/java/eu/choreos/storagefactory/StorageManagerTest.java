package eu.choreos.storagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;

public class StorageManagerTest {
	protected StorageNodeManager storageManager;
	protected StorageNodeSpec spec1 = new StorageNodeSpec();
	protected StorageNodeSpec spec2 = new StorageNodeSpec();
	protected StorageNode node1;
	protected StorageNode node2;

	@Before
	public void setUp() throws Exception {

		storageManager = new StorageNodeManager();

		spec1.setUuid("1");
		spec1.setType("mysql");

		spec2.setUuid("2");
		spec2.setType("mysql");

		node1 = new StorageNode(spec1);
		node2 = new StorageNode(spec2);
	}

	@Test
	public void shouldCreateAndStoreNodeDescription() throws Exception {
		StorageNode instantiatedNode = storageManager
				.registerNewStorageNode(node1);

		assertEquals(spec1.getUuid(), instantiatedNode.getUuid());
		assertEquals(spec1.getType(), instantiatedNode.getType());
	}

	@Test
	public void shouldGetAnStorageNodeByItsID() throws Exception {

		storageManager.registerNewStorageNode(node1);
		storageManager.registerNewStorageNode(node2);

		assertNotNull(storageManager.registry.getNode("1"));
		assertNotNull(storageManager.registry.getNode("2"));
		assertSame(spec2.getUuid(), storageManager.registry.getNode("2")
				.getUuid());
		assertSame(spec1.getUuid(), storageManager.registry.getNode("1")
				.getUuid());
	}

	@Test
	public void shouldGetAllStorageNodes() throws Exception {

		storageManager.registerNewStorageNode(node1);
		storageManager.registerNewStorageNode(node2);

		assertEquals(2, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddRemoveAndKeepCountOfNodes() throws Exception {
		storageManager.registerNewStorageNode(node1);
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(node2);
		assertEquals(2, storageManager.registry.getNodes().size());

		storageManager.destroyNode("2");
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.destroyNode("1");
		assertEquals(0, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddAndRemoveSingleNode() throws Exception {

		assertEquals(0, storageManager.registry.getNodes().size());

		storageManager.registerNewStorageNode(node1);
		assertEquals(1, storageManager.registry.getNodes().size());
		assertSame(spec1.getUuid(), storageManager.registry.getNode("1")
				.getUuid());

		storageManager.destroyNode("1");

		assertEquals(0, storageManager.registry.getNodes().size());

	}

	@Test
	public void shouldCreateNewStorageFromSpecification() {
		StorageNode node = storageManager.createNewStorageNode(spec1);

		assertEquals(node.getType(), spec1.getType());
		assertEquals(node.getUuid(), spec1.getUuid());

	}
}