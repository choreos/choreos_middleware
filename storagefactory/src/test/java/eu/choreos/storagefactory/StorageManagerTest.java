package eu.choreos.storagefactory;

import static org.junit.Assert.assertEquals;

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
	public void shouldCreateNewStorageFromSpecification() {
		StorageNode node = storageManager.createNewStorageNode(spec1);

		assertEquals(node.getType(), spec1.getType());
		assertEquals(node.getUuid(), spec1.getUuid());

	}
}