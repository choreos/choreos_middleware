package br.usp.ime.ccsl.choreos.storagefactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNode;
import br.usp.ime.ccsl.choreos.storagefactory.datatypes.StorageNodeSpec;
import br.usp.ime.choreos.nodepoolmanager.Node;
import br.usp.ime.choreos.nodepoolmanager.cloudprovider.CloudProvider;
import br.usp.ime.choreos.nodepoolmanager.utils.SshUtil;

public class StorageManagerTest {
	protected static StorageNodeManager storageManager;
	protected static StorageNode sampleStorageNode;
	protected Node infraNode = new Node();
	protected StorageNodeSpec spec1 = new StorageNodeSpec();
	protected StorageNodeSpec spec2 = new StorageNodeSpec();
	protected SshUtil connection;

	private CloudProvider infrastructure;

	@Before
	public void setUp() throws Exception {
		String parm;
		
		this.infrastructure = mock(CloudProvider.class);
		when(infrastructure.createNode((Node) anyObject())).thenReturn(
				infraNode);
		
		this.connection = mock(SshUtil.class);
		when(connection.runCommand("knife node run_list add $hostname 'mysql::server' -k $userkeyfile -s $chefserverurl -u $chefuser"+'\n')).thenReturn("OK");
		when(connection.isAccessible()).thenReturn(true);

		storageManager = new StorageNodeManager(this.infrastructure);
		
		spec1.setStorageId(new Long(1));
		spec1.setStorageType("mysql");
		
		spec2.setStorageId(new Long(2));
		spec2.setStorageType("mysql");

		infraNode.setHostname("localhost");
	}

	@Test
	public void shouldCreateAndStoreNodeDescription() throws Exception {
		StorageNode instantiatedNode = storageManager.registerNewStorageNode(spec1, infraNode);

		assertEquals(spec1.getStorageId(), instantiatedNode
				.getStorageNodeSpecs().getStorageId());
		assertEquals(spec1.getStorageType(), instantiatedNode
				.getStorageNodeSpecs().getStorageType());
	}

	@Test
	public void shouldGetAnStorageNodeByItsID() throws Exception {

		storageManager.registerNewStorageNode(spec1, infraNode);
		storageManager.registerNewStorageNode(spec2, infraNode);

		assertSame(spec2, storageManager.registry.getNode(new Long(2))
				.getStorageNodeSpecs());
		assertSame(spec1, storageManager.registry.getNode(new Long(1))
				.getStorageNodeSpecs());
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

		storageManager.destroyNode(new Long(2));
		assertEquals(1, storageManager.registry.getNodes().size());

		storageManager.destroyNode(new Long(1));
		assertEquals(0, storageManager.registry.getNodes().size());
	}

	@Test
	public void shouldAddAndRemoveSingleNode() throws Exception {

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
		Node infra = storageManager.createInfrastructureNode();
		assertNotNull("NodePoolManager returned null pointer", infra);
	}
	
	@Test
	public void shouldInstallMySqlOnNode() throws Exception {
		StorageNode storageNode = new StorageNode();
		
		storageNode.setNode(storageManager.createInfrastructureNode());
		StorageNodeSpec storageNodeSpecs = new StorageNodeSpec();
		storageNode.setStorageNodeSpecs(storageNodeSpecs );
		
		String commandReturn = storageManager.issueSshMySqlDeployerCommand(connection);
		
		assertEquals("OK", commandReturn);
		
	}
	
}