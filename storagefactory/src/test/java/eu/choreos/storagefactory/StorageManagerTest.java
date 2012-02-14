package eu.choreos.storagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import eu.choreos.storagefactory.datamodel.InfrastructureNodeData;
import eu.choreos.storagefactory.datamodel.StorageNode;
import eu.choreos.storagefactory.datamodel.StorageNodeSpec;

public class StorageManagerTest {
	protected static StorageNodeManager storageManager;
	protected static StorageNode sampleStorageNode;
	protected InfrastructureNodeData infraNode = new InfrastructureNodeData();
	protected StorageNodeSpec spec1 = new StorageNodeSpec();
	protected StorageNodeSpec spec2 = new StorageNodeSpec();

	@Before
	public void setUp() throws Exception {

		storageManager = new StorageNodeManager();
		
		spec1.setCorrelationID("1");
		spec1.setType("mysql");
		
		spec2.setCorrelationID("2");
		spec2.setType("mysql");
		
		infraNode.setHostname("localhost");
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
		assertTrue("NodePoolManager returned empty hostname", infra.getHostname().length() > 0);
	}
	
	/*
	@Test
	public void shouldInstallMySqlOnNode() throws Exception {
		StorageNode storageNode = new StorageNode();

		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();
		
		storageNode.setInfrastructureNodeData(storageManager.createInfrastructureNode());
		storageNode.setStorageNodeSpec(storageNodeSpecs );
		
		String commandReturn = ().createNode(infraNode)
		
		assertEquals("OK", commandReturn);
		
	}
	*/
}