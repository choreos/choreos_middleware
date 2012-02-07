package br.usp.ime.ccsl.choreos.storagefactory;

import java.io.UnsupportedEncodingException;

import org.jclouds.compute.RunNodesException;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.usp.ime.choreos.nodepoolmanager.Infrastructure;
import br.usp.ime.choreos.nodepoolmanager.Node;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class StorageManagerTest {
	    protected static StorageNode sampleStorageNode;
	    protected static Node node;
	    
	    protected StorageNodeManager storageNodeManager;
	    
	    private Infrastructure infrastructure;

	    @Before
	    public void setUp() throws RunNodesException{
	    	//this.node = mock(Node.class);
	    	this.infrastructure = mock(Infrastructure.class);
			when(infrastructure.createNode((Node) anyObject())).thenReturn(new Node());

			this.storageNodeManager = new StorageNodeManager(this.infrastructure);
	    }
	    
	  
	    @Test
	    public void shouldCreateStorageNode() throws Exception {
	        StorageNode storageNode = new StorageNode();
	        StorageNodeSpec spec = new StorageNodeSpec();
	        spec.setStorageId(new Long(1));
	        spec.setStorageType("mysql");
	        
	        StorageNode instantiatedNode = this.storageNodeManager.createNode(spec);
	        
	        assertEquals(spec.getStorageId(), instantiatedNode.getStorageNodeSpecs().getStorageId());
	        assertEquals(spec.getStorageType(), instantiatedNode.getStorageNodeSpecs().getStorageType());
	    }

	    @Test
	    public void shouldGetAnStorageNodeByItsID() throws Exception {
	    	
	        StorageNodeSpec spec1 = new StorageNodeSpec();
	        spec1.setStorageId(new Long(1));
	        spec1.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec1);
	        
	        StorageNodeSpec spec2 = new StorageNodeSpec();
	        spec2.setStorageId(new Long(2));
	        spec2.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec2);
	        
	        assertSame(spec2, this.storageNodeManager.getNode(new Long(2)).getStorageNodeSpecs());
	        assertSame(spec1, this.storageNodeManager.getNode(new Long(1)).getStorageNodeSpecs());
	    }
	    
	    @Test
	    public void shouldGetAllStorageNodes() throws Exception{
	    	StorageNodeSpec spec1 = new StorageNodeSpec();
	        spec1.setStorageId(new Long(1));
	        spec1.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec1);
	        
	        StorageNodeSpec spec2 = new StorageNodeSpec();
	        spec2.setStorageId(new Long(2));
	        spec2.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec2);
	        
	        assertEquals(2, storageNodeManager.getNodes().size());
	    }

	    @Test
	    public void shouldAddRemoveAndKeepCountOfNodes() throws Exception{
	    	StorageNodeSpec spec1 = new StorageNodeSpec();
	        spec1.setStorageId(new Long(1));
	        spec1.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec1);
	        
	        assertEquals(1, storageNodeManager.getNodes().size());
	        
	        StorageNodeSpec spec2 = new StorageNodeSpec();
	        spec2.setStorageId(new Long(2));
	        spec2.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec2);
	        
	        assertEquals(2, storageNodeManager.getNodes().size());
	        
	        this.storageNodeManager.destroyNode(new Long(2));
	        
	        assertEquals(1, storageNodeManager.getNodes().size());

	        this.storageNodeManager.destroyNode(new Long(1));
	        
	        assertEquals(0, storageNodeManager.getNodes().size());
	    }
	    
	    @Test
	    public void shouldAddAndRemoveSingleNode() throws Exception{
	    	
	    	assertEquals(0, storageNodeManager.getNodes().size());
	        
	    	StorageNodeSpec spec1 = new StorageNodeSpec();
	        spec1.setStorageId(new Long(1));
	        spec1.setStorageType("mysql");
	        
	        this.storageNodeManager.createNode(spec1);
	        
	        assertEquals(1, storageNodeManager.getNodes().size());
	        assertSame(spec1, storageNodeManager.getNode(new Long(1)).getStorageNodeSpecs());
	        
	        storageNodeManager.destroyNode(new Long(1));
	        
	        assertEquals(0, storageNodeManager.getNodes().size());
	        
	    }
}