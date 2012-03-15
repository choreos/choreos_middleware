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

		this.storageManager = new StorageNodeManager();

		this.spec1.setUuid("1");
		this.spec1.setType("mysql");

		this.spec2.setUuid("2");
		this.spec2.setType("mysql");

		this.node1 = new StorageNode(spec1);
		this.node2 = new StorageNode(spec2);
	}

	@Test
	public void shouldCreateNewStorageFromSpecification() {
		StorageNode node = storageManager.createNewStorageNode(spec1);

		assertEquals(node.getType(), spec1.getType());
		assertEquals(node.getUuid(), spec1.getUuid());

	}
}